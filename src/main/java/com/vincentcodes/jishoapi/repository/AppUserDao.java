package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.entity.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @link https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 * @link https://www.baeldung.com/spring-data-derived-queries
 */
@Repository
public interface AppUserDao extends CrudRepository<AppUser, UUID> {
    /**
     * @param name username
     */
    Optional<AppUser> findByName(String name);
}
