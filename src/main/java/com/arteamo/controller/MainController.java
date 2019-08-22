package com.arteamo.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {


    @GetMapping("/")
    public String welcome(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Authentication authentication,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String userName = authentication.getName();
            model.addAttribute("name", userName);
        } else {
            model.addAttribute("name", name);
        }
        return "welcome";
    }
}
