package de.micromata.spring.security.example.controller;

import de.micromata.spring.security.example.data.Message;
import de.micromata.spring.security.example.data.MessageRepository;
import de.micromata.spring.security.example.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jürgen Fast - j.fast@micromata.de
 */
@Controller
public class DefaultController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/noSecurity")
    public String noSecurity() {
        return "noSecurity";
    }

    @RequestMapping("/messages")
    public String listMessages(@AuthenticationPrincipal User user, Model model) {
        Iterable<Message> messages = messageRepository.findByUserId(user.getId());
        model.addAttribute("messages", messages);
        return "listMessages";
    }

    @RequestMapping(value = "/message/{id}")
    public String viewMessage(@PathVariable Long id, Model model) {
        Message message = messageRepository.findById(id);
        model.addAttribute("message", message);
        return "viewMessage";
    }

    @RequestMapping("/privateMessage/{id}")
    public String viewPrivateMessage(@PathVariable Long id, Model model) {
        Message message = messageRepository.findOne(id);
        model.addAttribute("message", message);
        return "viewMessage";
    }

}
