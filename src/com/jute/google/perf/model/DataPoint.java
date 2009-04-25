package com.jute.google.perf.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.Date;

/**
 * User: hugozhu
 * Date: Apr 25, 2009
 * Time: 12:31:25 PM
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class DataPoint {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Date date;

    @Persistent
    private Page page;

    @Persistent
    int connectTime;

    @Persistent
    int readTime;

    @Persistent
    int error;

    public DataPoint(Page page, int connectTime, int readTime, int error) {
        this.page = page;
        this.connectTime = connectTime;
        this.readTime = readTime;
        this.error = error;
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public int getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(int connectTime) {
        this.connectTime = connectTime;
    }

    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
