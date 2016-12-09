package com.foryousoft.hsking.enums;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

/**
 * Copyright (C) I-Tang, Pierre-Emmanuel HIU
 *  author' s email : itang_hiu@hotmail.com
 */
public enum CharacterType implements Serializable {

    UNKNOWN(0, "UNK", "unknown"),
    PICTOGRAM(1, "PIC", "pictogram"),
    SYMBOL(2, "SYM", "symbol"),
    MEANING_MEANING(3, "MME", "meaningMeaning"),
    SOUND_MEANING(4, "SME", "soundMeaning"),
    SOUND_LOAN( 5,"SLO", "soundLoan"),
    RECLARIFIED(6, "REC", "reclarified"),
    NONE(7, "NON", "none");
  /*  RADICAL(7, "RAD", "radical"),
    NOT_RADICAL(8, "NRA","notRadical"),
    EMPTY(9, "", "");*/

    private int index;
    private String universalKey;
    private String nameKey;


    private CharacterType(int id, String universalKey, String nameKey) {

        this.index = id;
        this.universalKey = universalKey;
        this.nameKey = nameKey;
    }

    public String getUniversalKey() {
        return universalKey;
    }

    public String getNameKey() {
        return nameKey;
    }

    public int getIndex() {
        return index;
    }

    public static CharacterType get(String _universalKey) {

        CharacterType[] characterTypes = CharacterType.values();
        for (CharacterType characterType : characterTypes)  {
            String characterTypeKey = characterType.getUniversalKey();
            if (characterTypeKey.equals(_universalKey)) return characterType;
        }
        return null;
    }

    public static CharacterType get(int index) {

        CharacterType[] characterTypes = CharacterType.values();
        for (CharacterType characterType : characterTypes)  {
            int characterTypeIndex = characterType.getIndex();
            if (characterTypeIndex == index) return characterType;
        }
        return null;
    }

    public static Collection<CharacterType> getCharacterTypes() {

        CharacterType[] characterTypes = CharacterType.values();
        return Arrays.asList(characterTypes);
    }
}


