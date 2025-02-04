/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import model.entities.Client;
import model.entities.Collection;
import model.entities.Comic;
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
import utils.EmailService;
import utils.FileUploadUtil;

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
    public int insertComic(@RequestBody ComicDTO cDTO) {

        Comic createdComic = comicService.save(dtoToComic(cDTO));
        sendNewComicEmail(createdComic);
        return createdComic.getId();
        //TODO SEND CREATED STATUS
    }

    private void sendNewComicEmail(Comic c) {
        int size = c.getCollection().getClients().size();

        if (size > 0) {
            EmailService emailService = new EmailService();
            String[] emails = new String[size];
            int i = 0;
            for (Client client : c.getCollection().getClients()) {
                emails[i] = client.getEmail();
                i++;
            }

            emailService.sendNewComicNotification(emails, c);
        }
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

    @RequestMapping(value = "/comic/{id}/image", method = RequestMethod.POST)
    public void uploadImage(@PathVariable int id, @RequestParam("image") MultipartFile image) {
        try {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            Comic comic = comicService.findById(id).get();
            comic.setImageURL(fileName);
            comicService.save(comic);
            FileUploadUtil.saveFile(System.getProperty("user.home")+"/Pictures/comics-images/" + id, fileName, image);
        } catch (IOException ex) {
            Logger.getLogger(ComicRestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping(value = "/comic/{id}/image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@PathVariable int id) throws IOException {
        ComicDTO comic = new ComicDTO(comicService.findById(id).get());
        if (comic.getImageURL() != null) {
            InputStream in = new FileInputStream(System.getProperty("user.home")+"/Pictures/comics-images/" + id + "/" + comic.getImageURL());
            return in.readAllBytes();
        }

        return null;
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
                    cDTO.getNumber(), cDTO.getPublisher(), cDTO.getIsbn(), collection.get(), cDTO.getImageURL());
        } catch (ParseException ex) {
            Logger.getLogger(ComicRestController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
