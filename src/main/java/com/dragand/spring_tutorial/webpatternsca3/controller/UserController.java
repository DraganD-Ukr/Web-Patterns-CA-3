package com.dragand.spring_tutorial.webpatternsca3.controller;

import com.dragand.spring_tutorial.webpatternsca3.business.User;
import com.dragand.spring_tutorial.webpatternsca3.business.dto.UserUpdateRequest;
import com.dragand.spring_tutorial.webpatternsca3.persistence.UserDAO;
import com.dragand.spring_tutorial.webpatternsca3.utils.AuthUtils;
import com.dragand.spring_tutorial.webpatternsca3.utils.Hash;
import com.dragand.spring_tutorial.webpatternsca3.utils.RegexUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@Slf4j




public class UserController {

    private final UserDAO userDAO;
    private final Hash hashUtil;
    private final AuthUtils authUtils;
    private final RegexUtils regexUtils;

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

        validatePayment(cardNumber, expiryDate, cvv);

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


    @PatchMapping("/profile")
    public String updateUser(
            @ModelAttribute("userUpdate") UserUpdateRequest userUpdateRequest,
            HttpSession session,
            Model model
    ) {

        try {
            authUtils.authenticateUser(session, model);
        } catch (IOException e) {
            log.error("Error authenticating user", e);
        }

        User user = (User) session.getAttribute("loggedInUser");

        // Update only the fields that have changed
        if (isChanged(userUpdateRequest, user)) {
            log.info("User with ID: {} updated their profile.", user.getUserID());
        } else {
            log.warn("User with ID: {} tried to update their profile with no changes detected.", user.getUserID());
            model.addAttribute("error", "No changes detected.");
            return "profile"; // Stay on the profile page
        }


        // Save the updated user in the database
        boolean isUpdated = userDAO.updateUser(user);

        if (isUpdated) {
            model.addAttribute("message", "Profile updated successfully.");
            session.setAttribute("loggedInUser", user); // Update session data
            return "index"; // Redirect to the home page
        } else {
            model.addAttribute("error", "Profile update failed. Please try again.");
            return "profile"; // Stay on the profile page
        }
    }

    private boolean isChanged(UserUpdateRequest userUpdateRequest, User user) {

        boolean isChanged = false;

        if (regexUtils.isValidName(userUpdateRequest.firstName()) &&
                !userUpdateRequest.firstName().equals(user.getFirstName())
        ) {
            user.setFirstName(userUpdateRequest.firstName());
            isChanged = true;
        }
        if (regexUtils.isValidName(userUpdateRequest.lastName()) &&
                !userUpdateRequest.lastName().equals(user.getLastName())) {
            user.setLastName(userUpdateRequest.lastName());
            isChanged = true;
        }

        if (regexUtils.isValidUserName(userUpdateRequest.username()) &&
                !userUpdateRequest.username().equals(user.getUserName())) {
            user.setUserName(userUpdateRequest.username());
            isChanged = true;
        }


//            Check if the old password matches the one in the database
            if (hashUtil.matchesOldPassword(userUpdateRequest.oldPassword(), user.getPassword())) {
//                Update the password
                if (regexUtils.isValidPassword(userUpdateRequest.newPassword())) {
                    user.setPassword(hashUtil.hashPassword(userUpdateRequest.newPassword()));
                    isChanged = true;
                } else {
                    log.info("User with id: {} entered an invalid new password.{}", user.getUserID());
                }
            } else {
                log.info("User with id: {} entered incorrect old password.{}", user.getUserID());
            }


        return isChanged;

    }




    private boolean validatePayment(String cardNumber, String expiryDate, String cvv) {
        // Validate the credit card number with regex
        String cardRegex = "^[0-9]{16}$";
        if (!cardNumber.matches(cardRegex)) {
            return false;
        }

        // Validate the expiry date with regex
        String expiryRegex = "^(0[1-9]|1[0-2])\\/?([0-9]{2})$";
        if (!expiryDate.matches(expiryRegex)) {
            return false;
        }

        // Validate the CVV with regex
        String cvvRegex = "^[0-9]{3}$";
        return cvv.matches(cvvRegex);
    }
}