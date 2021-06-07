/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.dao.ICollectionService;
import model.dtos.ClientDTO;
import model.dtos.CollectionComicsDTO;
import model.dtos.CollectionDTO;
import model.dtos.CollectionSuscriptorsDTO;
import model.dtos.ComicDTO;
import model.entities.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utils.FileUploadUtil;

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
    public int insertCollection(@RequestBody Collection c) {
        return collectionService.save(c).getId();
        //TODO SEND CREATED STATUS
    }

    @RequestMapping(value = "/collection/{id}", method = RequestMethod.PUT)
    public int updateCollection(@PathVariable int id, @RequestBody Collection c) {
        Optional<Collection> op = collectionService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
        } else {
            c.setId(id);
            collectionService.save(c);
            return c.getId();
        }
        
        return 0;
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
    public List<ClientDTO> getSuscriptions(@PathVariable int id) {
        Optional<Collection> op = collectionService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
            return null;
        }
        return new CollectionSuscriptorsDTO(op.get()).getSuscriptors();
    }

    @RequestMapping(value = "/collection/{id}/comics", method = RequestMethod.GET)
    public List<ComicDTO> getCollectionComics(@PathVariable int id) {
        Optional<Collection> op = collectionService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
            return null;
        }
        return new CollectionComicsDTO(op.get()).getComics();
    }

    @RequestMapping(value = "/collection/{id}/image", method = RequestMethod.POST)
    public void uploadImage(@PathVariable int id, @RequestParam("image") MultipartFile image) {
        try {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            Collection collection = collectionService.findById(id).get();
            collection.setImageURL(fileName);
            collectionService.save(collection);
            FileUploadUtil.saveFile(System.getProperty("user.home")+"/Pictures/collections-images/" + id, fileName, image);
        } catch (IOException ex) {
            Logger.getLogger(ComicRestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping(value = "/collection/{id}/image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@PathVariable int id) throws IOException {
        Collection comic = collectionService.findById(id).get();
        if (comic.getImageURL() != null) {
            InputStream in = new FileInputStream(System.getProperty("user.home")+"/Pictures/collections-images/" + id + "/" + comic.getImageURL());
            return in.readAllBytes();
        }
        return null;
    }

}
