package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Implementation of UserDAO interface for managing user-related database operations.
 *
 * @Author Jo Art Mahilaga
 */
@Repository
public class UserDaoImpl extends MySQLDao implements UserDAO {

    public UserDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }

    public UserDaoImpl() {
        super();
    }

    /**
     * Adds a new user to the database.
     *
     * @param user The user to be added.
     * @return True if the user is added successfully, false otherwise.
     */
    @Override
    public boolean addUser(User user) {
        if (user == null) {
            return false;
        }

        String sql = "INSERT INTO Users (firstName, lastName, userName, password, subscriptionEndDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getPassword());
            ps.setDate(5, Date.valueOf(user.getSubscriptionEndDate().toLocalDate()));

            return ps.executeUpdate() > 0;

        } catch (SQLException | NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": Error adding user.\nError: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the password for a user by username.
     *
     * @param userName The username of the user.
     * @return The password if the user exists, or null otherwise.
     */
    @Override
    public String getPasswordByUserName(String userName) {
        if (userName == null) {
            return null;
        }

        String sql = "SELECT password FROM Users WHERE userName = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": Error retrieving password.\nError: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves a user by their username.
     *
     * @param userName The username of the user.
     * @return The User object if found, or null otherwise.
     */
    @Override
    public User getUserByName(String userName) {
        if (userName == null) {
            return null;
        }

        String sql = "SELECT * FROM Users WHERE userName = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("userName"),
                            rs.getString("password"),
                            rs.getInt("userID"),
                            rs.getTimestamp("registrationDate").toLocalDateTime(),
                            rs.getTimestamp("subscriptionEndDate") != null
                                    ? rs.getTimestamp("subscriptionEndDate").toLocalDateTime()
                                    : null
                    );
                }
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": Error retrieving user by name.\nError: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deletes a user from the database.
     *
     * @param user The user to be deleted.
     * @return True if the user is deleted successfully, false otherwise.
     */
    @Override
    public boolean deleteUser(User user) {
        if (user == null) {
            return false;
        }

        String sql = "DELETE FROM Users WHERE userName = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUserName());

            return ps.executeUpdate() > 0;

        } catch (SQLException | NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": Error deleting user.\nError: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a user exists by their username.
     *
     * @param userName The username to check.
     * @return True if the user exists, false otherwise.
     */
    @Override
    public boolean existsbyUserName(String userName) {
        if (userName == null) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM Users WHERE userName = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": Error checking user existence.\nError: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The User object if found, or null otherwise.
     */
    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE userID = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return User.builder()
                            .firstName(rs.getString("firstName"))
                            .lastName(rs.getString("lastName"))
                            .userName(rs.getString("userName"))
                            .password(rs.getString("password"))
                            .userID(rs.getInt("userID"))
                            .build();
                }
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": Error retrieving user by ID.\nError: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves the subscription end date for a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The subscription end date, or null if not set.
     */
    @Override
    public LocalDate getSubscriptionEndDate(int userId) {
        String sql = "SELECT subscriptionEndDate FROM Users WHERE userID = ?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    java.sql.Date sqlDate = rs.getDate("subscriptionEndDate");
                    if (sqlDate != null) {
                        return sqlDate.toLocalDate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Updates the subscription end date for a user.
     *
     * @param userId     The ID of the user.
     * @param newEndDate The new subscription end date.
     * @return True if the update was successful, false otherwise.
     */
    @Override
    public boolean updateSubscriptionEndDate(int userId, LocalDate newEndDate) {
        String sql = "UPDATE Users SET subscriptionEndDate = ? WHERE userID = ?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(newEndDate));
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
