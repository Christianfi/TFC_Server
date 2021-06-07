/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dtos;

import java.io.Serializable;
import model.entities.Collection;

/**
 *
 * @author CHRISTIAN
 */
public class CollectionDTO implements Serializable{
    
    private int id;
    
    private String name;
    
    private String imageURL;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public CollectionDTO() {
    }

    public CollectionDTO(Collection c) {
        this.id = c.getId();
        this.name = c.getName();
        this.imageURL = c.getImageURL();
    }
    
    
}
