package com.ynov.chat.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Table(name = "users")
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "mail")
    private String mail;
    @Column(name = "mot_de_passe")
    private String mot_de_passe;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "port")
    private Short port;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<Liste_ami> ListeAmi;

}
