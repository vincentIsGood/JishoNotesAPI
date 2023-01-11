package com.vincentcodes.jishoapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincentcodes.jishoapi.entity.FlashCardDeck;
import com.vincentcodes.jishoapi.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @link https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/authentication.html
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "NORMAL_USER")
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APIFlashcardTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserService userService;

    private String deckId;

    @Test
    @Order(1)
    public void test_add_deck_then_give_valid_response() throws Exception {
        mockMvc.perform(post("/jishonotes/v1/flashcards/decks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Demo Card Pack\",\"cards\": [1154340, 1000750, 1318000, 1326820, 1155710]}"))
                .andExpect(res -> {
                    FlashCardDeck deck = mapper.readValue(res.getResponse().getContentAsString(), FlashCardDeck.class);
                    deckId = deck.getDeckId().toString();
                    assertThat(res.getResponse().getContentAsString())
                            .contains("\"name\":\"Demo Card Pack\"", "\"cards\":[1326820,1000750,1318000,1154340,1155710]");
                });
    }

    @Test
    @Order(2)
    public void test_update_deck_then_give_valid_response() throws Exception {
        mockMvc.perform(post("/jishonotes/v1/flashcards/decks/" + deckId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"New Card Pack\"}"))
                .andExpect(res -> assertThat(res.getResponse().getContentAsString())
                        .contains("\"name\":\"New Card Pack\""));
    }

    @Test
    @Order(3)
    public void test_addCardToDeck_then_is_added() throws Exception {
        mockMvc.perform(put("/jishonotes/v1/flashcards/decks/" + deckId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1258000, 1499610]"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/jishonotes/v1/flashcards/decks/" + deckId))
                .andExpect(status().isOk())
                .andExpect(res -> assertThat(res.getResponse().getContentAsString())
                        .contains("\"cards\":[1258000,1499610]"));
    }

    @Test
    @Order(4)
    public void test_remove_card_from_deck_then_is_removed() throws Exception {
        mockMvc.perform(delete("/jishonotes/v1/flashcards/decks/" + deckId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1258000]"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/jishonotes/v1/flashcards/decks/" + deckId))
                .andExpect(status().isOk())
                .andExpect(res -> assertThat(res.getResponse().getContentAsString())
                        .contains("\"cards\":[1499610]"));
    }

    @Test
    @Order(5)
    public void test_remove_deck_then_is_removed() throws Exception {
        mockMvc.perform(delete("/jishonotes/v1/flashcards/decks/" + deckId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/jishonotes/v1/flashcards/decks/" + deckId))
                .andExpect(status().isNotFound());
    }
}
