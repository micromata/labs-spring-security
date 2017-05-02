package de.micromata.spring.security.example.tests;

import de.micromata.spring.security.example.data.Message;
import de.micromata.spring.security.example.data.MessageRepository;
import de.micromata.spring.security.example.data.User;
import de.micromata.spring.security.example.data.UserRepository;
import de.micromata.spring.security.example.utils.WithCustomMockUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * @author Jürgen Fast - j.fast@micromata.de
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class MessageTest {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getMessageWithOutUser() {
        messageRepository.findById(110l);
    }

    @Test
    @WithUserDetails(value = "tom")
    public void getMessage() {
        messageRepository.findById(110l);
        messageRepository.findOne(110l);
    }

    @Test
    @WithUserDetails(value = "tom")
    public void getMessageFromAnOtherUser() {
        try {
            messageRepository.findById(100l);
            assertTrue("Tom can see the message of max by findById", false);
        } catch (AccessDeniedException e) {
        }

        try {
            messageRepository.findOne(100l);
            assertTrue("Tom can see the message of max by findOne", false);
        } catch (AccessDeniedException e) {
        }
    }

    @Test
    @WithUserDetails(value = "admin")
    public void getMessageFromAnOtherUserAsAdmin() {
        try {
            messageRepository.findById(110l);
        } catch (AccessDeniedException e) {
            assertTrue("The admin should see the message of tom by findById", false);
        }

        try {
            messageRepository.findOne(110l);
            assertTrue("The admin can see the message of max by findOne", false);
        } catch (AccessDeniedException e) {
        }
    }

    @Test
    @WithCustomMockUser()
    public void getMessageWithCustomMockUser() {
        User user = userRepository.findByUsername("newUser");
        assertTrue("user is null", user != null);

        Iterable<Message> messages = messageRepository.findByUserId(user.getId());
        assertTrue("newUser has no messages", messages != null && messages.iterator().hasNext());
    }

}
