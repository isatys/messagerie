package com.ynov.chat.service;

import com.ynov.chat.model.Liste_ami;
import com.ynov.chat.model.User;
import com.ynov.chat.repository.Liste_amiRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

interface ObserverStateFriend
{
    public default void updatestate(Liste_ami amilist, String state) {

    }
}
interface StateFriend
{

    String acceptami (Long usercurrentid, Long userid);
    String amisBloquer(Long usercurrentid, Long userid, Boolean deny);
    String addami(Long iduserdemande, Long iduserrecoit);
}

@Data
@Service
public class Liste_amiService  implements  StateFriend,ObserverStateFriend{
    @Autowired
    private Liste_amiRepository listeamirepossitory;
    @Autowired
    private UserService userService;

    @Override
    public void updatestate(Liste_ami amilist, String state)
    {
        amilist.setEtat(state);
    }

    public List<String> getListe (Long userid)
    {
        User utilisateur = userService.getUser(userid).get(); // l'utilisateur actuel qui possède la liste d'ami
        List<Liste_ami> ownerListe = utilisateur.getListeAmi();//liste d'ami de l'utilisateur en cours

        long listesize = ownerListe.size();
        List<String> ListAmi = null;
        for (Liste_ami liste : ownerListe)
        {
            ListAmi.add(utilisateur.getPrenom() + " " +utilisateur.getNom());

        }
        return ListAmi;
    }

    public Long getNbrAmi(Long userid)
    {
        User utilisateur = userService.getUser(userid).get(); // l'utilisateur actuel qui possède la liste d'ami
        List<Liste_ami> ownerListe = utilisateur.getListeAmi();//liste d'ami de l'utilisateur en cours

        long listesize = ownerListe.size();
        Long ListNbrAmi = Long.valueOf(0);

        for (Liste_ami ami : ownerListe)
        {
            if(ami.getEtat().equals("accepte"))
            {
                ListNbrAmi = Long.valueOf(+ 1);
            }
        }
        return  ListNbrAmi;
    }

    @Override
    public String acceptami(Long usercurrentid, Long userid)
    {
        User currentUser = userService.getUser(usercurrentid).get();
        User Userfriend = userService.getUser(userid).get();

        List<Liste_ami> listeAmis = currentUser.getListeAmi();
        for (Liste_ami amis : listeAmis)
        {
            if (amis.getUtilisateur2_id().equals(userid))
            {
                if (!amis.getUtilisateur1_id().equals(usercurrentid))
                {
                    if (amis.getEtat().equals("attente"))
                    {
                        updatestate(amis,"accepte");
                        amis.setEtat("accepte");
                        listeamirepossitory.save(amis);
                        List<Liste_ami> ListnewAmi = Userfriend.getListeAmi();
                        for (Liste_ami newami : ListnewAmi) {
                            if (newami.getUtilisateur1_id().equals(userid)) {
                                updatestate(newami,"accepte");
                                newami.setEtat("accepte");
                                listeamirepossitory.save(newami);
                                String friend = Userfriend.getPrenom() + " " +Userfriend.getNom();
                                return friend;
                            }
                        }
                    }
                }
            }
        }
        return "Erreur";

    }

    @Override
    public String amisBloquer(Long usercurrentid, Long userid, Boolean deny)
    {

        User currentUser = userService.getUser(usercurrentid).get();
        User Userfriend = userService.getUser(userid).get();

        List<Liste_ami> listeAmis = currentUser.getListeAmi();
        for (Liste_ami amis : listeAmis)
        {
            if (amis.getUtilisateur2_id().equals(userid))
            {
                if (amis.getEtat().equals("bloque"))
                {
                    if (amis.getId().equals(usercurrentid))
                    {
                        if (deny.equals(true))
                        {
                            return "utilisateur deja bloque";
                        }
                        else
                        {
                            List<Liste_ami> listbloque = Userfriend.getListeAmi();
                            for (Liste_ami amisReverse : listbloque)
                            {
                                if (amisReverse.getUtilisateur2_id().equals(usercurrentid) && amisReverse.getId().equals(usercurrentid)) {
                                    listeamirepossitory.deleteById(amisReverse.getId());
                                    break;
                                }
                            }
                            listeamirepossitory.deleteById(amis.getId());
                            return "utilisateur debloque";
                        }
                    }
                    else
                    {
                        return "utilisateur inexistant";
                    }
                }
                updatestate(amis,"bloque");
                amis.setEtat("bloque");
                amis.setUtilisateur2_id(usercurrentid);

                listeamirepossitory.save(amis);

                List<Liste_ami> listeAmisReverse = Userfriend.getListeAmi();

                for (Liste_ami amisReverse : listeAmisReverse)
                {
                    if (amisReverse.getUtilisateur2_id().equals(usercurrentid))
                    {
                        updatestate(amis,"bloque");
                        amis.setEtat("bloque");
                        amis.setUtilisateur1_id(usercurrentid);
                        listeamirepossitory.save(amisReverse);
                        return "Utilisateur bloqué";
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String addami(Long iduserdemande, Long iduserrecoit)
    {
        User userdemande = userService.getUser(iduserdemande).get();
        User userrecoit = userService.getUser(iduserrecoit).get();

        List<Liste_ami> listeAmis = userdemande.getListeAmi();
        for (Liste_ami amis : listeAmis)
        {
            if (amis.getUtilisateur1_id().equals(iduserrecoit) && amis.getUtilisateur2_id().equals(iduserdemande))
            {
                if (amis.getEtat().equals("accepte"))
                {
                    return "vous ates deja ami avec cet utilsiateur";
                }
                else if (amis.getEtat().equals("attente"))
                {
                    return "votre demande est en attente";

                }
                else if (amis.getEtat().equals("bloque"))
                {
                    return "vous avez bloqué cet utilisateur";
                }
            }
            else if (amis.getUtilisateur2_id().equals(iduserdemande) && amis.getUtilisateur1_id().equals(iduserrecoit))
            {
                if (amis.getEtat().equals("accepte"))
                {
                    return "vous etes deja ami avec cet utilisateur";
                }
                else if (amis.getEtat().equals("attente"))
                {
                    updatestate(amis,"accepte");
                    amis.setEtat("accepte");
                    listeamirepossitory.save(amis);
                    List<Liste_ami> liste_r = userrecoit.getListeAmi();
                    for (Liste_ami amis_r : liste_r)
                    {
                        if (amis_r.getUtilisateur2_id().equals(iduserdemande) && amis_r.getUtilisateur1_id().equals(iduserrecoit))
                        {
                            updatestate(amis_r,"accepte");
                            amis_r.setEtat("accepte");
                           listeamirepossitory.save(amis_r);
                            return "friendzoned";
                        }
                    }
                }
            }
            else if (amis.getEtat().equals("bloque")) {
                return "utilisateur  indisponible";
            }

        }

        //On demande l'ami
        Liste_ami listeAmis1 = new Liste_ami();
        listeAmis1.setUsers(userdemande);
        listeAmis1.setUtilisateur2_id(iduserrecoit);
        listeAmis1.setUtilisateur1_id(iduserdemande);
        updatestate(listeAmis1,"attente");
        listeAmis1.setEtat("attente");
        listeAmis1.setId(0L);
        listeamirepossitory.save(listeAmis1);

        Liste_ami listeAmisBis = new Liste_ami();
        listeAmisBis.setUsers(userrecoit);
        listeAmisBis.setUtilisateur2_id(iduserdemande);
        listeAmisBis.setUtilisateur1_id(iduserdemande);
        updatestate(listeAmisBis,"attente");
        listeAmisBis.setEtat("attente");
        listeAmisBis.setId(0L);
        listeamirepossitory.save(listeAmisBis);
        return "demande envoyé";
    }



}
