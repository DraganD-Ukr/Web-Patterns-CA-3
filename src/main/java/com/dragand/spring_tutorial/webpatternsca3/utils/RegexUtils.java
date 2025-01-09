package com.dragand.spring_tutorial.webpatternsca3.utils;

import org.springframework.stereotype.Service;

@Service
public class RegexUtils {

    // Precompiled regex patterns for performance
    private static final String NAME_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])[A-Za-z\\d\\s]+$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{3,20}$";

    /**
     * Validates a name.
     *
     * @param name The name to validate.
     * @return true if the name matches the pattern; false otherwise.
     */
    public boolean isValidName(String name) {
        return name != null && !name.isEmpty() && name.matches(NAME_PATTERN);
    }

    /**
     * Validates a password.
     *
     * @param password The password to validate.
     * @return true if the password matches the pattern; false otherwise.
     */
    public boolean isValidPassword(String password) {
        return password != null && !password.isEmpty() && password.matches(PASSWORD_PATTERN);
    }

    /**
     * Validates a username.
     *
     * @param userName The username to validate.
     * @return true if the username matches the pattern; false otherwise.
     */
    public boolean isValidUserName(String userName) {
        return userName != null && !userName.isEmpty() && userName.matches(USERNAME_PATTERN);
    }
}