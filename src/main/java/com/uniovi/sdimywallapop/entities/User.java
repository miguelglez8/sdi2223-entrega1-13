package com.uniovi.sdimywallapop.entities;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String dni;
    private String name;
    private String lastName;
    private String role;
    private double money;
    private String password;

    @Transient //propiedad que no se almacena en la tabla.
    private String passwordConfirm;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Offer> offers;

    public User(String dni, String name, String lastName) {
        super();
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.money = 100.0;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPasswordConfirm() {
        return passwordConfirm;
    }
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public User() { }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDni() {return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFullName() {
        return this.name + " " + this.lastName;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Set<Offer> getOffers() {
        return offers;
    }
    public void setOffers(Set user1Offers) {
        this.offers = user1Offers;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void decrementMoney(double price) {
        this.money -= price;
    }
}
