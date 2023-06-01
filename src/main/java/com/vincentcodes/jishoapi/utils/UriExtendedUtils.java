package com.vincentcodes.jishoapi.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class UriExtendedUtils {
    /**
     * @return null if uri cannot be converted to a URL
     */
    public static String getOrigin(URI uri){
        try {
            URL url = uri.toURL();
            return url.getProtocol() + "://" + url.getAuthority();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * @return null if url is incorrect
     */
    public static String getOrigin(String urlStr){
        try {
            URL url = new URL(urlStr);
            return url.getProtocol() + "://" + url.getAuthority();
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
