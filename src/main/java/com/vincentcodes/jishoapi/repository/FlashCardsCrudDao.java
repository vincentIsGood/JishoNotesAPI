package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.entity.FlashCardDeck;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

// see AppConfig.java for what db is used.
public interface FlashCardsCrudDao extends CrudRepository<FlashCardDeck, UUID> {
}
