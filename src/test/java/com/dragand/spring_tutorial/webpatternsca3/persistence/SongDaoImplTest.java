package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Song;
import com.dragand.spring_tutorial.webpatternsca3.persistence.*;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SongDAOImplTest {

    private static SongDaoImpl songDAO;
    /**
     * Sets up the test environment .
     * Initializes the SongDAOImpl instance with database.
     */
    @BeforeAll
    static void setUpBeforeClass() {
        songDAO = new SongDaoImpl("ca3_test");

        Song testSong = Song.builder()
                .songID(1)
                .title("Come Together")
                .albumID(1)
                .artistID(1)
                .length(LocalTime.of(0, 4, 20))
                .ratingCount(2)
                .averageRating(3.0)
                .ratingsSum(6)
                .build();

        // Song to add to the database for song manipulation tests
        Song testSongToaddToDatabase = Song.builder()
                .title("Befall")
                .albumID(2)
                .artistID(2)
                .length(LocalTime.of(0, 3, 10))
                .ratingCount(0)
                .averageRating(0)
                .ratingsSum(0)
                .build();

        // Song to delete from the database
        Song testSongToDeleteFromDatabase = Song.builder()
                .title("General feeling")
                .albumID(3)
                .artistID(4)
                .length(LocalTime.of(0, 2, 10))
                .ratingCount(0)
                .averageRating(0)
                .ratingsSum(0)
                .build();
    }

    @AfterAll
    static void cleanup() {
        // Cleanup songs added during the test
    }


    /**
     * Tests the findSongByTitle method.
     * Makes sure that a song with the specified title can be found.
     */
    @Test
    void testFindSongByTitle() {
        Song fetchedSong = songDAO.findSongByTitle("Come Together");
        assertNotNull(fetchedSong, "Song should be found");
        assertEquals("Come Together", fetchedSong.getTitle());
    }

    /**
     * Tests the getAllSongsByTitle method.
     * Makes sure that songs with the specified title can be returned.
     */
    @Test
    void testGetAllSongsByTitle() {
        List<Song> songs = songDAO.getAllSongsByTitle("Together");
        assertNotNull(songs, "Songs list should not be null");
        assertFalse(songs.isEmpty(), "Songs list should not be empty");
        assertTrue(songs.stream().anyMatch(song -> song.getTitle().contains("Together")));
    }


}