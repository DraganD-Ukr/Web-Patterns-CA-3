package com.dragand.spring_tutorial.webpatternsca3.controller;

import com.dragand.spring_tutorial.webpatternsca3.business.User;
import com.dragand.spring_tutorial.webpatternsca3.persistence.UserDAO;
import com.dragand.spring_tutorial.webpatternsca3.utils.Hash;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@Slf4j

public class UserController {


    private final UserDAO userDAO;
    private final Hash hashUtil;


    @PostMapping("/login")
    public String login(@RequestParam String userName, @RequestParam String password, Model model, HttpSession session) {
        User user = userDAO.getUserByName(userName);
        if (user != null && hashUtil.checkPasswordWithUsername(password, user.getPassword())) {
            // Login successful, redirect to a home or dashboard page
        session.setAttribute("loggedInUser",user);
        log.info("user with ID"+ user.getUserID() +" has logged");
        return "index"; // Redirect to home.html or similar
        } else {
            log.info("login attempt failed");
            // Login failed, display error message
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Redirect back to login.html
        }
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String userName,
            @RequestParam String password,
            Model model) {

        if (userDAO.existsbyUserName(userName)) {
            model.addAttribute("error", "Username already exists");
            return "register"; // Redirect back to registration page with error
        }

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .password(password)
                .build();

        user.setPassword(hashUtil.hashPassword(user.getPassword()));

        boolean success = userDAO.addUser(user);
        if (success) {
            model.addAttribute("message", "Registration successful");
            return "login"; // Redirect to login page after successful registration
        } else {
            model.addAttribute("error", "Registration failed. Please try again.");
            return "register"; // Redirect back to registration page with error
        }


    }
}