/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.dao.ICollectionService;
import model.dao.IComicService;
import model.dtos.ComicDTO;
import model.entities.Collection;
import model.entities.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author CHRISTIAN
 */
@RestController
@RequestMapping("/comics")
public class ComicRestController {

    @Autowired
    private IComicService comicService;

    @Autowired
    private ICollectionService collectionService;

    @RequestMapping(method = RequestMethod.GET)
    public List<ComicDTO> getComics() {
        return comicService.findAll().stream().map(c -> new ComicDTO(c)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/comic/{id}", method = RequestMethod.GET)
    public ComicDTO getComics(@PathVariable int id) {
        Optional<Comic> op = comicService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
            return null;
        }

        return new ComicDTO(op.get());
    }

    @RequestMapping(value = "/comic", method = RequestMethod.POST)
    public void insertComic(@RequestBody ComicDTO cDTO) {

        comicService.save(dtoToComic(cDTO));

        //TODO SEND CREATED STATUS
    }

    @RequestMapping(value = "/comic/{id}", method = RequestMethod.PUT)
    public void updateComic(@PathVariable int id, @RequestBody ComicDTO cDTO) {
        Optional<Comic> op = comicService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
        } else {
            Comic c = dtoToComic(cDTO);
            c.setId(id);
            comicService.save(c);
        }
    }

    @RequestMapping(value = "/comic/{id}", method = RequestMethod.DELETE)
    public void deleteComic(@PathVariable int id) {
        Optional<Comic> op = comicService.findById(id);
        if (!op.isPresent()) {
            //TODO EXCEPTION NOT FOUND
        } else {
            comicService.delete(op.get());
        }
    }

    public IComicService getComicService() {
        return comicService;
    }

    public Comic dtoToComic(ComicDTO cDTO) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Optional<Collection> collection = collectionService.findById(cDTO.getCollectionId());
        if (!collection.isPresent()) {
            return null;
        }

        try {
            return new Comic(cDTO.getName(), sdf.parse(cDTO.getPublishDate()), cDTO.getState(),
                    cDTO.getNumber(), cDTO.getPublisher(), cDTO.getIsbn(), collection.get());
        } catch (ParseException ex) {
            Logger.getLogger(ComicRestController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
