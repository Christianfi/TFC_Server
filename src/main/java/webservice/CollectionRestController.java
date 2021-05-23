/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import model.dao.ICollectionService;
import model.dtos.CollectionComicsDTO;
import model.dtos.CollectionDTO;
import model.dtos.CollectionSuscriptorsDTO;
import model.entities.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author CHRISTIAN
 */
@RestController
@RequestMapping("/collections")
public class CollectionRestController {

    @Autowired
    private ICollectionService collectionService;

    @RequestMapping(method = RequestMethod.GET)
    public List<CollectionDTO> getCollections() {
        return collectionService.findAll().stream().map(c -> new CollectionDTO(c)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/collection/{id}", method = RequestMethod.GET)
    public CollectionDTO getCollection(@PathVariable int id) {
        Optional<Collection> op = collectionService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
            return null;
        }

        return new CollectionDTO(op.get());
    }

    @RequestMapping(value = "/collection", method = RequestMethod.POST)
    public void insertCollection(@RequestBody Collection c) {
        collectionService.save(c);

        //TODO SEND CREATED STATUS
    }

    @RequestMapping(value = "/collection/{id}", method = RequestMethod.PUT)
    public void updateCollection(@PathVariable int id, @RequestBody Collection c) {
        Optional<Collection> op = collectionService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
        } else {
            c.setId(id);
            collectionService.save(c);
        }
    }

    @RequestMapping(value = "/collection/{id}", method = RequestMethod.DELETE)
    public void deleteCollection(@PathVariable int id) {
        Optional<Collection> op = collectionService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
        } else {
            collectionService.delete(op.get());
        }

    }

    public ICollectionService getCollectionService() {
        return collectionService;
    }

    @RequestMapping(value = "/collection/{id}/suscriptions", method = RequestMethod.GET)
    public CollectionSuscriptorsDTO getSuscriptions(@PathVariable int id) {
        Optional<Collection> op = collectionService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
            return null;
        }

        return new CollectionSuscriptorsDTO(op.get());
    }
    
    @RequestMapping(value = "/collection/{id}/comics", method = RequestMethod.GET)
    public CollectionComicsDTO getCollectionComics(@PathVariable int id) {
        Optional<Collection> op = collectionService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
            return null;
        }

        return new CollectionComicsDTO(op.get());
    }

}
