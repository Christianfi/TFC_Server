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
public class CollectionComicsDTO {
    
    private List<ComicDTO> comics;

    public List<ComicDTO> getComics() {
        return comics;
    }

    public void setComics(List<ComicDTO> comics) {
        this.comics = comics;
    }

    public CollectionComicsDTO() {
    }

    public CollectionComicsDTO(Collection c) {
        this.comics = c.getComics().stream().map(co -> new ComicDTO(co)).collect(Collectors.toList());
    }
    
    
}
