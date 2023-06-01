package com.vincentcodes.jishoapi.entity;

import com.vincentcodes.jishoapi.sterotype.DtoAsWell;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@DtoAsWell
//@Entity
//@Table(name = "cardreviewgame")
@Deprecated
public class CardReviewGame {
    @Id
    @Column(name = "gameid") // all lowercase
    @Type(type="org.hibernate.type.UUIDCharType")
    private final UUID gameId;

    // index of GameEntry
    @Column(name = "progress")
    private int progress;

    @Column(name = "numentries")
    private final int numEntries;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "gameentry", joinColumns = @JoinColumn(name = "gameid"))
    @Column(name = "entryid") // column in the collection table
    private Set<Integer> chosenEntries;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "gamecorrectentry", joinColumns = @JoinColumn(name = "gameid"))
    @Column(name = "entryid") // column in the collection table
    private Set<Integer> correctEntries;

    public CardReviewGame(int progress, int numEntries, Set<Integer> entries, Set<Integer> correctEntries) {
        gameId = UUID.randomUUID();
        this.progress = progress;
        this.numEntries = numEntries;
        this.chosenEntries = entries;
        this.correctEntries = correctEntries;
    }
    public CardReviewGame() {
        this(0, 0, new HashSet<>(), new HashSet<>());
    }

    public UUID getGameId() {
        return gameId;
    }

    public int getNumEntries() {
        return numEntries;
    }

    public int getProgress() {
        return progress;
    }

    public void addProgress() {
        progress++;
    }

    public Set<Integer> getChosenEntries() {
        return chosenEntries;
    }

    public void setChosenEntries(Set<Integer> chosenEntries) {
        this.chosenEntries = chosenEntries;
    }

    public Set<Integer> getCorrectEntries() {
        return correctEntries;
    }

    public void setCorrectEntries(Set<Integer> correctEntries) {
        this.correctEntries = correctEntries;
    }
}
