package de.micromata.spring.security.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jürgen Fast - j.fast@micromata.de
 */
@RestController
public class DefaultController {

    @RequestMapping("/")
    public String index() {
        return "You can only see this, if you are logged in!";
    }

    @RequestMapping("/noSecurity")
    public String noSecurity() {
        return "Everybody can see this!";
    }

}
