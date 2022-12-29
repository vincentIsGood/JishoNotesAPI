package com.vincentcodes.jishoapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class APIEntriesV2Test {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void test_getNoteById_then_exist() throws Exception {
        mockMvc.perform(get("/jishonotes/v2/entries/id/" + 2011530))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"sequence\":2011530,\"headword\":[\"寝転がる\"],\"readings\":[\"ねころがる\"],\"meanings\":[\"to lie down\",\"to throw oneself down\"]}"));
    }

    @Test
    public void test_getNotesByIds_then_exist() throws Exception {
        mockMvc.perform(post("/jishonotes/v2/entries/ids")
                    .contentType(MediaType.APPLICATION_JSON)
                    //.characterEncoding(StandardCharsets.UTF_8) // used customizer
                    .content("[2011530,1276080,1400790]"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"sequence\":2011530,\"meanings\":[\"to lie down\",\"to throw oneself down\"],\"headword\":[\"寝転がる\"],\"readings\":[\"ねころがる\"]},{\"sequence\":1276080,\"meanings\":[\"spoken language\",\"colloquial speech\",\"(modern) spoken Japanese\",\"written style based on (modern) spoken Japanese\"],\"headword\":[\"口語\"],\"readings\":[\"こうご\"]},{\"sequence\":1400790,\"meanings\":[\"enduring something out of pride\",\"putting up with it\",\"grinning and bearing it\"],\"headword\":[\"やせ我慢\",\"痩せ我慢\",\"痩我慢\"],\"readings\":[\"やせがまん\"]}]"));
    }

    @Test
    public void test_adv_search_then_matches_no_of_records() throws Exception {
        mockMvc.perform(get("/jishonotes/v2/entries/adv?search=ずるい"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"sequence\":1569240,\"headword\":[\"狡い\"],\"meanings\":[\"sly\",\"cunning\",\"sneaky\",\"crafty\",\"unfair\",\"dishonest\",\"miserly\",\"mean\",\"stingy\"],\"readings\":[\"ずるい\",\"こすい\"]}]"));
    }

    @Test
    public void test_getTopics_then_matches_no_of_records() throws Exception {
        mockMvc.perform(post("/jishonotes/v2/entries/kanji")
                        .content("若"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"literal\":\"若\",\"reading_meaning\":{\"rmgroup\":[{\"reading\":[\"ジャク\",\"ニャク\",\"ニャ\",\"わか.い\",\"わか-\",\"も.しくわ\",\"も.し\",\"も.しくは\",\"ごと.し\"],\"meaning\":[\"young\",\"if\",\"perhaps\",\"possibly\",\"low number\",\"immature\"]}],\"nanori\":[\"わく\",\"わこ\"]}}]"));
    }
}
