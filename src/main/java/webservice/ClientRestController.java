/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import model.dao.IClientService;
import model.dao.ICollectionService;
import model.dtos.ClientDTO;
import model.dtos.ClientSuscriptionsDTO;
import model.dtos.SuscriptionDTO;
import model.entities.Client;
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
@RequestMapping("/clients")
public class ClientRestController {

    @Autowired
    private IClientService clientService;

    @Autowired
    private ICollectionService collectionService;

    public IClientService getClientService() {
        return clientService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ClientDTO> getClients() {
        return clientService.findAll().stream().map(c -> new ClientDTO(c)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public ClientDTO getClient(@PathVariable int id) {
        Optional<Client> op = clientService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
            return null;
        }

        return new ClientDTO(op.get());
    }

    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public void insertClient(@RequestBody Client c) {
        clientService.save(c);

        //TODO SEND CREATED STATUS
    }

    @RequestMapping(value = "/client/{id}/suscriptions", method = RequestMethod.GET)
    public ClientSuscriptionsDTO getSuscriptions(@PathVariable int id) {
        Optional<Client> op = clientService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
            return null;
        }

        return new ClientSuscriptionsDTO(op.get());
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.PUT)
    public void updateClient(@PathVariable int id, @RequestBody Client c) {
        Optional<Client> op = clientService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
        } else {
            c.setId(id);
            clientService.save(c);
        }
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.DELETE)
    public void deleteClient(@PathVariable int id) {
        Optional<Client> op = clientService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
        } else {
            clientService.delete(op.get());
        }

    }

    @RequestMapping(value = "/client/{id}/suscription", method = RequestMethod.POST)
    public void addSuscription(@PathVariable int id, @RequestBody SuscriptionDTO suscription) {

        Optional<Client> client = clientService.findById(id);
        Optional<Collection> collection = collectionService.findById(suscription.getCollectionId());
        if (!client.isPresent() || !collection.isPresent()) {
            System.out.println("error");
        } else {
            client.get().addSuscription(collection.get());
            collection.get().addSuscriptor(client.get());
            clientService.save(client.get());
        }

    }
    
    @RequestMapping(value = "/client/{id}/suscription", method = RequestMethod.DELETE)
    public void deleteSuscription(@PathVariable int id, @RequestBody SuscriptionDTO suscription) {

        Optional<Client> client = clientService.findById(id);
        Optional<Collection> collection = collectionService.findById(suscription.getCollectionId());
        if (!client.isPresent() || !collection.isPresent()) {
            System.out.println("error");
        } else {
            client.get().getSuscriptions().remove(collection.get());
            collection.get().removeSuscriptor(client.get());
            clientService.save(client.get());
        }

    }

}
