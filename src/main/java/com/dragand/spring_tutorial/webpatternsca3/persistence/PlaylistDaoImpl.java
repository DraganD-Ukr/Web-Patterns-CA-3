package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.*;
import com.dragand.spring_tutorial.webpatternsca3.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Playlist DAO implementation used to interact with the database
 *
 * @author Aloysius Wilfred Pacheco D00253302
 */

@Repository
@Slf4j
public class PlaylistDaoImpl extends MySQLDao implements PlaylistDAO{

    private final UserDaoImpl userDaoImpl;

    /**
     * Default constructor (uses default database set)
     */
    public PlaylistDaoImpl(UserDaoImpl userDaoImpl){
        super();
        this.userDaoImpl = userDaoImpl;
    }

    //Search query methods
    /**
     * Retrieve a playlist by the username of the user who created it
     * This method accomplishes this by first retrieving the id of the user from the username
     * using the UserDaoImpl class and then using the id to retrieve the playlists.
     *
     * @param username the username of the user who created the playlist.
     * @return a list of all playlists created by the user
     */
    @Override
    public List<Playlist> getPlaylistByUsername(String username) {
        List<Playlist> playlists = new ArrayList<>();

        //Retrieve the user id from the username using the UserDaoImpl class
        //to make sure DAO integrity is maintained
        int userId = new UserDaoImpl().getUserByName(username).getUserID();

        String query = "SELECT * FROM playlists WHERE userID = ?";

        try(Connection con = super.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            try(ResultSet rs = ps.executeQuery()) {
                playlists = mapToPlaylists(rs);
            }
        } catch (SQLException e) {
            logError("SQLException occurred while retrieving playlists by username", e);
        }

        return playlists;
    }

    /**
     * Retrieve a playlist by its id from the sql database using a prepared statement
     *
     * @param id the id of the playlist to retrieve
     * @return the playlist with the given id.
     */
    @Override
    public Playlist getPlaylistById(int id) {
        Playlist playlist = null;
        String query = "SELECT * FROM playlists WHERE playlistID = ?";

        try(Connection con = super.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    playlist = mapToPlaylist(rs);
                }
            }
        } catch (SQLException e) {
            logError("SQLException occurred while retrieving playlist by id", e);
        }

        return playlist;
    }

    /**
     * Retrieve a playlist by its name from the sql database using a prepared statement
     *
     * @param name the name of the playlist to retrieve.
     * @return the playlist with the given name.
     */
    @Override
    public Playlist getPlaylistByName(String name) {
        Playlist playlist = null;
        String query = "SELECT * FROM playlists WHERE name = ?";

        try(Connection con = super.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    playlist = mapToPlaylist(rs);
                }
            }
        } catch (SQLException e) {
            logError("SQLException occurred while retrieving playlist by name", e);
        }

        return playlist;
    }

    //Database Data Entry/Edit query
    /**
     * Creates a playlist in the database
     *
     * @param playlist the playlist to be created
     * @return the id of the playlist created
     */
    @Override
    public int createPlaylist(Playlist playlist) {
        return 0;
    }

    /**
     * Deletes a playlist from the database by its id
     *
     * @param id the id of the playlist to be deleted
     * @return {@code true} if the playlist was deleted successfully, if not {@code false}
     */
    @Override
    public boolean deletePlaylist(int id) {
        return false;
    }

    /**
     * Adds a song to a playlist
     *
     * @param playlistId the id of the playlist to add the song to
     * @param songId     the id of the song to be added to the playlist
     * @return {@code true} if the song was added successfully, if not {@code false}
     */
    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        return false;
    }

    /**
     * Removes a song from a playlist
     *
     * @param playlistId the id of the playlist to remove the song from
     * @param songId     the id of the song to be removed from the playlist
     * @return {@code true} if the song was removed successfully, if not {@code false}
     */
    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        return false;
    }

    /**
     * Renames a playlist
     *
     * @param playlistId the id of the playlist to be renamed
     * @param newName    the new name of the playlist
     * @return {@code true} if the playlist was renamed successfully, if not {@code false}
     */
    @Override
    public boolean renamePlaylist(int playlistId, String newName) {
        return false;
    }

    //Extended Functionality
    //To be implemented in the future


    //Helper methods
    /**
     * Maps a result set to a playlist object to maintain readability
     * and reduce code duplication.
     *
     * @param rs the result set to map
     * @return the playlist object
     * @throws SQLException if an error occurs while mapping the result set
     */
    private Playlist mapToPlaylist(ResultSet rs) throws SQLException {
        return new Playlist(
                rs.getInt("playlistID"),
                rs.getInt("userID"),
                rs.getString("name"),
                rs.getBoolean("isPublic")
        );
    }

    /**
     * Maps a result set to a list of playlists to maintain readability
     * and reduce code duplication.
     *
     * @param rs the result set to map
     * @return the list of playlists
     * @throws SQLException if an error occurs while mapping the result set
     */
    private List<Playlist> mapToPlaylists(ResultSet rs) throws SQLException {
        List<Playlist> playlists = new ArrayList<>();
        while (rs.next()) {
            playlists.add(mapToPlaylist(rs));
        }
        return playlists;
    }
    //Logging methods maybe in the future could have other functionalities
    /**
     * Logs error messages with the given message and exception.
     *
     * @param message The message to log
     * @param e The exception to log
     */
    private void logError(String message, Exception e) {
        log.error(message+": ", e);
    }
}
