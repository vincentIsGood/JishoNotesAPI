package com.vincentcodes.jishoapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.vincentcodes.jishoapi.sterotype.DtoAsWell;
import org.hibernate.annotations.Type;
import org.springframework.web.util.HtmlUtils;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Collection table looks something like this:
 * +----+-------+
 * | fk | const |
 * +----+-------+
 *
 * "const" are predefined values.
 * It is not an object / relation / table / entity.
 * That's why it's called a collection table instead of join table.
 */
@DtoAsWell
@JsonView({FlashCardDeck.Views.Simple.class,
        FlashCardDeck.Views.Full.class})
@Entity
@Table(name = "flashcarddeck")
public class FlashCardDeck {
    @Id
    @Column(name = "deckid") // all lowercase
    @Type(type="org.hibernate.type.UUIDCharType")
    @JsonProperty
    private final UUID deckId;

    private String name;

    private String description;

    // https://stackoverflow.com/questions/2501383/persisting-a-list-of-integers-with-jpa?rq=1
    //@OneToMany(mappedBy = "somefield") // is essentially @ManyToMany
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "carddeckentry", joinColumns = @JoinColumn(name = "deckid"))
    @Column(name = "entryid") // column in the collection table
    private Set<Integer> cards;

    public FlashCardDeck(String name, Set<Integer> cards){
        this.deckId = UUID.randomUUID();
        this.name = HtmlUtils.htmlEscape(name);
        this.cards = cards;
    }
    public FlashCardDeck(String name){
        this(name, new HashSet<>());
    }

    // DTO constructor
    public FlashCardDeck(){
        this.deckId = UUID.randomUUID();
    }

    public void addCard(int entryId){
        cards.add(entryId);
    }

    public void addCards(int[] entriesId){
        cards.addAll(IntStream.of(entriesId).boxed().collect(Collectors.toList()));
    }

    public void deleteCard(int entryId){
        cards.removeIf(id -> id == entryId);
    }

    public void deleteCards(int[] entriesId){
        for(int entryId : entriesId) {
            cards.remove(entryId);
        }
    }

    @JsonIgnore
    public UUID getDeckId(){
        return deckId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @return null if no cards exist
     */
    @JsonView({FlashCardDeck.Views.Full.class, FlashCardDeck.Views.CardsOnly.class})
    public List<Integer> getCards() {
        if(cards == null)
            return null;
        return new ArrayList<>(cards);
    }

    @JsonIgnore
    public Set<Integer> getCardSet(){
        return cards;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCards(Set<Integer> cards){
        this.cards = cards;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Views{
        public static class Simple{}
        public static class CardsOnly{}
        public static class Full{}
    }

}
