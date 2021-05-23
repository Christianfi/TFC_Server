/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dtos;

import java.io.Serializable;
import model.entities.Client;

/**
 *
 * @author CHRISTIAN
 */
public class ClientDTO implements Serializable{
    
    private int id;
    
    private String name;
    
    private String lastname;
    
    private String dni;
    
    private String email;
    
    private String tlf;

    public ClientDTO(Client c) {
        this.id = c.getId();
        this.name = c.getName();
        this.lastname = c.getLastname();
        this.dni = c.getDni();
        this.email = c.getEmail();
        this.tlf = c.getTlf();
    }

    public ClientDTO() {
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
