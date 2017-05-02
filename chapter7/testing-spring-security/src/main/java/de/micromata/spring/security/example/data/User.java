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

    @NotEmpty(message = "role is required")
    private String role;

    protected User() {}

    public User(long id, String userName, String password, String role) {
        this.id = id;
        this.username = userName;
        this.password = password;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
