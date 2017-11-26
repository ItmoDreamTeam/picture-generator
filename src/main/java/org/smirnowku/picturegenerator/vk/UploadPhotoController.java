package org.smirnowku.picturegenerator.vk;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/vk/upload")
@CrossOrigin
public class UploadPhotoController {

    private static final Logger log = Logger.getLogger(UploadPhotoController.class);

    @PostMapping
    public ResponseEntity<?> upload(HttpServletResponse responseToClient,
                                    @RequestParam String url, @RequestParam MultipartFile photo) {
        log.info("upload photo request");
        try {
            HttpPost request = new HttpPost(url);
            request.setEntity(prepareFormData(photo));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(request);
            response.getEntity().writeTo(responseToClient.getOutputStream());
            httpClient.close();
        } catch (Exception e) {
            log.warn("error", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("upload photo request successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private HttpEntity prepareFormData(MultipartFile photo) throws Exception {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addBinaryBody("photo", photo.getInputStream(),
                ContentType.APPLICATION_OCTET_STREAM, ".png");
        return multipartEntityBuilder.build();
    }
}
