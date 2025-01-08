package com.dragand.spring_tutorial.webpatternsca3.controller;

import com.dragand.spring_tutorial.webpatternsca3.business.User;
import com.dragand.spring_tutorial.webpatternsca3.persistence.UserDAO;
import com.dragand.spring_tutorial.webpatternsca3.utils.Hash;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@Slf4j




public class UserController {

    private final UserDAO userDAO;
    private final Hash hashUtil;

    @PostMapping("/login")
    public String login(
            @RequestParam String userName,
            @RequestParam String password,
            Model model,
            HttpSession session) {
        // Backend Validation
        if (userName == null || userName.trim().length() < 3) {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
        if (password == null || password.trim().length() < 8) {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }

        User user = userDAO.getUserByName(userName.trim());
        if (user != null && hashUtil.checkPasswordWithUsername(password.trim(), user.getPassword())) {
            session.setAttribute("loggedInUser", user);
            log.info("User with ID " + user.getUserID() + " logged in successfully.");
            return "index"; // Redirect to home page
        } else {
            log.warn("Failed login attempt for username: " + userName);
            model.addAttribute("error", "Invalid username or password.");
            return "login"; // Redirect back to login page
        }
    }


    /**
     * Handle user registration.
     */
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String userName,
            @RequestParam String password,
            @RequestParam String cardNumber,
            @RequestParam String expiryDate,
            @RequestParam String cvv,
            Model model
    ) {
        // Create a new User object
        User user = new User(firstName, lastName, userName, hashUtil.hashPassword(password), 0, LocalDateTime.now(), LocalDateTime.now().plusYears(1));

        // Add the user to the database
        boolean isRegistered = userDAO.addUser(user);

        if (isRegistered) {
            model.addAttribute("message", "Registration successful! Your subscription ends on " + user.getSubscriptionEndDate());
            return "login"; // Redirect to the login page
        } else {
            model.addAttribute("error", "Registration failed. Please try again.");
            return "register"; // Stay on the registration page
        }
    }

    /**
     * Handle user logout.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        log.info("User has been logged out.");
        model.addAttribute("message", "You have been successfully logged out.");
        return "login"; // Redirect to login.html with a logout success message
    }
}