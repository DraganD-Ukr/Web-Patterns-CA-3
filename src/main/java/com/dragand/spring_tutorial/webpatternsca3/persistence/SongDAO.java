package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Song;

import java.util.List;

/**
 * Song DAO interface used to define the methods that will be implemented in the SongDAOImpl class
 *
 * @author Aloysius Wilfred Pacheco D00253302
 */
public interface SongDAO {
    /**
     * Gets A song by its title from the database
     * @param title The title of the song to find
     * @return The {@link Song} object if found, otherwise {@code null}
     */

    //Search Querry methods

    public Song findSongByTitle(String title);

    public List<Song> getAllSongsByTitle(String title);

    public List<Song> findAllSongsFromArtist(String artist);

    public List<Song> findAllSongsFromArtistById(int ArtistID);

    public List<Song> findAllFromAlbumByName(String albumName);

    public List<Song> findAllFromAlbumById(int albumId);

    public List<Song> getSongsInPlaylistByPlaylistName(String name);

    //Should be in the ratings table?

//    public Song getTopRatedSong();
//
//    public Song getMostPopularSong();
//

    //Database Data Entry/Edit query

    public boolean addSong(Song song);

    public boolean deleteSong(int id);

}
