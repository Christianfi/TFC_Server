/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dtos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import model.entities.Comic;



/**
 *
 * @author CHRISTIAN
 */
public class ComicDTO implements Serializable{
    
    private int id;
    
    private String name;
    
    private String publishDate;
    
    private String state;
    
    private int number;
    
    private String publisher;
    
    private String isbn;
    
    private String collectionName;
    
    private int collectionId;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public ComicDTO(Comic c) {
        this.id = c.getId();
        this.name = c.getName();
        this.publishDate = sdf.format(c.getPublishDate());
        this.state = c.getState();
        this.number = c.getNumber();
        this.publisher = c.getPublisher();
        this.isbn = c.getIsbn();
        this.collectionId = c.getCollection().getId();
        this.collectionName = c.getCollection().getName();
    }

    public ComicDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    
}
