/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dtos;

import java.util.List;
import java.util.stream.Collectors;
import model.entities.Collection;

/**
 *
 * @author CHRISTIAN
 */
public class CollectionSuscriptorsDTO {
    
    private List<ClientDTO> suscriptors;

    public List<ClientDTO> getSuscriptors() {
        return suscriptors;
    }

    public void setSuscriptors(List<ClientDTO> suscriptors) {
        this.suscriptors = suscriptors;
    }

    public CollectionSuscriptorsDTO() {
    }

    public CollectionSuscriptorsDTO(Collection c) {
        this.suscriptors = c.getClients().stream().map(co -> new ClientDTO(co)).collect(Collectors.toList());
    }
    
}
