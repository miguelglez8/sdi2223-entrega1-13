package com.uniovi.sdimywallapop.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Offer {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Date creationDate;
    private String details;
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Offer() {
    }

    public Offer(Long id, String title, String description, Date creationDate, String details, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.details = details;
        this.price = price;
    }

    public Offer(String title, String description, String details, double price){
        super();
        this.title = title;
        this.description = description;
        this.details = details;
        this.creationDate = new Date();
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
