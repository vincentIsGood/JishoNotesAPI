package com.vincentcodes.jishoapi;

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

// https://stackoverflow.com/questions/58525387/mockmvc-no-longer-handles-utf-8-characters-with-spring-boot-2-2-0-release
@Component
public class MockMvcCharacterEncodingCustomizer implements MockMvcBuilderCustomizer {
    @Override
    public void customize(ConfigurableMockMvcBuilder<?> builder) {
        builder.alwaysDo(result -> result.getResponse().setCharacterEncoding("UTF-8"));
    }
}