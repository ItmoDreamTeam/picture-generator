package org.smirnowku.picturegenerator.fs;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.smirnowku.picturegenerator.fs.exception.PictureNotFoundException;
import org.smirnowku.picturegenerator.fs.exception.UserNotFoundException;
import org.smirnowku.picturegenerator.fs.model.UploadedFile;
import org.smirnowku.picturegenerator.fs.model.User;
import org.smirnowku.picturegenerator.util.JsonSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureService {

    @Value("${fs.root}")
    private String rootUrl;

    @Value("${fs.username}")
    private String username;

    @Value("${fs.password}")
    private String password;

    @Resource
    private AuthorizationService auth;

    @Resource
    private JsonSerializer jsonSerializer;

    public List<UploadedFile> getPicturesMeta(int limit) throws IOException {
        HttpGet request = new HttpGet(String.format("%s/user/%s", rootUrl, username));
        auth.addAuthHeader(request, username, password);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(request);
        if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()).is4xxClientError()) {
            httpClient.close();
            throw new UserNotFoundException();
        }
        User user = jsonSerializer.deserialize(response.getEntity().getContent(), User.class);
        httpClient.close();
        return user.getFiles().stream().limit(limit).collect(Collectors.toList());
    }

    public void getPicture(OutputStream outputStream, int id) throws IOException {
        HttpGet request = new HttpGet(String.format("%s/user/%s/file/%d", rootUrl, username, id));
        auth.addAuthHeader(request, username, password);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(request);
        if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()).is4xxClientError()) {
            httpClient.close();
            throw new PictureNotFoundException();
        }
        response.getEntity().writeTo(outputStream);
        httpClient.close();
    }

    public void uploadPicture(MultipartFile picture) throws IOException {
        createUser();
        HttpPost request = new HttpPost(String.format("%s/user/%s/file", rootUrl, username));
        auth.addAuthHeader(request, username, password);
        request.setEntity(prepareFormData(picture));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        httpClient.execute(request);
        httpClient.close();
    }

    private void createUser() throws IOException {
        HttpPost request = new HttpPost(String.format("%s/signup?username=%s&password=%s", rootUrl, username, password));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        httpClient.execute(request);
        httpClient.close();
    }

    private HttpEntity prepareFormData(MultipartFile picture) throws IOException {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addBinaryBody("file", picture.getInputStream(),
                ContentType.APPLICATION_OCTET_STREAM, System.currentTimeMillis() + ".png");
        return multipartEntityBuilder.build();
    }
}
