package de.micromata.spring.security.example.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Jürgen Fast - j.fast@micromata.de
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
