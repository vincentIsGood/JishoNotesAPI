package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.entity.FlashCardDeck;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * May add individual user decks support
 */
public interface FlashCardsDao {
    Optional<FlashCardDeck> getDeckFromUUID(UUID deckId);

    boolean existDeckWithUUID(UUID deckId);

    List<FlashCardDeck> findDecksFromIds(List<UUID> deckIds);

    FlashCardDeck createDeck(String name);

    Optional<FlashCardDeck> updateDeck(UUID deckId, FlashCardDeck newDeck);

    FlashCardDeck addDeck(FlashCardDeck deck);

    void addCardsToDeck(UUID deckId, int[] cards);

    void removeDeck(UUID deckId);

    void removeCardsFromDeck(UUID deckId, int[] cards);
}
