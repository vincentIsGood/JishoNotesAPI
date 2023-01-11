package com.vincentcodes.jishoapi.entity;

import java.io.Serializable;
import java.util.UUID;

public class UserDecksCompositeKey implements Serializable {
    private UUID userId;
    private UUID deckId;
}