package com.ynov.chat.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "todolists")
@Entity
public class TodoList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Les annotations servent à gérer comment l'ORM va lier et charger les données
    @ToString.Exclude
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tasks",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_todolist"))
    private List<Tache> tasks = new ArrayList<Tache>();
}