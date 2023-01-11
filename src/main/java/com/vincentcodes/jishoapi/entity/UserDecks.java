package com.vincentcodes.jishoapi.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * A user having many decks. This is a join table.
 */
@Entity
@Table(name = "usercarddeck")
@IdClass(UserDecksCompositeKey.class)
public class UserDecks {
    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "userid")
    private UUID userId;

    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "deckid")
    private UUID deckId;

    // use userid (owner-side) to find deckids (other-side)
    // then the deckids ref card packs (defined in FlashCardDeck)

    public UserDecks() {
    }

    public UserDecks(UUID userId) {
        this(userId, null);
    }
    public UserDecks(UUID userId, UUID deckId) {
        this.userId = userId;
        this.deckId = deckId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getDeckId() {
        return deckId;
    }

    public void setDeckId(FlashCardDeck deck){
        deckId = deck.getDeckId();
    }
}
