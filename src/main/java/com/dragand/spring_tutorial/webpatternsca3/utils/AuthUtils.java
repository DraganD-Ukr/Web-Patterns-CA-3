package com.dragand.spring_tutorial.webpatternsca3.utils;

import com.dragand.spring_tutorial.webpatternsca3.business.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;

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

    public String isSubscriptionActive(HttpSession session, RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("loggedInUser");
        LocalDateTime subscriptionEndDate = user.getSubscriptionEndDate();

        if (subscriptionEndDate == null
                || user.getSubscriptionEndDate().isBefore(LocalDateTime.now())
                || user.getSubscriptionEndDate().isEqual(LocalDateTime.now())
        ) {
            log.info("Found user with an inactive subscription, redirecting to subscription page");
            redirectAttributes.addFlashAttribute("error", "You must have an active subscription to use this feature");
            return "redirect:/subscription";
        }
        log.info("Successfully checked a user for an active subscription");
        return null;
    }

}
