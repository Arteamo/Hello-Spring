package com.arteamo.controller;

import com.arteamo.entity.Message;
import com.arteamo.repository.MessageRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessagesController {
    private final MessageRepo messageRepo;

    public MessagesController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping("/messages")
    public String messages(Model model) {
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);

        return "messages";
    }

    @PostMapping("/messages")
    public String sendMessage(@RequestParam String text, Model model) {
        Message message = new Message(text);
        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);

        return "messages";
    }
}
