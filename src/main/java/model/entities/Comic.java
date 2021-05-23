/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author CHRISTIAN
 */
@Entity
@Table(name="comic")
public class Comic implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="publishDate")
    @Temporal(value=TemporalType.DATE)
    private Date publishDate;
    
    @Column(name="state")
    private String state;
    
    @Column(name="number")
    private int number;
    
    @Column(name="publisher")
    private String publisher;
    
    @Column(name="isbn")
    private String isbn;
    
    @Column(name="imageURL")
    private String imageURL;
    
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name="collection")
    private Collection collection;

    public Comic() {
    }

    public Comic(String name, Date publishDate, String state, int number, String publisher,String isbn, Collection collection, String imageURL) {
        this.name = name;
        this.publishDate = publishDate;
        this.state = state;
        this.number = number;
        this.publisher = publisher;
        this.isbn = isbn;
        this.collection = collection;
        this.imageURL = imageURL;
    }

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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
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

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
    
    
}
