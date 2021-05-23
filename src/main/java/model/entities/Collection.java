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
@Table(name = "collection")
public class Collection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "suscriptions", fetch = FetchType.LAZY)
    List<Client> clients;

    @OneToMany(mappedBy = "collection")
    List<Comic> comics;

    public Collection() {
    }

    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }

    public void addComic(Comic c) {
        if (comics == null) {
            comics = new ArrayList<>();
        }
        comics.add(c);
    }

    public void removeComic(Comic c) {
        if (comics == null) {
            comics.remove(c);
        }
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void addSuscriptor(Client c) {
        if (clients == null) {
            clients = new ArrayList<>();
        }
        clients.add(c);
    }

    public void removeSuscriptor(Client c) {
        if (clients == null) {
            clients.remove(c);
        }
    }

    public Collection(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return this.name;
    }

}
