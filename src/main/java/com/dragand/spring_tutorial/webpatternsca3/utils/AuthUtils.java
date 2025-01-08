package com.dragand.spring_tutorial.webpatternsca3.utils;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;

@Slf4j
@Service
public class AuthUtils {

    private final HttpServletResponse httpServletResponse;

    public AuthUtils(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public String authenticateUser(HttpSession session, Model model) throws IOException {
        if (session.getAttribute("loggedInUser") == null) {
            model.addAttribute("error", "You must be logged in to use this feature");
            log.info("User not logged in, redirecting to login page");
            httpServletResponse.sendRedirect("/login");
        }
        log.info("Successfully authenticated user");
        return null;
    }

}
