package com.eventapp.controller;

import com.eventapp.model.User;
import com.eventapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignUpController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/do-signup")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes) {

        System.out.println("Signup attempt: " + username); // Debug log ✅
        // Check if username already exists
        if (userRepository.findByUsername(username).isPresent()) {
            redirectAttributes.addAttribute("error", true); // This triggers the alert
            return "redirect:/signup";
        }

        User newUser = new User(username, passwordEncoder.encode(password), "USER");
        userRepository.save(newUser);

        return "redirect:/login?signup_success=true";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "views/index/signup";
    }
}

