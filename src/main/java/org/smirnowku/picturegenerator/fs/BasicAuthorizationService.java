package org.smirnowku.picturegenerator.fs;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthorizationService implements AuthorizationService {

    @Override
    public String authString(String username, String password) {
        return "Basic " + basicAuth(username, password);
    }

    private static String basicAuth(String username, String password) {
        return encodeString(String.format("%s:%s", username, password));
    }

    private static String encodeString(String s) {
        return new String(Base64.encodeBase64(s.getBytes()));
    }
}
