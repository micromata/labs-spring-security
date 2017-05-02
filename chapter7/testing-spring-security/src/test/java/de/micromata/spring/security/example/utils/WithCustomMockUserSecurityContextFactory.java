package de.micromata.spring.security.example.utils;

import de.micromata.spring.security.example.data.Message;
import de.micromata.spring.security.example.data.MessageRepository;
import de.micromata.spring.security.example.data.User;
import de.micromata.spring.security.example.data.UserRepository;
import de.micromata.spring.security.example.security.user.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * @author Jürgen Fast - j.fast@micromata.de
 */
public class WithCustomMockUserSecurityContextFactory
    implements WithSecurityContextFactory<WithCustomMockUser> {

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser customUser) {
        String username = customUser.username();
        createCustomValues(username);
        return authenticate(username);
    }

    public void createCustomValues(String username) {
        User user = userRepository.save(new User(4, username, "password", "ROLE_USER"));
        Message message = new Message();
        message.setTitle("custom");
        message.setText("custom");
        message.setUser(user);
        messageRepository.save(message);
    }

    public SecurityContext authenticate(String username) {
        UserDetails principal = authenticatedUserService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(),
            principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}
