package com.smirnowku.picturegenerator.proxy.picgen;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@Controller
@RequestMapping("/picture/{width}/{height}")
@CrossOrigin
public class PicGenController {

    private static final Logger log = Logger.getLogger(PicGenController.class);

    @GetMapping
    public ResponseEntity<?> generate(HttpServletResponse responseToClient, @PathVariable int width, @PathVariable int height) {
        log.info("generate picture request");
        try {
            HttpGet request = new HttpGet(getUrl(width, height));
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(request);
            InputStream pictureStream = response.getEntity().getContent();
            IOUtils.copy(pictureStream, responseToClient.getOutputStream());
        } catch (Exception e) {
            log.warn("error", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("generate picture request successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getUrl(int width, int height) {
        return String.format("http://lorempixel.com/%d/%d", width, height);
    }
}
