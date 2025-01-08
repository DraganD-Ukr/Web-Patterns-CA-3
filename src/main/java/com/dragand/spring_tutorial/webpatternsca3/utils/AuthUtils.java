package com.dragand.spring_tutorial.webpatternsca3.utils;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Slf4j
@Service
public class AuthUtils {

    public String authenticateUser(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            model.addAttribute("error", "You must be logged in to use this feature");
            log.info("User not logged in, redirecting to login page");
            return "redirect:/login";
        }
        log.info("Successfully authenticated user");
        return null;
    }

}
