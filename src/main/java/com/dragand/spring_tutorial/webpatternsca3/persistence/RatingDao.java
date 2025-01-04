package com.dragand.spring_tutorial.webpatternsca3.persistence;



import com.dragand.spring_tutorial.webpatternsca3.business.Rating;
import java.util.List;

/**
 * Rating DAO interface used to define the methods that will be implemented in the RatingDAOImpl class
 *
 * @author Jo Art Mahilaga
 */

public interface RatingDao {

    /**
     * Adds a rating to the database
     *
     * @param rating The rating to add to the database
     * @return {@code true} if the rating was added successfully, otherwise {@code false}
     */
    boolean addRating(Rating rating);

    /**
     * Removes a rating from the database
     *
     * @param rating The rating to remove from the database
     * @return {@code true} if the rating was removed successfully, otherwise {@code false}
     */
    boolean deleteRating(Rating rating);

    /**
     * Gets all ratings for a specific song using its ID
     *
     * @param songID The ID of the song to find ratings for
     * @return A list of {@link Rating} objects for the song
     */
    List<Rating> getRatingsBySongID(int songID);

    /**
     * Gets all ratings made by a specific user using their ID
     *
     * @param userID The ID of the user to find ratings for
     * @return A list of {@link Rating} objects made by the user
     */
    List<Rating> getRatingsByUserID(int userID);

    /**
     * Gets a specific rating by user ID and song ID
     *
     * @param songID The ID of the song
     * @param userID The ID of the user
     * @return The {@link Rating} object if found, otherwise {@code null}
     */
    Rating getRatingByUserIDandSongID(int songID, int userID);

    /**
     * Updates an existing rating in the database
     *
     * @param rating The rating to update
     * @return {@code true} if the rating was updated successfully, otherwise {@code false}
     */
    boolean updateRating(Rating rating);
}