package com.vincentcodes.jishoapi.service;

import com.vincentcodes.jishoapi.config.security.AuthenticationContext;
import com.vincentcodes.jishoapi.entity.AppUserObtainable;
import com.vincentcodes.jishoapi.entity.CardReviewGame;
import com.vincentcodes.jishoapi.entity.FlashCardDeck;
import com.vincentcodes.jishoapi.exception.InvalidOperation;
import com.vincentcodes.jishoapi.repository.CardReviewGameCrudRepo;
import com.vincentcodes.jishoapi.repository.FlashCardsCrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class CardReviewGameService {
    @Autowired
    private CardReviewGameCrudRepo gameRepo;

    @Autowired
    private FlashCardsCrudDao flashCardsCrudDao;

    @Autowired
    private AuthenticationContext authContext;

    public CardReviewGame createGame(UUID deckId, int numberOfEntries){
        Optional<FlashCardDeck> deckOption = flashCardsCrudDao.findById(deckId);
        if(deckOption.isEmpty()) return null;

        Set<Integer> randomEntries = flashCardsCrudDao.getRandomEntriesExceptReviewed(deckId.toString(), numberOfEntries);
        if(randomEntries.size() == 0){
            CardReviewGame emptyGame = new CardReviewGame();
            emptyGame.finish();
            return emptyGame;
        }
        return gameRepo.save(new CardReviewGame(authContext.getLoggedInUserId(), deckId, randomEntries));
    }

    public List<CardReviewGame> getGames(){
        return gameRepo.findByUserId(authContext.getLoggedInUserId());
    }

    public CardReviewGame getGame(UUID gameId){
        Optional<CardReviewGame> gameOptional = gameRepo.findById(gameId);
        return gameOptional.isEmpty()? null : gameOptional.get();
    }

    public void finishGame(UUID gameId){
        Optional<CardReviewGame> gameOptional = gameRepo.findById(gameId);
        if(gameOptional.isEmpty()) return;
        CardReviewGame reviewGame = gameOptional.get();

        Optional<FlashCardDeck> deckOption = flashCardsCrudDao.findById(reviewGame.getDeckId());
        if(deckOption.isEmpty()) return;
        FlashCardDeck deck = deckOption.get();

        reviewGame.finish();
        deck.addReviewedCards(reviewGame.getEntriesToReview());
        flashCardsCrudDao.save(deck);
        gameRepo.save(reviewGame);
    }

    public void deleteGame(UUID gameId){
        Optional<CardReviewGame> gameOptional = gameRepo.findById(gameId);
        if(gameOptional.isEmpty()) return;

        gameRepo.deleteById(gameId);
    }

    public void deleteUnfinishedGames(){
        gameRepo.deleteByFinishedAndUserId(false, authContext.getLoggedInUserId());
    }
}
