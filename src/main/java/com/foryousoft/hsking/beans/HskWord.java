package com.foryousoft.hsking.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

import com.foryousoft.hsking.enums.CharacterType;
import com.foryousoft.hsking.interfaces.ICedictWord;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.std.DateSerializer;

/**
 * Copyright (C) I-Tang, Pierre-Emmanuel HIU
 * author' s email : itang_hiu@hotmail.com
 */
@Entity
@Table(name = "HSKWORD",
        uniqueConstraints = {@UniqueConstraint(columnNames
                = {"user_id", "traditional", "simplified", "pinyin"})})
public class HskWord implements ICedictWord, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    protected User user;

    @ElementCollection(fetch = FetchType.EAGER)
    protected List<String> examples = new LinkedList();

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    protected CharacterType characterType = CharacterType.NONE;

    @Column
    protected String meaning;// meaning added by the user
    @Column
    protected String comment;// comment added by the user
    @Column
    protected String tip;// comment added by the user
    @Column(nullable = false)
    protected Boolean fromCedict = true;

    // CEDICT FIELDS
    @Column(nullable = false)
    protected String traditional;
    @Column(nullable = false)
    protected String simplified;
    @Column(nullable = false)
    protected String pinyin; // pinyin with numbers tone
    @Column(nullable = false)
    protected String cedictMeaning;
    @Column(nullable = false)
    private Date creationDate;
    @Column(nullable = true)
    private Date updateDate;

    // if this character is an original character form MCFC or CEDICT dico, then serialNumber == to the orginal one
    // if this character is a copy from MCFC or CEDICT, serialNumber = original serialNumber .
    // if this character is created from scratch, serialNumber == id
    //protected Long serialNumber;

    @SuppressWarnings("unused")
    private final static Logger logger = Logger.getLogger(HskWord.class.getName());

    public HskWord() {

        traditional = "";
        simplified = "";
        pinyin = "";
        setCreationDate(new Date());
    }

    public HskWord(User user) {

        this();
        this.user = user;
    }

    public HskWord(User user, String classical, String simplified, String pinyin, String cedictMeaning) {

        this();
        this.user = user;
        this.traditional = classical;
        this.simplified = simplified;
        this.pinyin = pinyin;
        this.cedictMeaning = cedictMeaning;
    }

    @Transient
    public HskWord clone() {

        HskWord cedictWord = new HskWord();
        cedictWord.setId(id);
        cedictWord.setUser(user);
        cedictWord.setTraditional(traditional);
        cedictWord.setSimplified(simplified);
        cedictWord.setPinyin(pinyin);
        return cedictWord;
    }

    public void addSentenceExample(String example) {
        examples.add(example);
    }

    public void removeSentenceExample(int index) {
        examples.remove(index);
    }

    public Boolean getFromCedict() {
        return fromCedict;
    }

    public void setFromCedict(Boolean fromCedict) {
        this.fromCedict = fromCedict;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getSimplified() {
        return simplified;
    }

    public String getTraditional() {
        return traditional;
    }

    public String getCedictMeaning() {
        return cedictMeaning;
    }

    public void setCedictMeaning(String meaning) {
        this.cedictMeaning = meaning;
    }

    /*
            pinyin with numbers tone
         */
    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }


    public void setTraditional(String traditional) {
        this.traditional = traditional;
    }

    public void setSimplified(String simplified) {
        this.simplified = simplified;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonSerialize(using = DateSerializer.class)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @JsonSerialize(using = DateSerializer.class)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {

        Long userId = (user == null) ? null : user.getId();
        String s = "id:" + id
                + ", simplified:" + simplified
                + ", traditional:" + traditional
                + ", pinyin:" + pinyin
                + ", userId:" + userId;
        String exampleString = "";
        int i = 1;
        for (String example : examples) {
            exampleString += ", example" + i + ":" + example;
            i++;
        }
        s += exampleString;
        return s;
    }

    @Override
    public boolean equals(Object word) {

        if (this == word)
            return true;
        if (!(word instanceof HskWord))
            return false;
        HskWord w = (HskWord) word;
        String concat = " " + traditional + simplified + pinyin;
        String otherConcat = " " + w.getTraditional() + w.getSimplified() + w.getPinyin();
        if (concat.equals(otherConcat))
            return true;
        return false;
    }
}

