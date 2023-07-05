package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.entity.FlashCardDeck;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

// see AppConfig.java for what db is used.
public interface FlashCardsCrudDao extends CrudRepository<FlashCardDeck, UUID> {
    List<FlashCardDeck> findByDeckIdIn(List<UUID> deckIds);

    // https://www.baeldung.com/spring-data-jpa-query
    // https://stackoverflow.com/questions/8768254/select-values-from-a-table-where-exclude-values-in-another-table
    // https://www.redpill-linpro.com/techblog/2021/05/07/getting-random-rows-faster.html
    @Query(value = "with entries_table as (select entryId from CardDeckEntry where deckId = ?1 except select entryId from CardDeckReviewedEntry) select entryId from entries_table order by random() limit ?2", nativeQuery = true)
    Set<Integer> getRandomEntriesExceptReviewed(String deckId, int n);
}
