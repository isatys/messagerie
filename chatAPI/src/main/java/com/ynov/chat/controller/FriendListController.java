package com.ynov.chat.controller;

import com.ynov.chat.model.User;
import com.ynov.chat.service.Liste_amiService;
import com.ynov.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FriendListController {
    @Autowired
    private Liste_amiService listeAmisService;

    //AFFICHER AMIS
    //tous les amis
    @GetMapping("/users/{idUsers}/listeAmis")
    public List<String> getListeAmis(@PathVariable Long idUsers) {
        return listeAmisService.getListe(idUsers);
    }

    //nombre d'amis
    @GetMapping("/users/{idUsers}/nombreAmis")
    public Long getNombreAmis(@PathVariable Long idUsers) {
        return listeAmisService.getNbrAmi(idUsers);
    }

    //CREATION MODIFICATION DEMANDE AMIS
    //ajout par id
    @PutMapping("/users/{idUsers1}/ajoutAmisId/{idUsers2}")
    public String ajoutAmisId(@PathVariable Long idUsers1, @PathVariable Long idUsers2) {
        return listeAmisService.addami(idUsers1, idUsers2);
    }
    //ajout par mail
    //Accepter une demande
    @PutMapping("/users/{currentUserId}/accepterAmiEnAttente/{idFuturAmi}")
    public String accepterAmiEnAttente(@PathVariable Long currentUserId, @PathVariable Long idFuturAmi) {
        return listeAmisService.acceptami(currentUserId, idFuturAmi);
    }
    //bloquer(true)/débloquer(false) un ami
    @PutMapping("/users/{currentUserId}/bloquer/{idUserABloquer}/{bloquerBoolean}")
    public String amisBloquer(@PathVariable Long currentUserId, @PathVariable Long idUserABloquer, @PathVariable Boolean bloquerBoolean) {
        return listeAmisService.amisBloquer(currentUserId, idUserABloquer, bloquerBoolean);
    }
}
