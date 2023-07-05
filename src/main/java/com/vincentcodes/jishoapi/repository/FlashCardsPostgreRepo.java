package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.entity.FlashCardDeck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//@Repository("PostgreSQL") // see config
@Transactional
public class FlashCardsPostgreRepo implements FlashCardsDao {
    // TODO: Using FlashCardsPostgreRepo as proxy is not really a good idea
    /// problem: think about integrating FlashCardsDao with JpaRepository / CrudRepository
    @Autowired
    private FlashCardsCrudDao dao;

    @Override
    public Optional<FlashCardDeck> getDeckFromUUID(UUID deckId) {
        return dao.findById(deckId);
    }

    @Override
    public boolean existDeckWithUUID(UUID deckId) {
        return dao.existsById(deckId);
    }

    @Override
    public List<FlashCardDeck> findDecksFromIds(List<UUID> deckIds) {
        return dao.findByDeckIdIn(deckIds);
    }

    @Override
    public FlashCardDeck createDeck(String name) {
        FlashCardDeck deck = new FlashCardDeck(name);
        return dao.save(deck);
    }

    @Override
    public Optional<FlashCardDeck> updateDeck(UUID deckId, FlashCardDeck newDeck) {
        Optional<FlashCardDeck> optionalDeck = dao.findById(deckId);
        if(optionalDeck.isPresent()){
            FlashCardDeck deck = optionalDeck.get();
            if(newDeck.getName() != null)
                deck.setName(newDeck.getName());
            if(newDeck.getDescription() != null)
                deck.setDescription(newDeck.getDescription());
            if(newDeck.getCardSet() != null)
                deck.setCards(newDeck.getCardSet());
            dao.save(deck);
            return Optional.of(deck);
        }
        return Optional.empty();
    }

    @Override
    public FlashCardDeck addDeck(FlashCardDeck deck) {
        return dao.save(deck);
    }

    @Override
    public void addCardsToDeck(UUID deckId, int[] cards) {
        Optional<FlashCardDeck> optionalDeck = dao.findById(deckId);
        if(optionalDeck.isPresent()){
            FlashCardDeck deck = optionalDeck.get();
            deck.addCards(cards);
            dao.save(deck);
        }
    }

    @Override
    public void removeDeck(UUID deckId) {
        dao.deleteById(deckId);
    }

    @Override
    public void removeCardsFromDeck(UUID deckId, int[] cards) {
        Optional<FlashCardDeck> optionalDeck = dao.findById(deckId);
        if(optionalDeck.isPresent()){
            FlashCardDeck deck = optionalDeck.get();
            deck.deleteCards(cards);
            dao.save(deck);
        }
    }
}
