package com.vincentcodes.jishoapi.controller;

import com.vincentcodes.jishoapi.entity.CardReviewGame;
import com.vincentcodes.jishoapi.service.CardReviewGameService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("jishonotes/v1/games")
public class CardReviewGameController {
    @Autowired
    private CardReviewGameService service;

    // TODO: before creating a new game, find a game which haven't finished yet?
    @PostMapping("/create")
    public CardReviewGame createGame(@RequestParam("deck") UUID deckId,
                                     @RequestParam(value = "n", defaultValue = "5") int n){
        CardReviewGame game = service.createGame(deckId, n);
        if(game == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot create game");
        return game;
    }

    @GetMapping("/")
    public List<CardReviewGame> getGames(){
        return service.getGames();
    }

    @GetMapping("/{uuid}")
    public CardReviewGame getGame(@PathVariable("uuid") UUID gameId){
        CardReviewGame game = service.getGame(gameId);
        if(game == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find the specified game");
        return game;
    }

    @PostMapping("/finish")
    public void finishGame(@RequestParam("uuid") UUID gameId){
        service.finishGame(gameId);
    }

    @DeleteMapping("/{uuid}")
    public void deleteGame(@PathVariable("uuid") UUID gameId){
        service.deleteGame(gameId);
    }

    @PostMapping("/delete/unfinished")
    public void deleteUnfinishedGames(){
        service.deleteUnfinishedGames();
    }
}
