package com.vincentcodes.jishoapi.controller;

import com.vincentcodes.jishoapi.entity.CardReviewGame;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//@CrossOrigin
//@RestController
//@RequestMapping("jishonotes/v1/games")
public class CardReviewGameController {
    // create
    @GetMapping("/create")
    public CardReviewGame createGame(){
        throw new NotYetImplementedException();
    }

    @PostMapping("/check")
    public boolean check(@RequestBody String userAnswer){
        throw new NotYetImplementedException();
    }

    // retrieve
    @GetMapping("/{uuid}")
    public CardReviewGame getGame(@PathVariable("uuid") UUID gameId){
        throw new NotYetImplementedException();
    }

    // delete
    @DeleteMapping("/{uuid}")
    public void deleteGame(@PathVariable("uuid") UUID gameId){
        throw new NotYetImplementedException();
    }
}
