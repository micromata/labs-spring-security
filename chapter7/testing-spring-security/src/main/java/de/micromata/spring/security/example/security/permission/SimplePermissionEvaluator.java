package de.micromata.spring.security.example.security.permission;

import de.micromata.spring.security.example.data.Message;
import de.micromata.spring.security.example.data.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Jürgen Fast - j.fast@micromata.de
 */
@Component
public class SimplePermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null) {
            return false;
        }
        Message message = (Message) targetDomainObject;
        if (message == null) {
            return true;
        }
        User user = (User) authentication.getPrincipal();

        if (user.getId() == message.getUser().getId()) {
            return true;
        }

        if ("privateMessage".equals(permission)) {
            return false;
        } else if ("message".equals(permission) && "ROLE_ADMIN".equals(user.getRole())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
