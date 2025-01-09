package com.dragand.spring_tutorial.webpatternsca3.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Aloysius Wilfred Pacheco D00253302
 * Test class for PlaylistDaoImpl.
 * To test all database operations for the playlist table.
 */
class PlaylistDaoImplTest {

    /**
     * The DAO to test.
     */
    private PlaylistDaoImpl playlistDao;

    @BeforeEach
    void setUp() {
        playlistDao = new PlaylistDaoImpl();
    }

    /**
     * Test the getPlaylistByUserID method
     * to retrieve all playlists in the database that belong to the user by user ID.
     */
    @Test
    void getPlaylistByUserID() {
    }

    /**
     * Test the getPlaylistByUsernameAndName method
     * to retrieve all playlists in the database that belong to the user by username and query.
     */
    @Test
    void getPlaylistByUsernameAndName() {
    }

    /**
     * Test the getAllPublicPlaylists method
     * to retrieve all public playlists in the database.
     */
    @Test
    void getAllPublicPlaylists() {
    }

    /**
     * Test the getPlaylistByUsername method
     * to retrieve all playlists created by a user, using their username.
     */
    @Test
    void getPlaylistByUsername() {
    }

    /**
     * Test the getPlaylistById method
     * to retrieve a playlist by its ID from the database.
     */
    @Test
    void getPlaylistById() {
    }

    /**
     * Test the getPlaylistByName method
     * to retrieve a playlist by its name from the database.
     */
    @Test
    void getPlaylistByName() {
    }

    /**
     * Test the getAllPlaylistbyName method
     * to retrieve all playlists by a name and their visibility (public/private).
     */
    @Test
    void getAllPlaylistbyName() {
    }

    /**
     * Test the createPlaylist method
     * to create a new playlist in the database.
     */
    @Test
    void createPlaylist() {
    }

    /**
     * Test the deletePlaylist method
     * to delete a playlist from the database using its ID.
     */
    @Test
    void deletePlaylist() {
    }

    /**
     * Test the renamePlaylist method
     * to rename a playlist in the database using its ID and new name.
     */
    @Test
    void renamePlaylist() {
    }
}
