package com.uniovi.sdimywallapop.entities;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @ManyToOne
    private User user;

    @ManyToOne
    private Conversation conversation;

    public Message() { }

    public Message(String text, User user, Conversation conversation) {
        this.text = text;
        this.user = user;
        this.conversation = conversation;
        conversation.addMessage(this);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
