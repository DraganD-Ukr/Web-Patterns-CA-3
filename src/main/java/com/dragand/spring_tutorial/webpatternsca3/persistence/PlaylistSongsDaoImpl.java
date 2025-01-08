package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Song;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class PlaylistSongsDaoImpl extends MySQLDao implements PlaylistSongsDAO{

    //Constructor
    /**
     * Constructor for the PlaylistSongsDaoImpl class
     * Calls the super constructor to initialize the connection to the database
     */
    public PlaylistSongsDaoImpl() {
        super();
    }

    //Search methods
    /**
     * Retrieve a list of songs in a playlist by the playlist id
     * This method accomplishes this by retrieving the songs from the playlistsongs table using the prepared statement
     *
     * @param playlistId the id of the playlist to retrieve the songs from
     * @return a list of songs in the playlist
     */
    @Override
    public List<Song> getSongsInPlaylistByPlaylistId(int playlistId) {
        List<Song> songs = new ArrayList<>();

        String query = "SELECT * FROM playlistsongs WHERE playlistID = ?";

        try (Connection con = super.getConnection();
             var ps = con.prepareStatement(query)) {
            ps.setInt(1, playlistId);
            try (ResultSet rs = ps.executeQuery()) {
                songs = mapSongsFromResultSet(rs);
            }
        } catch (SQLException e) {
            logError("An error occurred while retrieving the songs in the playlist with id: " + playlistId, e);
        }

        return songs;
    }

    /**
     * Retrieve a list of songs in a playlist by the playlist name
     * This method accomplishes this by first getting the playlist id by name from the playlistDaoImpl
     * this is then used to retrieve the songs from the playlistsongs table using the prepared statement
     *
     * @param playlistName the name of the playlist to retrieve the songs from
     * @return a list of songs in the playlist
     */
    @Override
    public List<Song> getSongsInPlaylistByPlaylistName(String playlistName) {
        List<Song> songs = new ArrayList<>();

        int playlistId = new PlaylistDaoImpl().getPlaylistByName(playlistName).getPlaylistId();

        return getSongsInPlaylistByPlaylistId(playlistId);
    }

    //Database Data Entry/Edit methods

    /**
     * Adds a song to a playlist using the prepared statement
     * USES INSERT sql query to add the song to the playlist
     *
     * @param playlistId the id of the playlist to add the song to
     * @param songId     the id of the song to add to the playlist
     * @return {@code true} if the song was added to the playlist successfully, if not {@code false}
     */

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        String query = "INSERT INTO playlistsongs (playlistID, songID) VALUES (?, ?)";

        try (Connection con = super.getConnection();
             var ps = con.prepareStatement(query)) {
            ps.setInt(1, playlistId);
            ps.setInt(2, songId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logError("An error occurred while adding the song with id: " + songId + " to the playlist with id: " + playlistId, e);
            return false;
        }
    }

    /**
     * Removes a song from a playlist using the prepared statement
     * USES DELETE sql query to remove the song from the playlist
     *
     * @param playlistId the id of the playlist to remove the song from
     * @param songId     the id of the song to remove from the playlist
     * @return {@code true} if the song was removed from the playlist successfully, if not {@code false}
     */
    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        String query = "DELETE FROM playlistsongs WHERE playlistID = ? AND songID = ?";

        try (Connection con = super.getConnection();
             var ps = con.prepareStatement(query)) {
            ps.setInt(1, playlistId);
            ps.setInt(2, songId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logError("An error occurred while removing the song with id: " + songId + " from the playlist with id: " + playlistId, e);
            return false;
        }
    }



    private Song mapSongFromResultSet(ResultSet rs) throws SQLException {
        return new Song(
                rs.getInt("songID"),
                rs.getString("title"),
                rs.getInt("albumID"),
                rs.getInt("artistID"),
                rs.getTime("length").toLocalTime(),
                rs.getInt("ratingCount"),
                rs.getDouble("averageRating"),
                rs.getInt("ratingsSum")
        );
    }

    /**
     * Maps all rows of the ResultSet to a list of Song objects.
     *
     * @param rs The ResultSet containing multiple rows of song data.
     * @return A list of Song objects.
     * @throws SQLException If an SQL error occurs while mapping the data.
     */
    private List<Song> mapSongsFromResultSet(ResultSet rs) throws SQLException {
        List<Song> songList = new ArrayList<>();
        while (rs.next()) {
            songList.add(mapSongFromResultSet(rs));
        }
        return songList;
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
