package com.foryousoft.hsking.beans;

import javax.persistence.*;

/**
 * Created by i-tang on 26/08/16.
 */
@Entity
@Table(name = "MYBEAN")
public class MyBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    public MyBean(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
