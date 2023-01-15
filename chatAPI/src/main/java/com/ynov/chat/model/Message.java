package com.ynov.chat.model;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Friends sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Friends receiver;

    // Constructeur vide requis par Hibernate
    public Message() {
    }

    public Message(String content, Friends sender, Friends receiver) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
    }

    public long getId() {
        return id;
    }
}
