package com.foryousoft.hsking.beans;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="user", fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<HskList> hskLists = new ArrayList();

    @Column( nullable = false, unique = true)
    private String email;
    @Column( nullable = false)
    private String firstName;
    @Column( nullable = false)
    private String lastName;
    @Column
    private String city;
    @Column
    private String zipCode;
    @Column
    private String country;
    @Column
    private String occupation;
    @Column
    private Integer age;
    @Column
    private Integer sex;
    @Column(nullable = false)
    private Date creationDate;
    @Column
    private Date updateDate;


    public User() {
        super();
        setCreationDate(new Date());
    }

    public User(String emailAddress) {

        this();
        this.email = emailAddress;
    }

    public User(String emailAddress, String firstName, String lastName) {

        this(emailAddress);
        this.firstName = firstName;
        this.lastName = lastName;
        setCreationDate(new Date());
    }

    public void add(HskList list) {
        hskLists.add(list);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<HskList> getHskLists() {
        return hskLists;
    }

    public void setHskLists(List<HskList> hsLists) {
        this.hskLists = hsLists;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getSex() {
        return sex;
    }

    public String getEmail() {
        return this.email;
    }

    public String getOccupation() {
        return occupation;
    }

    public Integer getAge() {
        return age;
    }

    public void setFirstName(String _firstName) {
        firstName = _firstName;
    }

    public void setLastName(String _lastName) {
        this.lastName = _lastName;
    }

    public void setSex(Integer _sex) {
        this.sex = _sex;
    }

    public void setEmail(String _emailAddress) {
        email = _emailAddress;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {

        Integer size = (hskLists == null) ? null : hskLists.size();
        return new ToStringBuilder(this).
                append("id:", id).
                append(", lastName:", lastName).
                append(", firstName:", firstName).
                append(", email:", email).
                append(", hsklists.size:", size).
                toString();
    }
}
