package com.vincentcodes.jishoapi.entity;

import com.vincentcodes.jishoapi.sterotype.DtoAsWell;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@DtoAsWell
@Entity
@Table(name = "cardreviewgame")
public class CardReviewGame {
    @Id
    @Column(name = "gameid")
    @Type(type="org.hibernate.type.UUIDCharType")
    private final UUID gameId;

    @Column(name = "userid")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID userId;

    @Column(name = "deckid")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID deckId;

    @Column(name = "finished")
    private boolean finished = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reviewentry", joinColumns = @JoinColumn(name = "gameid"))
    @Column(name = "entryid") // column in the collection table
    private Set<Integer> entriesToReview;

    public CardReviewGame(UUID userId, UUID deckId, Set<Integer> entriesToReview) {
        gameId = UUID.randomUUID();
        this.userId = userId;
        this.deckId = deckId;
        this.entriesToReview = entriesToReview;
    }
    public CardReviewGame() {
        this(null, null, new HashSet<>());
    }

    public void finish(){
        finished = true;
    }

    public UUID getGameId() {
        return gameId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getDeckId() {
        return deckId;
    }

    public boolean isFinished() {
        return finished;
    }

    public Set<Integer> getEntriesToReview() {
        return entriesToReview;
    }
}
