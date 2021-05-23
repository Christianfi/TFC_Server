/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dtos;

import java.util.List;
import java.util.stream.Collectors;
import model.entities.Client;

/**
 *
 * @author CHRISTIAN
 */
public class ClientSuscriptionsDTO {
    
    private List<CollectionDTO> collections;

    public List<CollectionDTO> getCollections() {
        return collections;
    }

    public void setCollections(List<CollectionDTO> collections) {
        this.collections = collections;
    }

    public ClientSuscriptionsDTO() {
    }

    public ClientSuscriptionsDTO(Client c) {
        this.collections = c.getSuscriptions().stream().map(co -> new CollectionDTO(co)).collect(Collectors.toList());
    }
    
}
