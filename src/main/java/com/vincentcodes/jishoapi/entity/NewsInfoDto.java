package com.vincentcodes.jishoapi.entity;

import com.vincentcodes.jishoapi.utils.RandomStringGenerator;

public class NewsInfoDto {
    public String id;
    public String link;
    public String title;
    public String publisher;
    public String type;
    public String imageUrl;

    public NewsInfoDto() {
        this.id = RandomStringGenerator.generate(6);
    }
}
