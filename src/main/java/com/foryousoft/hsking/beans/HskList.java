package com.foryousoft.hsking.beans;

import com.foryousoft.hsking.enums.LanguageType;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;


/*
  even if published == true (the list is public) , the userId can be not null.
  If a user creates a public list, we have to be able to know who is the author of this list
 */
@Entity
@Table(name = "HSKLIST",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "name"})})
public class HskList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne( targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToMany
    @JoinTable(name = "HSKLIST_HSWORD", joinColumns = @JoinColumn(
            name = "list_id"), inverseJoinColumns = @JoinColumn(name = "hskword_id"))
    private List<HskWord> hskWords = new ArrayList();

    @Column(nullable = false)
    private boolean published = false;// if true, this list is visible and can be downloaded by anyone
    @Column(nullable = false)
    protected String name;
    private String bookName;
    private String volumeNumber;
    private String publisher;
    private String lesson;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private LanguageType language = LanguageType.ENGLISH;// used for the provided lists
    @Column(nullable = false)
    private Date creationDate;
    private Date updateDate;

    /*@Column(name="RANKING",nullable=true)
    private int ranking;
    @Column(name = "GROUP_ID", nullable = false)
    private Long groupId;// a list can belong to a group*/

    @SuppressWarnings("unused")
    private final static Logger logger = Logger.getLogger(HskList.class.getName());


    public HskList() {

        super();
        setCreationDate(new Date());
    }

    public HskList(User user, String name) {

        this();
        this.user = user;
        this.name = (name == null) ? "" : name;
    }

    public HskList(User user, String name, String bookName, String lesson, String volumeNbr, String publisher) {

        this(user, name);
        this.bookName = bookName;
        this.lesson = lesson;
        this.volumeNumber = volumeNbr;
        this.publisher = publisher;
    }

    public HskList(User user, String name, LanguageType language, boolean published) {

        this(user, name, published);
        this.language = language;
        this.user = user;
    }

    public HskList(User user, String name, boolean published) {

        this(user, name);
        this.user = user;
        this.published = (user == null) ? true : published;// if usedIrd == null then the list must be public
    }

    public void addHskWord(HskWord word) {

        if (hskWords.contains(word)) return;
        hskWords.add(word);
    }

    public void removeHskWord(HskWord word) {
        hskWords.remove(word);
    }

    public HskWord getHskWord(int index) {

        if (index > hskWords.size() - 1) return null;
        return hskWords.get(index);
    }

    public List<HskWord> getHskWords() {
        return hskWords;
    }

    public void setHskWords(List<HskWord> hsWords) {
        this.hskWords = hsWords;
    }

    public Integer size() {
        return hskWords.size();
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(String volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public LanguageType getLanguage() {
        return language;
    }

    public void setLanguage(LanguageType language) {
        this.language = language;
    }

    //@JsonSerialize(using = JacksonDateSerializer.class)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    //@JsonSerialize(using = JacksonDateSerializer.class)
    public Date getUpdateDate() {
        return updateDate;
    }

    @Override
    public boolean equals(Object _object) {

        if (!(_object instanceof HskList)) return false;
        String listName = ((HskList) _object).getName();
        if (name.equals(listName)) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {

        Integer size = (hskWords == null) ? null : hskWords.size();
        return new ToStringBuilder(this).
                append("id:", id)
                .append("size: " , size)
                .append("name: ", name)
                .toString();

    }

    /*  public boolean forward(int _selectedIndex) {

        //we can t advance the character if it is at the end of the list
        if (_selectedIndex == getCedictWords().size() - 1) return false;
        Collections.swap(cedictWords, _selectedIndex, _selectedIndex + 1);
        return true;
    }

    public boolean backward(int _selectedIndex) {

        //we can t backward the character if it is at the beginning of the list
        if (_selectedIndex == 0) return false;
        Collections.swap(cedictWords, _selectedIndex, _selectedIndex - 1);
        return true;
    }

    public List<Long> removeCharacter(int _selectedIndex) {

        cedictWords.remove(_selectedIndex);
        return getCharacterIds();
    }

     public List<Long> getCharacterIds() {

        List<Long> l = new ArrayList<Long>();
        for (CedictWord userWord : getCedictWords()) {
            l.add(userWord.getId());
        }
        return l;
    }

    public CedictWord getMCFCWord(Long _id) {

        if (_id == null) return null;
        for (CedictWord userWord : getCedictWords()) {
            Long id = userWord.getId();
            if (_id.equals(id)) return userWord;
        }
        return null;
    }

      public List<Long> getCharacterIds(Integer _lowerIndex, Integer _upperIndex, String _sql) {

        List<Long> list = new ArrayList<Long>();
        for (Long characterId : getCharacterIds()) {
            if ((characterId >= _lowerIndex) && (characterId <= _upperIndex)) {
                list.add(characterId);
            }
        }
        return list;
    }

    @Transient
    public boolean containsCharacter(HSWord _word) {

        if (_word == null) return true;
        for (CedictWord currentWord : cedictWords) {
            if (currentWord == null)
                throw new RuntimeException("Internal error : there can be no null value in cedictWords");
        }
        return false;
    }
    */

}
