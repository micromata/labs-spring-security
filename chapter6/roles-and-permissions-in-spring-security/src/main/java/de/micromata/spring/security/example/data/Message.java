package de.micromata.spring.security.example.data;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Jürgen Fast - j.fast@micromata.de
 */
@Entity
public class Message {
    @Id
    @GeneratedValue()
    private long id;

    @OneToOne
    @NotNull
    private User user;

    @NotEmpty(message = "text is required")
    private String text;

    @NotEmpty(message = "title is required")
    private String title;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}