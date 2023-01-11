package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.entity.UserDecks;
import com.vincentcodes.jishoapi.entity.UserDecksCompositeKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserDecksCrudDao extends CrudRepository<UserDecks, UserDecksCompositeKey> {
    List<UserDecks> findByUserId(UUID userId);
}
