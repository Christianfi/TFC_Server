/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author CHRISTIAN
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "dni")
    private String dni;

    @Column(name = "email")
    private String email;

    @Column(name = "tlf")
    private String tlf;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "suscription",
            joinColumns = @JoinColumn(name = "clientId", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "collectionId", nullable = false)
    )
    List<Collection> suscriptions;

    public List<Collection> getSuscriptions() {
        return suscriptions;
    }

    public void setSuscriptions(List<Collection> suscriptions) {
        this.suscriptions = suscriptions;
    }
    
    public void addSuscription(Collection c){
        if(suscriptions == null){
            suscriptions = new ArrayList<>();
        }
        suscriptions.add(c);
    }

    public Client() {
    }

    public Client(String name, String lastname, String dni, String email, String tlf) {
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.email = email;
        this.tlf = tlf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

}
