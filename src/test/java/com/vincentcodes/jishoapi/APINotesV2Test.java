package com.vincentcodes.jishoapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class APINotesV2Test {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void test_getRandomNote_then_exist() throws Exception {
        mockMvc.perform(get("/jishonotes/v2/notes/rand"))
                .andExpect(status().isOk())
                .andExpect(res -> mapper.readValue(res.getResponse().getContentAsString(), JsonNode.class));
    }

    @Test
    public void test_getNoteById_then_exist() throws Exception {
        mockMvc.perform(get("/jishonotes/v2/notes/id/" + 2011530))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"type\":100,\"value\":2011530,\"note\":\"4\\nto lie down (intentional lie down)\"}"));
    }

    @Test
    public void test_getNotesByIds_then_exist() throws Exception {
        mockMvc.perform(post("/jishonotes/v2/notes/ids")
                    .contentType(MediaType.APPLICATION_JSON)
                    //.characterEncoding(StandardCharsets.UTF_8) // used customizer
                    .content("[2011530,1276080,1400790]"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"type\":100,\"value\":2011530,\"note\":\"4\\nto lie down (intentional lie down)\"},{\"type\":100,\"value\":1276080,\"note\":\"0\"},{\"type\":100,\"value\":1400790,\"note\":\"3\\n\\nsee 悟る(さとる)\"}]"));
    }

    @Test
    public void test_adv_search_then_matches_no_of_records() throws Exception {
        mockMvc.perform(get("/jishonotes/v2/notes/adv?strict=true&pitch=true&search=プリコネ"))
                .andExpect(status().isOk())
                .andExpect(res -> {
                    ArrayNode node = mapper.readValue(res.getResponse().getContentAsString(), ArrayNode.class);
                    assertThat(node.size()).isGreaterThan(480);
                });
    }

    @Test
    public void test_getTopics_then_matches_no_of_records() throws Exception {
        mockMvc.perform(get("/jishonotes/v2/notes/topics"))
                .andExpect(status().isOk())
                .andExpect(res -> {
                    ArrayNode node = mapper.readValue(res.getResponse().getContentAsString(), ArrayNode.class);
                    assertThat(node.size()).isGreaterThan(900);
                });
    }
}
