package com.dragand.spring_tutorial.webpatternsca3.utils;

import org.springframework.stereotype.Service;

@Service
public class RegexUtils {

    // Precompiled regex patterns for performance
    private static final String NAME_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])[A-Za-z\\d\\s]+$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{3,20}$";
    private static final String CREDIT_CARD_PATTERN = "^[0-9]{16}$";
    private static final String EXPIRY_DATE_PATTERN = "^(0[1-9]|1[0-2])\\/?([0-9]{2})$";
    private static final String CVV_PATTERN = "^[0-9]{3}$";

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

    /**
     * Validates a credit card.
     * @param creditCard
     * @return true if the credit card matches the pattern; false otherwise.
     */
    public boolean isValidCreditCard(String creditCard) {
        return creditCard != null && !creditCard.isEmpty() && creditCard.matches(CREDIT_CARD_PATTERN);
    }

    /**
     * Validates an expiry date of credit card.
     * @param expiryDate
     * @return true if the expiry date matches the pattern; false otherwise.
     */
    public boolean isValidExpiryDate(String expiryDate) {
        return expiryDate != null && !expiryDate.isEmpty() && expiryDate.matches(EXPIRY_DATE_PATTERN);
    }

    /**
     * Validates CVV of credit card.
     * @param cvv
     * @return true if the CVV matches the pattern; false otherwise.
     */
    public boolean isValidCVV(String cvv) {
        return cvv != null && !cvv.isEmpty() && cvv.matches(CVV_PATTERN);
    }
}