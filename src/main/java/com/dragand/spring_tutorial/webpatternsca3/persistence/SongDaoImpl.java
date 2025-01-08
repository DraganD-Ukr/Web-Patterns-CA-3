package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Song;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Song DAO implementation class used to interact with the database
 *
 * @author Aloysius Wilfred Pacheco D00253302
 */

@RequiredArgsConstructor
@Repository
@Log4j2
public class SongDaoImpl extends MySQLDao implements SongDAO{

    public SongDaoImpl(String databaseName){
        super(databaseName);
    }

    //Search Querries
    /**
     * Finds a song by its ID. This method performs a search in the Songs table based on the given ID.
     *
     * @param id The ID of the song to search for.
     * @return A Song object if a matching song is found, otherwise null.
     */
    @Override
    public Song findSongById(int id) {
        String sql = "SELECT songID, title, albumID, artistID, length, ratingCount, averageRating, ratingsSum " +
                "FROM Songs WHERE songID = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapSongFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            logError("An error occurred while fetching songs by ID", e);
        }
        return null;
    }

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
     * Fetches all songs from a specific artist by name.
     * This method makes use of the ArtistDao implementation to get the artist's ID.
     * Then it performs a query on the Songs table to fetch all songs by the artist.
     * This maintains the integrity of the DAO pattern.
     *
     * @param artist The name of the artist whose songs need to be fetched.
     * @return A list of Song objects belonging to the artist.
     */
    @Override
    public List<Song> findAllSongsFromArtist(String artist) {
        //uses method from artistDao implementation to maintain Dao integrity.
        int artistId= new ArtistDaoImpl().getArtistByName(artist).getArtistId();

        String sql = "SELECT songID, title, albumID, artistID, length, ratingCount, averageRating, ratingsSum " +
                "FROM Songs WHERE artistID = ?";

        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, artistId);
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
     * Retrieves all songs from an album using the album's name.
     * This method uses the AlbumDao implementation to get the album's ID.
     * Then it performs a query on the Songs table to fetch all songs from the album.
     *
     * @param albumName The name of the album to search.
     * @return A list of Song objects from the album.
     */
    @Override
    public List<Song> findAllFromAlbumByName(String albumName) {
        //uses method from albumDao implementation to maintain Dao integrity.
        int albumId= new AlbumDaoImpl().getAlbumByName(albumName).getAlbumId();

        String sql = "SELECT songID, title, albumID, artistID, length, ratingCount, averageRating, ratingsSum " +
                "FROM Songs WHERE albumID = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, albumId);
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
     * Adds a new song to the Songs table The provided Song object is mapped to the SQL
     * parameters, and the song is inserted into the database
     *
     * @param song The Song object to be added.
     * @return True if the song was added successfully, otherwise false.
     */
    @Override
    public boolean addSong(Song song) {
        String sql = "INSERT INTO Songs (title, albumID, artistID, length, ratingCount, averageRating, ratingsSum) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, song.getTitle());
            ps.setInt(2, song.getAlbumID());
            ps.setInt(3, song.getArtistID());
            ps.setTime(4, Time.valueOf(song.getLength()));
            ps.setInt(5, song.getRatingCount());
            ps.setDouble(6, song.getAverageRating());
            ps.setInt(7, song.getRatingsSum());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logError("An error occurred while adding the song", e);
        }
        return false;
    }

    /**
     * Deletes a song from the Songs table by its song ID.
     *
     * @param id The song's ID to be deleted.
     * @return True if the song was deleted successfully, otherwise false.
     */
    @Override
    public boolean deleteSong(int id) {
        String sql = "DELETE FROM Songs WHERE songID = ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logError("An error occurred while deleting the song", e);
        }
        return false;
    }

    //Extended functionality
    /**
     * Retrieves a limited number of songs based on their name. This can be used in places only limited results need to be shown.
     *
     * @param name The partial or full name of the song to search for.
     * @param limit The maximum number of songs to return.
     * @return A list of Song objects matching the name, limited to the specified number.
     */
    @Override
    public List<Song> getLimitedSongsByName(String name, int limit) {
        String sql = "SELECT songID, title, albumID, artistID, length, ratingCount, averageRating, ratingsSum " +
                "FROM Songs WHERE title LIKE ? LIMIT ?";
        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%"); // Case-insensitive partial match
            ps.setInt(2, limit); // Set the maximum number of results to return
            try (ResultSet rs = ps.executeQuery()) {
                return mapSongsFromResultSet(rs); // Reuse existing mapping method
            }
        } catch (SQLException e) {
            logError("An error occurred while fetching a limited number of songs by name", e);
        }
        return new ArrayList<>();
    }

//Refactor methods (these methods are used in case of repetitive code from previous application)
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
