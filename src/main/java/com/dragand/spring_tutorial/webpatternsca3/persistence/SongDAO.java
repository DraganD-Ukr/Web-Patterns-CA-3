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

     Song findSongByTitle(String title);

     List<Song> getAllSongsByTitle(String title);

     List<Song> findAllSongsFromArtist(String artist);

     List<Song> findAllSongsFromArtistById(int ArtistID);

     List<Song> findAllFromAlbumByName(String albumName);

     List<Song> findAllFromAlbumById(int albumId);

     List<Song> getSongsInPlaylistByPlaylistName(String name);

    //Should be in the ratings table?

//     Song getTopRatedSong();
//
//     Song getMostPopularSong();
//

    //Database Data Entry/Edit query

     boolean addSong(Song song);

     boolean deleteSong(int id);

}
