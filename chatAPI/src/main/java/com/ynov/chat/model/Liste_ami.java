package com.ynov.chat.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "Liste_ami")
@Entity
public class Liste_ami implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User users;

    @Column (name ="utilisateur1_id")
    private Long utilisateur1_id;
    @Column (name ="utilisateur2_id")
    private Long utilisateur2_id;
    @Column (name="etat")
    private String  etat ;


}
