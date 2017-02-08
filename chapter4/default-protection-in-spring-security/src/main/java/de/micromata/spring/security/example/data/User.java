package de.micromata.spring.security.example.data;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author Jürgen Fast - j.fast@micromata.de
 */
@Entity
public class User {

    @Id
    @GeneratedValue()
    private long id;

    @NotEmpty(message = "username is required")
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "password is required")
    private String password;

    protected User() {}

    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
