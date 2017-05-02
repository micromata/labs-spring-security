package de.micromata.spring.security.example.utils;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Jürgen Fast - j.fast@micromata.de
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {

    String username() default "newUser";

}