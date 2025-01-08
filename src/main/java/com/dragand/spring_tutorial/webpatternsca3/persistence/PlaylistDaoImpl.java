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


    /**
     * Default constructor (uses default database set)
     */
    public PlaylistDaoImpl(){
        super();
    }
    //Search query methods

    /**
     * Retrieve all playlists in the database that are public.
     *
     * @return a list of all playlists in the database
     */
    @Override
    public List<Playlist> getAllPublicPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        String query = "SELECT * FROM playlists WHERE isPublic = 1";

        try(Connection con = super.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            playlists = mapToPlaylists(rs);
        } catch (SQLException e) {
            logError("SQLException occurred while retrieving all public playlists", e);
        }

        return playlists;
    }

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
     * Creates a playlist in the database using a prepared statement
     * The method returns the id of the playlist created using the RETURN_GENERATED_KEYS option
     * if no playlist is created, a SQLException is thrown and -1 is returned
     *
     * @param playlist the playlist to be created
     * @return the id of the playlist created, if no playlist is created -1 is returned
     */
    @Override
    public int createPlaylist(Playlist playlist) {
        int playlistId = -1;
        String query = "INSERT INTO playlists (userID, name, isPublic) VALUES (?, ?, ?)";

        try(Connection con = super.getConnection();
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, playlist.getUserId());
            ps.setString(2, playlist.getName());
            ps.setBoolean(3, playlist.isPublic());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating playlist failed, no rows affected.");
            }

            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    playlistId = rs.getInt(1);
                } else {
                    throw new SQLException("Creating playlist failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logError("SQLException occurred while creating playlist", e);
        }

        return playlistId;
    }

    /**
     * Deletes a playlist from the database by its id using a prepared statement
     * The method returns {@code true} if the playlist was deleted successfully, if not {@code false}
     *
     * @param id the id of the playlist to be deleted
     * @return {@code true} if the playlist was deleted successfully, if not {@code false}
     */
    @Override
    public boolean deletePlaylist(int id) {
        String query = "DELETE FROM playlists WHERE playlistID = ?";

        try(Connection con = super.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logError("SQLException occurred while deleting playlist", e);
        }

        return false;
    }


    /**
     * Renames a playlist using a prepared statement AND update sql query
     * The method returns {@code true} if the playlist was renamed successfully, if not {@code false}
     *
     * @param playlistId the id of the playlist to be renamed
     * @param newName    the new name of the playlist
     * @return {@code true} if the playlist was renamed successfully, if not {@code false}
     */
    @Override
    public boolean renamePlaylist(int playlistId, String newName) {
        String query = "UPDATE playlists SET name = ? WHERE playlistID = ?";

        try(Connection con = super.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, newName);
            ps.setInt(2, playlistId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logError("SQLException occurred while renaming playlist", e);
        }

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
