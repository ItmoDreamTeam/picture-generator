package com.smirnowku.picturegenerator.proxy.fs;

import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthorizationService {

    String authString(String username, String password);

    default void addAuthHeader(HttpUriRequest request, String username, String password) {
        request.addHeader("Authorization", authString(username, password));
    }
}
