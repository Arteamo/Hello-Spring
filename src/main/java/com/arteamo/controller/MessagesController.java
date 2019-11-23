package com.arteamo.controller;

import com.arteamo.entity.Message;
import com.arteamo.repository.MessageRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.arteamo.util.Utils.REDIRECT;

@Controller
public class MessagesController {
    private final MessageRepo messageRepo;

    public MessagesController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping("/messages")
    public String getMessages(Model model) {
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);

        return "messages";
    }

    @PostMapping("/messages")
    public String sendMessage(
            @RequestParam String subject,
            @RequestParam String text
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Message message = new Message(subject, text, username);
        messageRepo.save(message);

        return REDIRECT + "messages";
    }

    @DeleteMapping("/messages/delete/{id}")
    public String deleteMessage(
            @PathVariable(value = "id") Long id
    ) {

        if (authorizationIsValid(id)) {
            messageRepo.deleteMessageById(id);
        }

        return REDIRECT + "messages";
    }

    @PutMapping("/messages/edit/{id}")
    public String updateMessage(
            @PathVariable(value = "id") Long id,
            @RequestParam String subject,
            @RequestParam String text
    ) {
        if (authorizationIsValid(id)) {
            Message updatedMessage = messageRepo.findMessageById(id)
                    .setSubject(subject)
                    .setText(text);
            messageRepo.save(updatedMessage);
        }

        return REDIRECT + "messages";
    }

    private boolean authorizationIsValid(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return messageRepo.findMessageById(id).getAuthor().equals(auth.getName());
    }
}
