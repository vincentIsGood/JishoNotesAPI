package com.vincentcodes.jishoapi.helpers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectionUtils {
    public static boolean isReachable(String host, int port, int connectTimeout){
        try(Socket sock = new Socket()){
            sock.connect(new InetSocketAddress(host, port), connectTimeout);
        }catch (IOException e){
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean isReachable(String host, int port){
        return isReachable(host, port, 0);
    }
}
