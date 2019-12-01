package com.arteamo.controller;

import com.arteamo.repository.UserRepo;
import com.arteamo.user.Role;
import com.arteamo.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

import static com.arteamo.util.Utils.REDIRECT;

@Controller
public class RegistrationController {
    private final UserRepo userRepo;

    public RegistrationController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(User user, Model model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("error", "User already exists");
            return "registration";
        }

        user.setActive(true);

        Set<Role>  userRoles = new HashSet<>();
        userRoles.add(Role.USER);

        if (user.getUsername().equals("admin")) {
            userRoles.add(Role.ADMIN);
        }
        user.setRoles(userRoles);

        userRepo.save(user);

        return REDIRECT + "login";
    }
}
