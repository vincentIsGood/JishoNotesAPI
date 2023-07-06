package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.entity.CardReviewGame;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * @see FlashCardsCrudDao
 */
public interface CardReviewGameCrudRepo extends CrudRepository<CardReviewGame, UUID> {
    // https://www.baeldung.com/spring-data-derived-queries
    List<CardReviewGame> findByUserId(UUID userId);

    //@Query(value = "select * from cardreviewgame where userid = ?1", nativeQuery = true)
    //List<CardReviewGame> findByUserId(String userId);

    void deleteByFinishedAndUserId(boolean finished, UUID userId);
}
