package com.vincentcodes.jishoapi.config.security;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;

public class SSONameCodec {
    public static class CompositeName implements Serializable {
        public final String provider;
        public final String uniqueId;

        public CompositeName(String provider, String uniqueId) {
            this.provider = provider;
            this.uniqueId = uniqueId;
        }
    }

    /**
     * Create unique name (eg. "google_vincent32132Id")
     */
    public static String encodeUniqueName(ClientRegistration clientRegistration, OAuth2User oauth2User){
        // To ensure the uniqueness of the username, we use normally-prohibited character "\"
        return clientRegistration.getClientName() + "\\" + oauth2User.getName();
    }

    /**
     * @return null if name is invalid or none is found
     */
    public static CompositeName decode(String encodedUniqueName){
        String[] splited = encodedUniqueName.split("\\\\");
        if(splited.length <= 2)
            return null;
        return new CompositeName(splited[0], splited[1]);
    }
}
