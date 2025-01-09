package com.dragand.spring_tutorial.webpatternsca3.utils;

import com.dragand.spring_tutorial.webpatternsca3.business.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUtils {

    private final HttpServletResponse httpServletResponse;

    /**
     * Authenticate a user by checking if they are logged in
     * @param session the session to check for the user
     * @param model the model to add an error message to
     * @return null if the user is authenticated, otherwise redirect to the login page
     * @throws IOException
     */
    public String authenticateUser(HttpSession session, Model model) throws IOException {

        if (session.getAttribute("loggedInUser") == null) {
            model.addAttribute("error", "You must be logged in to use this feature");
            log.info("User not logged in, redirecting to login page");
            httpServletResponse.sendRedirect("/login");
        }
        log.info("Successfully authenticated user");
        return null;

    }

    /**
     * Check if a user has an active subscription
     * @param session the session to get the user from
     * @param redirectAttributes the redirect attributes to add an error message to
     * @return null if the user has an active subscription, otherwise redirect to the subscription page
     */
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
