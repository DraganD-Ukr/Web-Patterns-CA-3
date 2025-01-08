package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Song;

import java.util.List;

/**
 * Song DAO interface used to define the methods that will be implemented in the SongDAOImpl class
 *
 * @author Aloysius Wilfred Pacheco D00253302
 */
public interface SongDAO {

    //Search Querry methods
    //get all songs

    /**
     * Gets all songs from the database
     *
     * @return A list of all {@link Song} objects
     */
    List<Song> getAllSongs();
    /**
     * Gets A song by its title from the database
     *
     * @param id The title of the song to find
     * @return A {@link Song} object if found, otherwise {@code null}
     */
    Song findSongById(int id);

    /**
     * Gets A song by its title from the database
     *
     * @param title The title of the song to find
     * @return A {@link Song} object if found, otherwise {@code null}
     */
    Song findSongByTitle(String title);

    /**
     * Gets all songs from the database that resemble the title
     *
     * @param title The title of the song to find
     * @return A list of all {@link Song} objects
     */
    List<Song> getAllSongsByTitle(String title);

    /**
     * Gets all songs from a specific artist using name
     *
     * @param artist The name of the artist to find songs from
     * @return A list of {@link Song} objects from the artist
     */
    List<Song> findAllSongsFromArtist(String artist);

    /**
     * Gets all songs from a specific artist using id
     *
     * @param ArtistID The id of the artist to find songs from
     * @return A list of {@link Song} objects from the artist
     */
    List<Song> findAllSongsFromArtistById(int ArtistID);

    /**
     * Gets all songs from a specific album using name
     *
     * @param albumName The name of the album to find songs from
     * @return A list of {@link Song} objects from the album
     */
    List<Song> findAllFromAlbumByName(String albumName);

    /**
     * Gets all songs from a specific album using id
     *
     * @param albumId The id of the album to find songs from
     * @return A list of {@link Song} objects from the album
     */
    List<Song> findAllFromAlbumById(int albumId);


    //Should be in the ratings table?

//     Song getTopRatedSong();
//
//     Song getMostPopularSong();
//

    //Database Data Entry/Edit query
    /**
     * Adds a song to the database
     *
     * @param song The song to add to the database
     * @return {@code true} if the song was added successfully, otherwise {@code false}
     */
    boolean addSong(Song song);

    /**
     * Removes a song from the database
     *
     * @param id The id of the song to remove
     * @return {@code true} if the song was removed successfully, otherwise {@code false}
     */
    boolean deleteSong(int id);

    // Extended Functionality

    //Top Rated songs for dropdowns with list limiter
    /**
     * Gets the top rated songs from the database
     *
     * @param limit The number of songs to get
     * @return A list of {@link Song} objects
     */
    List<Song> getLimitedSongsByName(String name, int limit);

    //get all songs with limit
    /**
     * Gets all songs from the database with a limit
     *
     * @param limit The number of songs to get
     * @return A list of {@link Song} objects
     */
    List<Song> getLimitedSongs(int limit);
}
