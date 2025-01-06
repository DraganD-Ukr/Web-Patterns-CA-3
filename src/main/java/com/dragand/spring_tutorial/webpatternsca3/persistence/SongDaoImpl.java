package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Song;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Song DAO implementation class used to interact with the database
 *
 * @author Aloysius Wilfred Pacheco D00253302
 */
public class SongDaoImpl extends MySQLDao implements SongDAO{

    public SongDaoImpl(String databaseName){
        super(databaseName);
    }

    //Search Querries

    /**
     * Finds a song by its title. This method performs a search in the
     * Songs table based on the given title.
     *
     * @param title The exact title of the song to search for.
     * @return A Song object if a matching song is found, otherwise null.
     */
    @Override
    public Song findSongByTitle(String title) {
        String sql = "SELECT songID, title, albumID, artistID, length, ratingCount, averageRating, ratingsSum " +
                "FROM Songs WHERE title = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapSongFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            logError("An error occurred while fetching songs by title", e);
        }
        return null;
    }

    /**
     * Retrieves all songs that match the given title The search is case-insensitive.
     * Uses a LIKE clause in SQL to fetch all songs with similar titles.
     *
     * @param title The title to search   (part of the title).
     * @return A list of Song objects that match the title.
     */
    @Override
    public List<Song> getAllSongsByTitle(String title) {
        String sql = "SELECT songID, title, albumID, artistID, length, ratingCount, averageRating, ratingsSum " +
                "FROM Songs WHERE title LIKE ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            try (ResultSet rs = ps.executeQuery()) {
                return mapSongsFromResultSet(rs);
            }
        } catch (SQLException e) {
            logError("An error occurred while fetching songs by title", e);
        }
        return new ArrayList<>();
    }

    /**
     * Fetches all songs from a specific artist by name It joins the Songs table with
     * the Artists table to get the songs associated with the artist name.
     *
     * @param artist The name of the artist whose songs need to be fetched.
     * @return A list of Song objects belonging to the artist.
     */
    @Override
    public List<Song> findAllSongsFromArtist(String artist) {
        String sql = "SELECT s.songID, s.title, s.albumID, s.artistID, s.length, s.ratingCount, s.averageRating, s.ratingsSum " +
                "FROM Songs s " +
                "JOIN Artists a ON s.artistID = a.artistID " +
                "WHERE a.name = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, artist);
            try (ResultSet rs = ps.executeQuery()) {
                return mapSongsFromResultSet(rs);
            }
        } catch (SQLException e) {
            logError("An error occurred while fetching songs from the artist", e);
        }
        return new ArrayList<>();
    }

    /**
     * Finds all songs from an artist by their artist ID This method performs a query
     * on the Songs table filtered by the given artist's ID.
     *
     * @param ArtistID The artist's ID to filter songs by.
     * @return A list of Song objects from the specified artist.
     */
    @Override
    public List<Song> findAllSongsFromArtistById(int ArtistID) {
        String sql = "SELECT songID, title, albumID, artistID, length, ratingCount, averageRating, ratingsSum " +
                "FROM Songs WHERE artistID = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ArtistID);
            try (ResultSet rs = ps.executeQuery()) {
                return mapSongsFromResultSet(rs);
            }
        } catch (SQLException e) {
            logError("An error occurred while fetching songs by artist ID", e);
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves all songs from an album using the album's name It joins the Songs
     * table with the Albums table to find the songs associated with the album name
     *
     * @param albumName The name of the album to search  .
     * @return A list of Song objects from the album.
     */
    @Override
    public List<Song> findAllFromAlbumByName(String albumName) {
        String sql = "SELECT s.songID, s.title, s.albumID, s.artistID, s.length, s.ratingCount, s.averageRating, s.ratingsSum " +
                "FROM Songs s " +
                "JOIN Albums a ON s.albumID = a.albumID " +
                "WHERE a.title = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, albumName);
            try (ResultSet rs = ps.executeQuery()) {
                return mapSongsFromResultSet(rs);
            }
        } catch (SQLException e) {
            logError("An error occurred while fetching songs from the album", e);
        }
        return new ArrayList<>();
    }

    /**
     * Fetches all songs from an album using the album's ID. This is useful
     * filtering songs by album ID from the Songs table.
     *
     * @param albumId The album's ID to filter songs by.
     * @return A list of Song objects in the specified album.
     */
    @Override
    public List<Song> findAllFromAlbumById(int albumId) {
        String sql = "SELECT songID, title, albumID, artistID, length, ratingCount, averageRating, ratingsSum " +
                "FROM Songs WHERE albumID = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, albumId);
            try (ResultSet rs = ps.executeQuery()) {
                return mapSongsFromResultSet(rs);
            }
        } catch (SQLException e) {
            logError("An error occurred while fetching songs from the album ID", e);
        }
        return new ArrayList<>();
    }


    /**
     * Fetches all songs that belong to a playlist identified by its name.
     * Uses a JOIN between the Songs, PlaylistSongs, and Playlists tables.
     *
     * @param name The playlist's name to fetch songs.
     * @return A list of Song objects that are in the playlist.
     */
    @Override
    public List<Song> getSongsInPlaylistByPlaylistName(String name) {
        String sql = "SELECT s.songID, s.title, s.albumID, s.artistID, s.length, s.ratingCount, s.averageRating, s.ratingsSum " +
                "FROM Songs s " +
                "JOIN PlaylistSongs ps ON s.songID = ps.songID " +
                "JOIN Playlists p ON ps.playlistID = p.playlistID " +
                "WHERE p.name = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return mapSongsFromResultSet(rs);
            }
        } catch (SQLException e) {
            logError("An error occurred while fetching songs from the playlist", e);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean addSong(Song song) {
        return false;
    }

    @Override
    public boolean deleteSong(int id) {
        return false;
    }

//Refactory methods (these methods are used in case of repetitive code from previous application)
    /**
     * Maps a single row of the ResultSet to a Song object.
     *
     * @param rs The ResultSet containing the song data.
     * @return A Song object with the data from the ResultSet.
     * @throws SQLException If an SQL error occurs while mapping the data.
     */
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

    /**
     * Logs error messages along with the exception details to the console
     *
     * @param message The message to log
     * @param e The exception to log
     */
    private void logError(String message, Exception e) {
        System.out.println(LocalDateTime.now() + ": " + message);
        e.printStackTrace();
    }


}
