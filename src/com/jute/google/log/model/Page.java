package com.jute.google.log.model;

import com.google.appengine.api.users.User;

import javax.jdo.annotations.*;
import java.util.Date;

/**
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 12:39:39 PM
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Page {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private User author;

    @Persistent
    private String url;

    @Persistent
    private Date created;


    public Page(User author, String url) {
        this.author = author;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
