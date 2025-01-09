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


    /**
     * Handle user profile update. Only the fields that have changed will be updated.
     * @param userUpdateRequest - UserUpdateRequest object with updated user data
     * @param session - HttpSession object
     * @param model - Model object
     * @return - profile.html
     */
    @PatchMapping("/profile")
    public String updateUser(
            @ModelAttribute("userUpdate") UserUpdateRequest userUpdateRequest,
            HttpSession session,
            Model model
    ) {
        // Clear previous errors
        model.asMap().clear();

        try {
            authUtils.authenticateUser(session, model);
        } catch (IOException e) {
            log.error("Error authenticating user", e);
        }

        User user = (User) session.getAttribute("loggedInUser");

        // Update only the fields that have changed
        boolean isChanged = isChanged(userUpdateRequest, user, model);

        // If no changes detected, add a general error message
        if (!isChanged) {
            model.addAttribute("error", "No changes detected.");
            log.info("No changes detected for user with ID: {}", user.getUserID());
        }

        // Save the updated user in the database
        if (isChanged && userDAO.updateUser(user)) {
            model.addAttribute("message", "Profile updated successfully.");
            session.setAttribute("loggedInUser", user); // Update session data
        } else if (!isChanged) {
            // Only add this if there were no changes
            model.addAttribute("error", "No changes detected.");
        } else {
            model.addAttribute("error", "Profile update failed. Please try again.");
        }

        return "profile";
    }







    //    -----------------------------Helper methods--------------------------------

    /**
     * Check if the user has changed any fields and update the user object accordingly.
     * @param userUpdateRequest - UserUpdateRequest DTO with updated user data
     * @param user - User object from the session
     * @param model - Model object to add error messages
     * @return - true if any field has changed, false otherwise
     */
    private boolean isChanged(UserUpdateRequest userUpdateRequest, User user, Model model) {
        boolean isChanged;

        // Password validation should be checked first.
        boolean passwordChanged = validatePasswords(
                userUpdateRequest.oldPassword(),
                userUpdateRequest.newPassword(),
                user,
                model
        );

        isChanged = passwordChanged;

        // Now check the other fields and add errors to the model if needed
        if (regexUtils.isValidName(userUpdateRequest.firstName()) &&
                !userUpdateRequest.firstName().equals(user.getFirstName())
        ) {
            user.setFirstName(userUpdateRequest.firstName());
            log.debug("First Name of user with ID:{} was different, adding to updated user object", user.getUserID());
            isChanged = true;
        } else if (userUpdateRequest.firstName() != null && !userUpdateRequest.firstName().equals(user.getFirstName())) {
            model.addAttribute("errorFirstName", "Invalid or unchanged first name.");
        }

        if (regexUtils.isValidName(userUpdateRequest.lastName()) &&
                !userUpdateRequest.lastName().equals(user.getLastName())
        ) {
            user.setLastName(userUpdateRequest.lastName());
            log.debug("Last Name of user with ID:{} was different, adding to updated user object", user.getUserID());
            isChanged = true;
        } else if (userUpdateRequest.lastName() != null && !userUpdateRequest.lastName().equals(user.getLastName())) {
            model.addAttribute("errorLastName", "Invalid or unchanged last name.");
        }

        if (regexUtils.isValidUserName(userUpdateRequest.username()) &&
                !userUpdateRequest.username().equals(user.getUserName())
        ) {
            user.setUserName(userUpdateRequest.username());
            log.debug("Username of user with ID:{} was different, adding to updated user object", user.getUserID());
            isChanged = true;
        } else if (userUpdateRequest.username() != null && !userUpdateRequest.username().equals(user.getUserName())) {
            model.addAttribute("errorUsername", "Invalid or unchanged username.");
        }

        if (!isChanged){
            model.addAttribute("error", "No changes detected.");
        }
        return isChanged;
    }


    /**
     * Validate the old and new passwords and update the user object if needed.
     * @param requestOldPass - Old password from the patch request
     * @param requestNewPass - New password from the patch request
     * @param user - User object from the session
     * @param model - Model object to add error messages
     * @return - true if the password has changed, false otherwise
     */
    private boolean validatePasswords(String requestOldPass, String requestNewPass, User user, Model model) {

        if (requestOldPass == null
                || requestNewPass == null
                || requestOldPass.isEmpty()
                || requestNewPass.isEmpty()

        ) {
            return false;
        }

        boolean isChanged = false;

        // Check if the current password (in patch request) matches the one in the database (in session)
        if (hashUtil.verify(requestOldPass, user.getPassword())) {

            // Check if the new password is valid and different from the old password
            if (regexUtils.isValidPassword(requestNewPass) && !hashUtil.verify(requestNewPass, user.getPassword())) {
                // Hash the new password and set it in the user object
                user.setPassword(hashUtil.hashPassword(requestNewPass));
                log.debug("Password of user with ID:{} was different, adding to updated user object", user.getUserID());
                isChanged = true;
            } else {
                model.addAttribute("errorPassword", "Invalid new password or it is not different from the old one.");
                log.info("User with id: {} entered an invalid new password or it is not different from old one.", user.getUserID());
            }
        } else {
            model.addAttribute("errorOldPassword", "Incorrect old password.");
            log.info("User with id: {} entered incorrect old password.", user.getUserID());
        }

        return isChanged;
    }


    /**
     * Validate the payment details.
     * @param cardNumber - Credit card number
     * @param expiryDate - Expiry date of the card
     * @param cvv - CVV of the card
     * @return - true if the payment details are valid, false otherwise
     */
    private boolean validatePayment(String cardNumber, String expiryDate, String cvv) {
        return regexUtils.isValidCreditCard(cardNumber)
                && regexUtils.isValidExpiryDate(expiryDate)
                && regexUtils.isValidCVV(cvv);
    }
}