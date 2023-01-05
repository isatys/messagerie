package com.ynov.chat.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "tasks")
@Entity
public class Tache implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Date date;

    private boolean started;

    private boolean finished;

    // Les annotations servent à gérer comment l'ORM va lier et charger les données
    @ToString.Exclude
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_todolist")
    private TodoList todoList ;

}