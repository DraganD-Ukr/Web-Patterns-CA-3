package com.dragand.spring_tutorial.webpatternsca3.business;

import lombok.*;

/**
 * User DTO used to hold user data from database
 *
 * @author Jo Art Mahilaga
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode

/**
 * Constructor for creating a user object
 * @param firstName The first name of the user
 * @param lastName The last name of the user
 * @param userName The username of the user
 * @param password The hashed password of the user
 * @param userID The unique identifier for the user
 */
public class User {

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String userName;
    @NonNull
    private String password; // hashed password
    private int userID;


}