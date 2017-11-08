package com.smirnowku.picturegenerator.proxy.vk;

import com.smirnowku.picturegenerator.proxy.util.InMemoryResource;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/vk/upload")
@CrossOrigin
public class UploadPhotoController {

    private static final Logger log = Logger.getLogger(UploadPhotoController.class);

    @PostMapping
    public ResponseEntity upload(@RequestParam String url, @RequestParam MultipartFile photo) {
        log.info("upload photo");

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        try {
            params.add("photo", new InMemoryResource(photo.getOriginalFilename(), photo.getInputStream()));
        } catch (IOException e) {
            log.warn("error when accessing photo input stream");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(params, null), (Class<?>) null);
        } catch (RestClientException e) {
            log.warn("error when sending post request to VK");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
