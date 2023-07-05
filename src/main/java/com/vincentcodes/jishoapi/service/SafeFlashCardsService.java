package com.vincentcodes.jishoapi.service;

import com.vincentcodes.jishoapi.config.security.AuthenticationContext;
import com.vincentcodes.jishoapi.entity.AppUserObtainable;
import com.vincentcodes.jishoapi.entity.FlashCardDeck;
import com.vincentcodes.jishoapi.entity.UserDecks;
import com.vincentcodes.jishoapi.exception.DuplicateResourceNotAllowed;
import com.vincentcodes.jishoapi.exception.InvalidOperation;
import com.vincentcodes.jishoapi.exception.ResourceNotFound;
import com.vincentcodes.jishoapi.repository.FlashCardsDao;
import com.vincentcodes.jishoapi.repository.UserDecksCrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Handles different users as well
 */
@Service
@Transactional
public class SafeFlashCardsService {
    @Autowired
    private FlashCardsDao flashCardsRepo;

    @Autowired
    private UserDecksCrudDao userDecksRepo;

    @Autowired
    private AuthenticationContext authContext;

    // Remember to check for account info, when account system is available.
    public FlashCardDeck createDeck(FlashCardDeck deck){
        if(flashCardsRepo.existDeckWithUUID(deck.getDeckId()))
            throw new DuplicateResourceNotAllowed();
        FlashCardDeck newDeck = flashCardsRepo.createDeck(deck.getName());
        userDecksRepo.save(new UserDecks(getLoggedInUserId(), newDeck.getDeckId()));
        return newDeck;
    }

    public FlashCardDeck addDeck(FlashCardDeck deck){
        if(flashCardsRepo.existDeckWithUUID(deck.getDeckId()))
            throw new DuplicateResourceNotAllowed();
        FlashCardDeck newDeck = flashCardsRepo.addDeck(deck);
        userDecksRepo.save(new UserDecks(getLoggedInUserId(), newDeck.getDeckId()));
        return newDeck;
    }

    public Optional<FlashCardDeck> updateDeck(UUID deckId, FlashCardDeck deck){
        verifyIfDeckBelongsToUser(deckId);
        return flashCardsRepo.updateDeck(deckId, deck);
    }

    public void addCardsToDeck(UUID deckId, int[] entryIds){
        verifyIfDeckBelongsToUser(deckId);
        flashCardsRepo.addCardsToDeck(deckId, entryIds);
    }

    public Optional<FlashCardDeck> getDeckFromUUID(UUID deckId){
        verifyIfDeckBelongsToUser(deckId);
        return flashCardsRepo.getDeckFromUUID(deckId);
    }

    public List<FlashCardDeck> getDecks(){
        List<UserDecks> userDecks = getUserDecks();
        if(userDecks.isEmpty())
            return List.of();
        List<UserDecks> deckIds = userDecksRepo.findByUserId(getLoggedInUserId());
        return flashCardsRepo.findDecksFromIds(deckIds.stream().map(UserDecks::getDeckId).collect(Collectors.toList()));
    }

    public void removeDeck(UUID deckId){
        verifyIfDeckBelongsToUser(deckId);
        flashCardsRepo.removeDeck(deckId);
    }

    public void removeCardsFromDeck(UUID deckId, int[] entryIds){
        verifyIfDeckBelongsToUser(deckId);
        flashCardsRepo.removeCardsFromDeck(deckId, entryIds);
    }

    public void clearReviewedCards(UUID deckId){
        Optional<FlashCardDeck> deckFromUUID = getDeckFromUUID(deckId);
        if(deckFromUUID.isEmpty())
            return;
        deckFromUUID.get().getReviewedCards().clear();
    }

    private AppUserObtainable getLoggedInUserInfo(){
        if(authContext.getAuthentication().getPrincipal() instanceof AppUserObtainable)
            return (AppUserObtainable) authContext.getAuthentication().getPrincipal();
        throw new InvalidOperation("User is not authorized");
    }

    private UUID getLoggedInUserId(){
        return getLoggedInUserInfo().getAppUser().getUserId();
    }

    private List<UserDecks> getUserDecks(){
        return userDecksRepo.findByUserId(getLoggedInUserId());
    }

    private void verifyIfDeckBelongsToUser(UUID deckId){
        List<UserDecks> userDecks = getUserDecks();
        if(userDecks.size() == 0)
            throw new ResourceNotFound("User has no decks");
        for(UserDecks deck : userDecks){
            if(deck.getDeckId().equals(deckId))
                return;
        }
        throw new InvalidOperation("The deck requested does not belong to the user");
    }
}
