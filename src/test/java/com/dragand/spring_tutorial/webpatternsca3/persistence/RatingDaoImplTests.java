package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Rating;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RatingDaoImpl
 *
 * @Author Jo Art Mahilaga
 */
public class RatingDaoImplTests {

    private static RatingDaoImpl ratingDao;

    private static Rating ratingToBeAdded, ratingToBeUpdated, ratingToBeDeleted, duplicateRating;
    private static List<Rating> userRatings, songRatings;

    /**
     * Initialize the DAO instance and sample test data.
     */
    @BeforeAll
    public static void init() {
        ratingDao = new RatingDaoImpl("database-test.properties");

        // Sample ratings for testing
        userRatings = List.of(
                new Rating(1, 1, 1, 4),
                new Rating(2, 1, 2, 3)
        );

        songRatings = List.of(
                new Rating(2, 1, 2, 3),
                new Rating(3, 2, 2, 4)
        );

        ratingToBeAdded = new Rating(4, 2, 3, 5);
        ratingToBeUpdated = new Rating(5, 2, 4, 3);
        ratingToBeDeleted = new Rating(6, 2, 5, 4);
        duplicateRating = new Rating(4, 2, 3, 5); // Same as `ratingToBeAdded`
    }

    /**
     * Cleanup test data after all tests have been executed.
     */
    @AfterAll
    public static void cleanUp() {
        ratingDao.deleteRating(ratingToBeAdded);
        ratingDao.deleteRating(ratingToBeUpdated);
        ratingDao.deleteRating(ratingToBeDeleted);
    }

    /**
     * Test adding a valid rating.
     */
    @Test
    public void testAddRating_Success() {
        boolean result = ratingDao.addRating(ratingToBeAdded);
        assertTrue(result, "Adding a valid rating should succeed.");
    }

    /**
     * Test adding a null rating, expecting failure.
     */
    @Test
    public void testAddRating_Failure_NullRating() {
        boolean result = ratingDao.addRating(null);
        assertFalse(result, "Adding a null rating should fail.");
    }

    /**
     * Test adding a duplicate rating, expecting failure.
     */
    @Test
    public void testAddRating_Failure_DuplicateRating() {
        ratingDao.addRating(ratingToBeAdded); // Add rating first
        boolean result = ratingDao.addRating(duplicateRating);
        assertFalse(result, "Adding a duplicate rating should fail.");
    }

    /**
     * Test updating an existing rating.
     */
    @Test
    public void testUpdateRating_Success() {
        ratingDao.addRating(ratingToBeUpdated);
        ratingToBeUpdated.setRatingValue(5); // Update rating value
        boolean result = ratingDao.updateRating(ratingToBeUpdated);
        assertTrue(result, "Updating an existing rating should succeed.");

        Rating updatedRating = ratingDao.getRatingByUserIDandSongID(
                ratingToBeUpdated.getUserID(), ratingToBeUpdated.getSongID());
        assertNotNull(updatedRating, "Updated rating should exist.");
        assertEquals(5, updatedRating.getRatingValue(), "Updated rating value should match.");
    }

    /**
     * Test updating a non-existent rating, expecting failure.
     */
    @Test
    public void testUpdateRating_Failure_NonExistentRating() {
        Rating nonExistentRating = new Rating(60, 69, 70, 3);
        boolean result = ratingDao.updateRating(nonExistentRating);
        assertFalse(result, "Updating a non-existent rating should fail.");
    }

    /**
     * Test deleting an existing rating.
     */
    @Test
    public void testDeleteRating_Success() {
        ratingDao.addRating(ratingToBeDeleted); // Ensure the rating exists
        boolean result = ratingDao.deleteRating(ratingToBeDeleted);
        assertTrue(result, "Deleting an existing rating should succeed.");

        Rating deletedRating = ratingDao.getRatingByUserIDandSongID(
                ratingToBeDeleted.getUserID(), ratingToBeDeleted.getSongID());
        assertNull(deletedRating, "Deleted rating should no longer exist.");
    }

    /**
     * Test deleting a null rating, expecting failure.
     */
    @Test
    public void testDeleteRating_Failure_NullRating() {
        boolean result = ratingDao.deleteRating(null);
        assertFalse(result, "Deleting a null rating should fail.");
    }

    /**
     * Test deleting a non-existent rating, expecting failure.
     */
    @Test
    public void testDeleteRating_Failure_NonExistentRating() {
        Rating nonExistentRating = new Rating(69, 79, 69, 3);
        boolean result = ratingDao.deleteRating(nonExistentRating);
        assertFalse(result, "Deleting a non-existent rating should fail.");
    }

    /**
     * Test retrieving ratings by a valid user ID.
     */
    @Test
    public void testGetRatingsByUserID_Success() {
        List<Rating> ratings = ratingDao.getRatingsByUserID(1);
        assertNotNull(ratings, "Ratings list should not be null.");
        assertFalse(ratings.isEmpty(), "Ratings list should not be empty.");
        assertEquals(userRatings, ratings, "Ratings should match the test data.");
    }

    /**
     * Test retrieving ratings for a non-existent user ID.
     */
    @Test
    public void testGetRatingsByUserID_Empty() {
        List<Rating> ratings = ratingDao.getRatingsByUserID(69);
        assertNotNull(ratings, "Ratings list should not be null.");
        assertTrue(ratings.isEmpty(), "Ratings list should be empty for a non-existent user.");
    }

    /**
     * Test retrieving ratings by a valid song ID.
     */
    @Test
    public void testGetRatingsBySongID_Success() {
        List<Rating> ratings = ratingDao.getRatingsBySongID(2);
        assertNotNull(ratings, "Ratings list should not be null.");
        assertFalse(ratings.isEmpty(), "Ratings list should not be empty.");
        assertEquals(songRatings, ratings, "Ratings should match the test data.");
    }

    /**
     * Test retrieving ratings for a non-existent song ID.
     */
    @Test
    public void testGetRatingsBySongID_Empty() {
        List<Rating> ratings = ratingDao.getRatingsBySongID(69);
        assertNotNull(ratings, "Ratings list should not be null.");
        assertTrue(ratings.isEmpty(), "Ratings list should be empty for a non-existent song.");
    }

    /**
     * Test retrieving a rating by valid user ID and song ID.
     */
    @Test
    public void testGetRatingByUserIDandSongID_Success() {
        Rating rating = ratingDao.getRatingByUserIDandSongID(4, 7);
        assertNotNull(rating, "Rating should not be null.");
        assertEquals(userRatings.get(1), rating, "Retrieved rating should match the test data.");
    }

    /**
     * Test retrieving a rating for a non-existent user ID and song ID.
     */
    @Test
    public void testGetRatingByUserIDandSongID_NotFound() {
        Rating rating = ratingDao.getRatingByUserIDandSongID(69, 69);
        assertNull(rating, "Rating should be null for a non-existent user and song.");
    }
}
