package com.vincentcodes.jishoapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Deprecated
@CrossOrigin
@RestController
@RequestMapping("jishonotes/v1/gpt")
public class GptTextController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String generate(@RequestParam("prompt") String prompt){
        return "";
        //ResponseEntity<String> gptResponse = restTemplate.getForEntity("http://gpt.service.jisho:8080/gpt?prompt={prompt}", String.class, prompt);
        //return gptResponse.getBody();
    }
}
