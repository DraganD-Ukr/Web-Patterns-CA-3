package com.dragand.spring_tutorial.webpatternsca3.utils;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.dragand.spring_tutorial.webpatternsca3.persistence.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class Hash {

private final UserDAO userDAO;

    //Method to check hash password
    public boolean checkPasswordWithUsername(String password, String hashedPassword){
        //Get the hashed password from the database

        //Check if the password is correct
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }

    //Method to hash password
    public String hashPassword(String password){
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public boolean matchesOldPassword(String oldPlainPassword, String oldHashedPassword) {
        // Use BCrypt to verify the password
        BCrypt.Result result = BCrypt.verifyer().verify(oldPlainPassword.toCharArray(), oldHashedPassword);
        return result.verified;
    }

}
