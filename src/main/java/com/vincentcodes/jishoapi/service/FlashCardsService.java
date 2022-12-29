package com.vincentcodes.jishoapi.service;

import com.vincentcodes.jishoapi.entity.FlashCardDeck;
import com.vincentcodes.jishoapi.repository.FlashCardsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlashCardsService {
    @Autowired
    private FlashCardsDao repo;

    // Remember to check for account info, when account system is available.
    public FlashCardDeck createDeck(FlashCardDeck deck){
        return repo.createDeck(deck.getName());
    }

    public FlashCardDeck addDeck(FlashCardDeck deck){
        return repo.addDeck(deck);
    }

    public Optional<FlashCardDeck> updateDeck(UUID uuid, FlashCardDeck deck){
        return repo.updateDeck(uuid, deck);
    }

    public void addCardsToDeck(UUID uuid, int[] entryIds){
        repo.addCardsToDeck(uuid, entryIds);
    }

    public Optional<FlashCardDeck> getDeckFromUUID(UUID deckId){
        return repo.getDeckFromUUID(deckId);
    }

    public List<FlashCardDeck> getDecks(){
        return repo.getAllDecks();
    }

    public void removeDeck(UUID deckId){
        repo.removeDeck(deckId);
    }

    public void removeCardsFromDeck(UUID deckId, int[] entryIds){
        repo.removeCardsFromDeck(deckId, entryIds);
    }
}
