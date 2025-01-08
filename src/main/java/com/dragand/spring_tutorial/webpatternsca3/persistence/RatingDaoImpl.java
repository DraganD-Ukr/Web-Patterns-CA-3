package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Rating;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of RatingDAO interface for managing rating-related database operations.
 *
 * @Author Jo Art Mahilaga
 */
@Repository
public class RatingDaoImpl extends MySQLDao implements RatingDAO {

    public RatingDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }

    public RatingDaoImpl() {
        super();
    }

    /**
     * Deletes a rating from the database.
     *
     * @param rating The rating to be deleted.
     * @return True if the rating is deleted successfully, false otherwise.
     */
    @Override
    public boolean deleteRating(Rating rating) {
        if (rating == null) {
            return false;
        }

        String sql = "DELETE FROM ratings WHERE ratingID = ?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rating.getRatingID());
            return ps.executeUpdate() > 0;

        } catch (SQLException | NullPointerException e) {
            logException("Error deleting rating", e);
            return false;
        }
    }

    /**
     * Adds or updates a rating for a specific entity.
     *
     * @param type        The entity type (e.g., song, playlist, etc.).
     * @param entityID    The ID of the entity being rated.
     * @param userID      The ID of the user providing the rating.
     * @param ratingValue The rating value.
     * @return True if the operation is successful, false otherwise.
     */
    @Override
    public boolean addOrUpdateRating(String type, int entityID, int userID, int ratingValue) {
        String table = getTableName(type);

        String checkSql = "SELECT ratingID FROM ratings WHERE userID = ? AND " + table + "ID = ?";
        String updateSql = "UPDATE ratings SET ratingValue = ? WHERE userID = ? AND " + table + "ID = ?";
        String insertSql = "INSERT INTO ratings (userID, " + table + "ID, ratingValue) VALUES (?, ?, ?)";

        try (Connection conn = super.getConnection()) {
            // Check if rating exists
            try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                checkPs.setInt(1, userID);
                checkPs.setInt(2, entityID);
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next()) {
                        // Update the rating
                        try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                            updatePs.setInt(1, ratingValue);
                            updatePs.setInt(2, userID);
                            updatePs.setInt(3, entityID);
                            return updatePs.executeUpdate() > 0;
                        }
                    }
                }
            }
            // Insert a new rating
            try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                insertPs.setInt(1, userID);
                insertPs.setInt(2, entityID);
                insertPs.setInt(3, ratingValue);
                return insertPs.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            logException("Error adding or updating rating", e);
            return false;
        }
    }

    /**
     * Updates an existing rating.
     *
     * @param rating The rating to be updated.
     * @return True if the update is successful, false otherwise.
     */
    @Override
    public boolean updateRating(Rating rating) {
        if (rating == null) {
            return false;
        }

        String sql = "UPDATE ratings SET userID = ?, songID = ?, ratingValue = ? WHERE ratingID = ?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rating.getUserID());
            ps.setInt(2, rating.getSongID());
            ps.setInt(3, rating.getRatingValue());
            ps.setInt(4, rating.getRatingID());

            return ps.executeUpdate() > 0;

        } catch (SQLException | NullPointerException e) {
            logException("Error updating rating", e);
            return false;
        }
    }

    /**
     * Retrieves all ratings provided by a specific user.
     *
     * @param userID The ID of the user.
     * @return A list of ratings provided by the user.
     */
    @Override
    public List<Rating> getRatingsByUserID(int userID) {
        String sql = "SELECT * FROM ratings WHERE userID = ?";
        List<Rating> ratings = new ArrayList<>();

        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ratings.add(extractRating(rs));
                }
            }

        } catch (SQLException | NullPointerException e) {
            logException("Error retrieving ratings by user ID", e);
        }

        return ratings;
    }

    /**
     * Retrieves a specific rating by user ID and song ID.
     *
     * @param songID The ID of the song.
     * @param userID The ID of the user.
     * @return The Rating object if found, or null otherwise.
     */
    @Override
    public Rating getRatingByUserIDandSongID(int songID, int userID) {
        String sql = "SELECT * FROM ratings WHERE songID = ? AND userID = ?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, songID);
            ps.setInt(2, userID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractRating(rs);
                }
            }
        } catch (SQLException | NullPointerException e) {
            logException("Error retrieving rating by song ID and user ID", e);
        }

        return null;
    }

    /**
     * Retrieves all ratings for a specific song.
     *
     * @param songID The ID of the song.
     * @return A list of ratings for the song.
     */
    @Override
    public List<Rating> getRatingsBySongID(int songID) {
        String sql = "SELECT * FROM ratings WHERE songID = ?";
        List<Rating> ratings = new ArrayList<>();

        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, songID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ratings.add(extractRating(rs));
                }
            }

        } catch (SQLException | NullPointerException e) {
            logException("Error retrieving ratings by song ID", e);
        }

        return ratings;
    }

    /**
     * Adds a new rating to the database.
     *
     * @param rating The rating to be added.
     * @return True if the rating is added successfully, false otherwise.
     */
    @Override
    public boolean addRating(Rating rating) {
        String sql = "INSERT INTO Ratings (userID, songID, ratingValue) VALUES (?, ?, ?)";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rating.getUserID());
            ps.setInt(2, rating.getSongID());
            ps.setInt(3, rating.getRatingValue());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            logException("Error adding rating", e);
            return false;
        }
    }

    /**
     * Extracts a Rating object from the current row of a ResultSet.
     *
     * @param rs The ResultSet containing rating data.
     * @return The Rating object.
     * @throws SQLException If an error occurs during data extraction.
     */
    private Rating extractRating(ResultSet rs) throws SQLException {
        return new Rating(
                rs.getInt("ratingID"),
                rs.getInt("userID"),
                rs.getInt("songID"),
                rs.getInt("ratingValue")
        );
    }

    /**
     * Logs an exception with a custom message.
     *
     * @param message The message to log.
     * @param e       The exception to log.
     */
    private void logException(String message, Exception e) {
        System.out.println(LocalDateTime.now() + ": " + message + ".\nError: " + e.getMessage());
        e.printStackTrace();
    }

    /**
     * Resolves the table name based on the entity type.
     *
     * @param type The type of entity (e.g., song, playlist).
     * @return The corresponding table name.
     * @throws IllegalArgumentException If the type is invalid.
     */
    private String getTableName(String type) {
        switch (type.toLowerCase()) {
            case "song":
                return "song";
            case "playlist":
                return "playlist";
            case "artist":
                return "artist";
            case "album":
                return "album";
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }
}
