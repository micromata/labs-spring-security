package de.micromata.spring.security.example.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;

/**
 * @author Jürgen Fast - j.fast@micromata.de
 */
public interface MessageRepository extends CrudRepository<Message, Long> {

    @PostAuthorize("hasPermission(returnObject, 'message')")
    Message findById(Long id);

    Iterable<Message> findByUserId(Long id);

    @PostAuthorize("hasPermission(returnObject, 'privateMessage')")
    Message findOne(Long id);

}
