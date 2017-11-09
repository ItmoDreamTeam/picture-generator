package com.smirnowku.picturegenerator.proxy.fs;

import com.smirnowku.picturegenerator.proxy.fs.exception.PictureNotFoundException;
import com.smirnowku.picturegenerator.proxy.fs.exception.UserNotFoundException;
import com.smirnowku.picturegenerator.proxy.fs.model.UploadedFile;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/fs")
@CrossOrigin
public class PictureController {

    private static final Logger log = Logger.getLogger(PictureController.class);

    @Resource
    private PictureService pictureService;

    @PostMapping
    public ResponseEntity<?> uploadPicture(@RequestParam MultipartFile picture) {
        log.info("upload picture request");
        try {
            pictureService.uploadPicture(picture);
        } catch (IOException e) {
            log.warn("error", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("upload picture request successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getPicturesMeta() {
        log.info("get pictures meta request");
        List<UploadedFile> files;
        try {
            files = pictureService.getPicturesMeta();
        } catch (UserNotFoundException e) {
            log.info("user not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            log.warn("error", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("get pictures meta request successful");
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPicture(HttpServletResponse responseToClient, @PathVariable int id) {
        log.info("get picture request id=" + id);
        try {
            pictureService.getPicture(responseToClient.getOutputStream(), id);
        } catch (PictureNotFoundException e) {
            log.info("picture not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            log.warn("error", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("get picture request successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
