package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.User;

import java.time.LocalDate;

/**
 * User DAO interface used to define the methods that will be implemented in the UserDAOImpl class
 *
 * @author Jo Art Mahilaga
 */
public interface UserDAO {

    /**
     * Adds a user to the database
     *
     * @param user The user to add to the database
     * @return {@code true} if the user was added successfully, otherwise {@code false}
     */
    boolean addUser( User user);

    /**
     * Updates a user in the database
     *
     * @param user The user to update
     * @return {@code true} if the user was updated successfully, otherwise {@code false}
     */
    boolean updateUser(User user);

    /**
     * Retrieves the hashed password for a given username
     *
     * @param userName The username to find the password for
     * @return The hashed password if found, otherwise {@code null}
     */
    String getPasswordByUserName(String userName);

    /**
     * Retrieves a user by their username
     *
     * @param userName The username to search for
     * @return The {@link User} object if found, otherwise {@code null}
     */
    User getUserByName(String userName);

    /**
     * Removes a user from the database
     *
     * @param user The user to remove from the database
     * @return {@code true} if the user was removed successfully, otherwise {@code false}
     */
    boolean deleteUser(User user);

    /**
     * Checks if a user with the given username exists in the database
     *
     * @param userName The username to check for existence
     * @return {@code true} if the username exists, otherwise {@code false}
     */
    boolean existsbyUserName(String userName);

    /**
     * Retrieves a user by his id
     *
     * @param id The id to search for
     * @return The {@link User} object if found, otherwise {@code null}
     */
    User getUserById(int id);

    LocalDate getSubscriptionEndDate(int userId);

    boolean updateSubscriptionEndDate(int userId, LocalDate newEndDate);

}

