package com.dragand.spring_tutorial.webpatternsca3.persistence;
import com.dragand.spring_tutorial.webpatternsca3.business.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jo Art Mahilaga
 */

public class RatingDaoImpl extends MySQLDao implements RatingDAO {

    public RatingDaoImpl(String databaseName) {
        super(databaseName);
    }

    @Override
    public boolean addRating(Rating rating) {
        // Validates the input parameters
        if (rating == null || rating.getUserID() <= 0 || rating.getSongID() <= 0 ||
                rating.getRatingValue() < 1 || rating.getRatingValue() > 5) {
            return false;
        }

        String sql = "INSERT INTO ratings (ratingID, userID, songID, ratingValue) VALUES (?, ?, ?, ?)";

        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // goes through the prepared statement and sets the values
            ps.setInt(1, rating.getRatingID());
            ps.setInt(2, rating.getUserID());
            ps.setInt(3, rating.getSongID());
            ps.setInt(4, rating.getRatingValue());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;


        } catch (SQLException e) {
            // this code catches if there is a duplicate entry error
            if (  e.getErrorCode() == 1062) {
                System.out.println(LocalDateTime.now() + ": Duplicate entry error occurred while adding a rating. RatingID " + rating.getRatingID() + " already exists.");
                return false;
            } else {
                System.out.println(LocalDateTime.now() + ": An SQLException occurred while adding a rating \n Error: " + e.getMessage());
                e.printStackTrace();
                return false;
            }


        } catch (NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": A NullPointerException occurred while adding a rating \n Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteRating(Rating rating) {

        if (rating == null) {
            return false;
        }


        String sql = "DELETE FROM ratings WHERE ratingID = ?";

        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rating.getRatingID());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(LocalDateTime.now() + ": An SQLException occurred while deleting a rating \n Error: " + e.getMessage());
            e.printStackTrace();
            return false;

        } catch (NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": A NullPointerException occurred while deleting a rating \n Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateRating(Rating rating) {
        if (rating == null) {
            return false;
        }


        String sql = "UPDATE ratings SET userID = ?, songID = ?, ratingValue = ? WHERE ratingID = ?";


        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // goes through the prepared statement and sets the values
            ps.setInt(1, rating.getUserID());
            ps.setInt(2, rating.getSongID());
            ps.setInt(3, rating.getRatingValue());
            ps.setInt(4, rating.getRatingID());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(LocalDateTime.now() + ": An SQLException occurred while updating a rating \n Error: " + e.getMessage());
            e.printStackTrace();
            return false;

        } catch (NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": A NullPointerException occurred while updating a rating \n Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Rating> getRatingsByUserID(int userID) {


        String sql = "SELECT * FROM ratings WHERE userID = ?";

        List<Rating> rating = new ArrayList<>();



        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // goes through the prepared statement and sets the values
            ps.setInt(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    rating.add(new Rating(
                            rs.getInt("ratingID"),
                            rs.getInt("userID"),
                            rs.getInt("songID"),
                            rs.getInt("ratingValue"))
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(LocalDateTime.now() + ": An SQLException occurred while retrieving rating by user ID \n Error: " + e.getMessage());
            e.printStackTrace();

        } catch (NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": A NullPointerException occurred while retrieving rating by user ID \n Error: " + e.getMessage());
            e.printStackTrace();
        }

        return rating;
    }

    @Override
    public Rating getRatingByUserIDandSongID(int songID, int userID) {
        String sql = "SELECT * FROM ratings WHERE songID = ? AND userID = ?";
        Rating rating = null;



        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // goes through the prepared statement and sets the values
            ps.setInt(1, songID);
            ps.setInt(2, userID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rating = new Rating(
                            rs.getInt("ratingID"),
                            rs.getInt("userID"),
                            rs.getInt("songID"),
                            rs.getInt("ratingValue")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(LocalDateTime.now() + ": An SQLException occurred while retrieving rating by song ID and user ID \n Error: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": A NullPointerException occurred while retrieving rating by song ID and user ID \n Error: " + e.getMessage());
            e.printStackTrace();
        }

        return rating;
    }


    @Override
    public List<Rating> getRatingsBySongID(int songID) {
        String sql = "SELECT * FROM ratings WHERE songID = ?";
        List<Rating> rating = new ArrayList<Rating>();



        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // goes through the prepared statement and sets the values
            ps.setInt(1, songID);

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {

                    rating.add(new Rating(
                            rs.getInt("ratingID"),
                            rs.getInt("userID"),
                            rs.getInt("songID"),
                            rs.getInt("ratingValue"))
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(LocalDateTime.now() + ": An SQLException occurred while retrieving rating by song ID \n Error: " + e.getMessage());
            e.printStackTrace();

        } catch (NullPointerException e) {
            System.out.println(LocalDateTime.now() + ": A NullPointerException occurred while retrieving rating by song ID \n Error: " + e.getMessage());
            e.printStackTrace();
        }

        return rating;
    }
}


