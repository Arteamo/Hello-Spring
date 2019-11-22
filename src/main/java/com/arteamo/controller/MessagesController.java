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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessagesController {
    private final MessageRepo messageRepo;
    private Authentication auth = SecurityContextHolder.getContext().getAuthentication();

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
    public String sendMessage(@RequestParam String subject, @RequestParam String text, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Message message = new Message(subject, text, username);
        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);

        return "redirect:/" + "messages";
    }

    @DeleteMapping("/messages/delete/{id}")
    public String deleteMessage(@PathVariable(value = "id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (messageRepo.findMessageById(id).getAuthor().equals(auth.getName())) {
            messageRepo.deleteMessageById(id);
        }

        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);

        return "redirect:/" + "messages";
    }
}
