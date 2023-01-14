package com.ynov.chat.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "messages")
@Entity
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="contenu")
    private String message;
    @Column(name="date_envoi")
    private String date_envoi;
    @Column(name="expediteur_id")
    private String expediteur_id;
    @Column(name="destinataire_id")
    private String destinataire_id;

}