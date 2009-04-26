package com.jute.google.perf.model;

import com.google.appengine.api.users.User;
import com.google.appengine.api.datastore.Text;

import javax.jdo.annotations.*;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.io.*;

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
    @Unique
    private String url;

    @Persistent
    private Date created;

    @Persistent
    private Text properties;

    public Page(String url) {
        this.url = url;
        this.created = new Date();
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

    public Properties getProperties() {
        Properties props = new Properties();
        try {
            if (properties!=null) {
                props.loadFromXML(new ByteArrayInputStream(properties.getValue().getBytes("UTF-8")));
            }
        } catch (IOException e) {            
        }
        return props;
    }

    public void setProperties(Properties props) {
        try {
            OutputStream os = new ByteArrayOutputStream();
            props.storeToXML(os,"","UTF-8");
            String text = os.toString();
            this.properties = new Text(text);
        } catch (IOException e) {
        }
    }
}
