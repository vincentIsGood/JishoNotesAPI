package com.vincentcodes.jishoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


// What proxyTargetClass do?
// https://stackoverflow.com/questions/45124660/the-bean-could-not-be-injected-as-a-type-because-it-is-a-jdk-dynamic-proxy-tha

/**
 * JishoNotesAPI
 * @author Vincent Ko
 */
@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class Main extends SpringApplication{
    public static void main(String... args){
        SpringApplication.run(Main.class, args);
    }
}
