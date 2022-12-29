package com.vincentcodes.jishoapi.controller;

import com.vincentcodes.jishoapi.entity.FlashCardDeck;
import com.vincentcodes.jishoapi.service.FlashCardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("jishonotes/v1/flashcards")
public class FlashCardsController {

    @Autowired
    private FlashCardsService service;

    @GetMapping("/decks")
    public List<FlashCardDeck> getDecks(){
        return service.getDecks();
    }

    @GetMapping("/decks/{uuid}")
    public FlashCardDeck getDeckFromName(@PathVariable("uuid") UUID uuid){
        Optional<FlashCardDeck> deck = service.getDeckFromUUID(uuid);
        if(deck.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find the specified flash card deck");
        return deck.get();
    }

    @PostMapping("/decks")
    public FlashCardDeck createDeck(@RequestBody FlashCardDeck deck){
        if(deck.getName() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'name' field cannot be null");
        if(deck.getCards() == null)
            return service.createDeck(deck);
        if(deck.getDeckId() == null)
            return service.addDeck(new FlashCardDeck(deck.getName(), deck.getCardSet()));
        return service.addDeck(deck);
    }

    @PostMapping("/decks/{uuid}")
    public FlashCardDeck updateDeck(@PathVariable("uuid") UUID uuid, @RequestBody FlashCardDeck deck){
        Optional<FlashCardDeck> newDeck = service.updateDeck(uuid, deck);
        if(newDeck.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find the specified flashcard deck");
        return newDeck.get();
    }

    @PutMapping("/decks/{uuid}")
    public void addCardsToDeck(@PathVariable("uuid") UUID uuid, @RequestBody int[] entryIds){
        service.addCardsToDeck(uuid, entryIds);
    }

    @DeleteMapping("/decks/{uuid}")
    public void removeCardOrDeck(@PathVariable("uuid") UUID uuid, @RequestBody(required = false) int[] entryId){
        if(entryId != null) {
            service.removeCardsFromDeck(uuid, entryId);
        }else
            service.removeDeck(uuid);
    }
}
