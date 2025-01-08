package com.dragand.spring_tutorial.webpatternsca3.controller;

import com.dragand.spring_tutorial.webpatternsca3.business.User;
import com.dragand.spring_tutorial.webpatternsca3.persistence.UserDAO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {

    private final UserDAO userDAO;

    // Display the subscription page
    @GetMapping("/subscription")
    public String getSubscriptionPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("error", "You must be logged in to view your subscription.");
            return "login"; // Redirect to login if not authenticated
        }

        // Fetch the subscription end date
        LocalDate subscriptionEndDate = userDAO.getSubscriptionEndDate(loggedInUser.getUserID());
        model.addAttribute("subscriptionEndDate", subscriptionEndDate);
        return "subscription"; // Render subscription.html
    }

    // Extend or renew subscription
    @PostMapping("/subscription/renew")
    public String renewSubscription(
            @RequestParam String cardNumber,
            HttpSession session,
            Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("error", "You must be logged in to renew your subscription.");
            return "login"; // Redirect to login if not authenticated
        }

        // Validate the credit card number with regex
        String cardRegex = "^[0-9]{16}$";
        if (!cardNumber.matches(cardRegex)) {
            model.addAttribute("error", "Invalid credit card number. Must be 16 digits.");
            return "subscription"; // Redirect back to subscription page
        }

        // Get the current subscription end date
        LocalDate currentEndDate = userDAO.getSubscriptionEndDate(loggedInUser.getUserID());
        LocalDate newEndDate = null;

        if (currentEndDate == null || currentEndDate.isBefore(LocalDate.now())) {
            // If there's no valid subscription, start from today
            newEndDate = LocalDate.now().plusYears(1);
        } else {
            return "index";
        }



            // Update in the database
            boolean success = userDAO.updateSubscriptionEndDate(loggedInUser.getUserID(), newEndDate);
            if (success) {
                session.setAttribute("loggedInUser", loggedInUser); // Update session data
                model.addAttribute("message", "Subscription extended successfully until: " + newEndDate);
            } else {
                model.addAttribute("error", "Failed to extend subscription. Please try again.");
            }
            return "subscription"; // Render subscription.html
        }
    }

