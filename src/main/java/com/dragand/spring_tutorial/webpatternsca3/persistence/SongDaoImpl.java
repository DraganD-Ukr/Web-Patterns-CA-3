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

    @Override
    public Song findSongByTitle(String title) {
        return null;
    }

    @Override
    public List<Song> getAllSongsByTitle(String title) {
        return List.of();
    }

    @Override
    public List<Song> findAllSongsFromArtist(String artist) {
        return List.of();
    }

    @Override
    public List<Song> findAllSongsFromArtistById(int ArtistID) {
        return List.of();
    }

    @Override
    public List<Song> findAllFromAlbumByName(String albumName) {
        return List.of();
    }

    @Override
    public List<Song> findAllFromAlbumById(int albumId) {
        return List.of();
    }

    @Override
    public List<Song> getSongsInPlaylistByPlaylistName(String name) {
        return List.of();
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
