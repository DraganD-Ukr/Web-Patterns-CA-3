package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Song;
import com.dragand.spring_tutorial.webpatternsca3.persistence.*;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.ArrayList;
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
        songDAO = new SongDaoImpl();
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
        List<Song> songsControll = songDAO.getAllSongsByTitle("s");
        List<Song> songsTOtest = new ArrayList<>();
        songsTOtest.addAll(List.of(
                        Song.builder().songID(3).title("Stairway to Heaven").albumID(2).artistID(2).length(LocalTime.of(0, 8, 2)).ratingCount(2).averageRating(4.5).ratingsSum(9).build(),
                        Song.builder().songID(5).title("Blank Space").albumID(3).artistID(3).length(LocalTime.of(0, 3, 51)).ratingCount(1).averageRating(4.0).ratingsSum(4).build(),
                        Song.builder().songID(8).title("Lose Yourself").albumID(5).artistID(5).length(LocalTime.of(0, 5, 26)).ratingCount(0).averageRating(0).ratingsSum(0).build()
        ));
        assertEquals(songsControll, songsTOtest);
    }


    /**
     * Tests the findAllSongsFromArtist method.
     * Makes sure that songs from the specified artist can be returned.
     */
    @Test
    void getAllSongs() {
        List<Song> songsControll = songDAO.getAllSongs();
        List<Song> songsTOtest = new ArrayList<>();
        songsTOtest.addAll(List.of(
                Song.builder().songID(1).title("Come Together").albumID(1).artistID(1).length(LocalTime.of(0, 4, 20)).ratingCount(2).averageRating(3.0).ratingsSum(6).build(),
                Song.builder().songID(2).title("Let It Be").albumID(1).artistID(1).length(LocalTime.of(0, 3, 50)).ratingCount(2).averageRating(3.5).ratingsSum(7).build(),
                Song.builder().songID(3).title("Stairway to Heaven").albumID(2).artistID(2).length(LocalTime.of(0, 8, 2)).ratingCount(2).averageRating(4.5).ratingsSum(9).build(),
                Song.builder().songID(4).title("Whole Lotta Love").albumID(2).artistID(2).length(LocalTime.of(0, 5, 34)).ratingCount(2).averageRating(4.0).ratingsSum(8).build(),
                Song.builder().songID(5).title("Blank Space").albumID(3).artistID(3).length(LocalTime.of(0, 3, 51)).ratingCount(1).averageRating(4.0).ratingsSum(4).build(),
                Song.builder().songID(6).title("Formation").albumID(4).artistID(4).length(LocalTime.of(0, 3, 26)).ratingCount(0).averageRating(0.0).ratingsSum(0).build(),
                Song.builder().songID(7).title("Halo").albumID(4).artistID(4).length(LocalTime.of(0, 3, 44)).ratingCount(0).averageRating(0.0).ratingsSum(0).build(),
                Song.builder().songID(8).title("Lose Yourself").albumID(5).artistID(5).length(LocalTime.of(0, 5, 26)).ratingCount(0).averageRating(0.0).ratingsSum(0).build(),
                Song.builder().songID(9).title("Without Me").albumID(5).artistID(5).length(LocalTime.of(0, 4, 0)).ratingCount(0).averageRating(0.0).ratingsSum(0).build()
        ));
        assertEquals(songsControll, songsTOtest);
    }

    /**
     * Tests the findSongById method.
     * Makes sure that a song with the specified id can be found.
     */
    @Test
    void findSongById() {
        Song fetchedSong = songDAO.findSongById(1);
        Song songToTest = Song.builder().songID(1).title("Come Together").albumID(1).artistID(1).length(LocalTime.of(0, 4, 20)).ratingCount(2).averageRating(3.0).ratingsSum(6).build();
        assertEquals(fetchedSong, songToTest);
    }

    /**
     * Tests the findAllSongsFromArtist method.
     * Makes sure that songs from the specified artist can be returned.
     */
    @Test
    void findSongByTitle() {
        Song fetchedSong = songDAO.findSongByTitle("Come Together");
        Song songToTest = Song.builder().songID(1).title("Come Together").albumID(1).artistID(1).length(LocalTime.of(0, 4, 20)).ratingCount(2).averageRating(3.0).ratingsSum(6).build();
        assertEquals(fetchedSong, songToTest);
    }

    /**
     * Tests the findAllSongsFromArtist method.
     * Makes sure that songs from the specified artist can be returned.
     */

    @Test
    void findAllSongsFromArtist() {
        List<Song> songsControll = songDAO.findAllSongsFromArtist("Led Zeppelin");
        List<Song> songsTOtest = new ArrayList<>();
        songsTOtest.addAll(List.of(
                Song.builder().songID(3).title("Stairway to Heaven").albumID(2).artistID(2).length(LocalTime.of(0, 8, 2)).ratingCount(2).averageRating(4.5).ratingsSum(9).build(),
                Song.builder().songID(4).title("Whole Lotta Love").albumID(2).artistID(2).length(LocalTime.of(0, 5, 34)).ratingCount(2).averageRating(4.0).ratingsSum(8).build()
        ));
        assertEquals(songsControll, songsTOtest);
    }

    /**
     * Tests the findAllSongsFromArtistById method.
     * Makes sure that songs from the specified artist can be returned.
     */
    @Test
    void findAllSongsFromArtistById() {
        List<Song> songsControll = songDAO.findAllSongsFromArtistById(2);
        List<Song> songsTOtest = new ArrayList<>();
        songsTOtest.addAll(List.of(
                Song.builder().songID(3).title("Stairway to Heaven").albumID(2).artistID(2).length(LocalTime.of(0, 8, 2)).ratingCount(2).averageRating(4.5).ratingsSum(9).build(),
                Song.builder().songID(4).title("Whole Lotta Love").albumID(2).artistID(2).length(LocalTime.of(0, 5, 34)).ratingCount(2).averageRating(4.0).ratingsSum(8).build()
        ));
        assertEquals(songsControll, songsTOtest);
    }

    /**
     * Tests the findAllFromAlbumByName method.
     * Makes sure that songs from the specified album can be returned.
     */
    @Test
    void findAllFromAlbumByName() {
        List<Song> songsControll = songDAO.findAllFromAlbumByName("Led Zeppelin IV");
        List<Song> songsTOtest = new ArrayList<>();
        songsTOtest.addAll(List.of(
                Song.builder().songID(3).title("Stairway to Heaven").albumID(2).artistID(2).length(LocalTime.of(0, 8, 2)).ratingCount(2).averageRating(4.5).ratingsSum(9).build(),
                Song.builder().songID(4).title("Whole Lotta Love").albumID(2).artistID(2).length(LocalTime.of(0, 5, 34)).ratingCount(2).averageRating(4.0).ratingsSum(8).build()
        ));
        assertEquals(songsControll, songsTOtest);
    }

    /**
     * Tests the findAllFromAlbumById method.
     * Makes sure that songs from the specified album can be returned.
     */
    @Test
    void findAllFromAlbumById() {
        List<Song> songsControll = songDAO.findAllFromAlbumById(2);
        List<Song> songsTOtest = new ArrayList<>();
        songsTOtest.addAll(List.of(
                Song.builder().songID(3).title("Stairway to Heaven").albumID(2).artistID(2).length(LocalTime.of(0, 8, 2)).ratingCount(2).averageRating(4.5).ratingsSum(9).build(),
                Song.builder().songID(4).title("Whole Lotta Love").albumID(2).artistID(2).length(LocalTime.of(0, 5, 34)).ratingCount(2).averageRating(4.0).ratingsSum(8).build()
        ));
        assertEquals(songsControll, songsTOtest);
    }

    //Edit methods
    //add song
    /**
     * Tests the addSong method.
     * Makes sure that a song can be added to the database.
     */
    @Test
    void addSong() {
        Song song = Song.builder().title("TestSong").albumID(1).artistID(1).length(LocalTime.of(0, 4, 20)).ratingCount(2).averageRating(3.0).ratingsSum(6).build();
        assertTrue(songDAO.addSong(song));

        //check if song is added really
        boolean found = songDAO.findSongByTitle("TestSong") != null;
        if(found){
            songDAO.deleteSong(songDAO.findSongByTitle("TestSong").getSongID());
        }
        assertTrue(found,"Song should be found");
    }

    //remove song
    /**
     * Tests the removeSong method.
     * Makes sure that a song can be removed from the database.
     * it deletes the song with the title "TestSong" that was added in the previous test
     */
    @Test
    void removeSong() {
        songDAO.addSong(Song.builder().title("TestSong").albumID(1).artistID(1).length(LocalTime.of(0, 4, 20)).ratingCount(2).averageRating(3.0).ratingsSum(6).build());
        Song song = songDAO.findSongByTitle("TestSong");
        assertTrue(songDAO.deleteSong(song.getSongID()));

        //check if song is removed really
        boolean found = songDAO.findSongByTitle("TestSong") == null;
        if(!found){
            songDAO.deleteSong(songDAO.findSongByTitle("TestSong").getSongID());
        }
        assertTrue(found, "Song should not be found");

    }

    //null tests(test that test if lists are empty or conditions that might fail)
    //test for empty list return for AllSongsByTitle
    /**
     * Tests the getAllSongsByTitle method.
     * Tests if a song with the specified title that does not exist returns an empty list.
     */
    @Test
    void testGetAllSongsByTitleEmpty() {
        List<Song> songsControll = songDAO.getAllSongsByTitle("z");
        List<Song> songsTOtest = new ArrayList<>();
        assertEquals(songsControll, songsTOtest);
    }

    //test for empty list return for findAllSongsFromArtist
    /**
     * Tests the findAllSongsFromArtist method.
     * Tests if an artist with the specified name that does not exist returns an empty list.
     */
    @Test

    void findAllSongsFromArtistEmpty() {
        List<Song> songsControll = songDAO.findAllSongsFromArtist("kenobi");
        List<Song> songsTOtest = new ArrayList<>();
        assertEquals(songsControll, songsTOtest);
    }

    //test for empty list return for findAllSongsFromArtistById
    /**
     * Tests the findAllSongsFromArtistById method.
     * Tests if an artist with the specified id that does not exist returns an empty list.
     */
    @Test
    void findAllSongsFromArtistByIdEmpty() {
        List<Song> songsControll = songDAO.findAllSongsFromArtistById(6);
        List<Song> songsTOtest = new ArrayList<>();
        assertEquals(songsControll, songsTOtest);
    }

    //test for empty list return for findAllFromAlbumByName
    /**
     * Tests the findAllFromAlbumByName method.
     * Tests if an album with the specified name that does not exist returns an empty list.
     */
    @Test
    void findAllFromAlbumByNameEmpty() {
        List<Song> songsControll = songDAO.findAllFromAlbumByName("TestAlbum");
        List<Song> songsTOtest = new ArrayList<>();
        assertEquals(songsControll, songsTOtest);
    }

    //test for empty list return for findAllFromAlbumById
    /**
     * Tests the findAllFromAlbumById method.
     * Tests if an album with the specified id that does not exist returns an empty list.
     */
    @Test
    void findAllFromAlbumByIdEmpty() {
        List<Song> songsControll = songDAO.findAllFromAlbumById(6);
        List<Song> songsTOtest = new ArrayList<>();
        assertEquals(songsControll, songsTOtest);
    }


    //test for null return for findSongById
    /**
     * Tests the findSongById method.
     * Tests if a song with the specified id that does not exist returns null.
     */
    @Test
    void findSongByIdNull() {
        Song fetchedSong = songDAO.findSongById(10000);
        assertNull(fetchedSong, "Song should not be found");
    }
}