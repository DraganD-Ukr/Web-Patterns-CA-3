package com.dragand.spring_tutorial.webpatternsca3.persistence;
import com.dragand.spring_tutorial.webpatternsca3.business.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @Author: Jo Art Mahilaga
 */

public class UserDaoImpl extends MySQLDao implements UserDAO {

    public UserDaoImpl(String databaseName) {
        super(databaseName);
    }

    @Override
    public boolean addUser(User user) {

        // Validate input
        if (user == null) {
            return false;
        }



        try ( Connection conn = super.getConnection();
              PreparedStatement ps = conn.prepareStatement("INSERT INTO Users (firstName, lastName, userName, password) VALUES (?, ?, ?, ?)")) {

            // Set parameters for the prepared statement
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getPassword());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(LocalDateTime.now() + ": An SQLException occurred while adding a user \n Error:" + e.getMessage());
            e.printStackTrace();
            return false;


        } catch (NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": A NullPointerException occurred \n Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getPasswordByUserName(String userName) {

        String password = null;
        // Validate input
        if (userName == null) {
            return null;
        }



        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Users WHERE userName = ?")) {

            // Set parameters for the prepared statement
            ps.setString(1, userName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    password = rs.getString("password");
                }

            }
        } catch (SQLException e) {
            System.out.println(LocalDateTime.now() + ": An SQLException occurred in the getUserByID() method.\nError: " + e.getMessage());
            e.printStackTrace();


        } catch (NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": A NullPointerException occurred in the getUserByID() method.\nError: " + e.getMessage());
            e.printStackTrace();
        }

        return password;
    }


    @Override
    public User getUserByName(String userName) {

        // Validate input
        if (userName == null) {
            return null;
        }

        User user = null;


        try (
                Connection con = super.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Users WHERE userName = ?")) {

            // Set parameters for the prepared statement
            ps.setString(1, userName);

            try (ResultSet rs = ps.executeQuery()) {
                // Extract password if user found
                if (rs.next()) {
                    user = new User(
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("userName"),
                            rs.getString("password"),
                            rs.getInt("userID")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(LocalDateTime.now() + ": An SQLException occurred in the getUserByID() method.\nError: " + e.getMessage());
            e.printStackTrace();


        } catch (NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": A NullPointerException occurred in the getUserByID() method.\nError: " + e.getMessage());
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public boolean deleteUser(User user) {
        // Validate input
        if (user == null) {
            return false;
        }



        try ( Connection con = super.getConnection();
              PreparedStatement ps = con.prepareStatement("DELETE FROM Users WHERE userName= ?")) {

            // Set parameters for the prepared statement
            ps.setString(1, user.getUserName());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(LocalDateTime.now() + ": An SQLException occurred while deleting a user.\nError: " + e.getMessage());
            e.printStackTrace();
            return false;

        } catch (NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": A NullPointerException occurred while deleting a user.\nError: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean existsbyUserName(String userName) {
        // Validate input
        if (userName == null) {
            return false;
        }

        String query = "SELECT COUNT(*) FROM Users WHERE userName = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            // Set parameters for the prepared statement
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println(LocalDateTime.now() + ": An SQLException occurred while checking if user exists.\nError: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": A NullPointerException occurred while checking if user exists.\nError: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUserById(int userId) {

        User user = null;
        String query = "SELECT * FROM Users WHERE userID = ?";

        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    user = User.builder()
                            .firstName(rs.getString("firstName"))
                            .lastName(rs.getString("lastName"))
                            .userName(rs.getString("userName"))
                            .password(rs.getString("password"))
                            .userID(rs.getInt("userID"))
                            .build();
                }

            } catch (SQLException e) {
                System.out.println(LocalDateTime.now() + ": SQLException occurred while getting the playlist.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println(LocalDateTime.now() + ": SQLException occurred while preparing the SQL query");
            e.printStackTrace();
        }

        return user;
    }
}
