package com.dragand.spring_tutorial.webpatternsca3.persistence;

import java.util.List;
import com.dragand.spring_tutorial.webpatternsca3.business.*;

public interface PlaylistDAO {
//Search query methods
    /**
     * Retrieve a playlist by the username of the user who created it.
     *
     * @param username the username of the user who created the playlist.
     * @return a list of all playlists created by the user
     */
    List<Playlist> getPlaylistByUsername(String username);

    /**
     * Retrieve a playlist by its id.
     *
     * @param id the id of the playlist to retrieve
     * @return the playlist with the given id.
     */
    Playlist getPlaylistById(int id);

    /**
     * Retrieve a playlist by its name
     *
     * @param name the name of the playlist to retrieve.
     * @return the playlist with the given name.
     */
    Playlist getPlaylistByName(String name);

//Database Data Entry/Edit query
    /**
     * Creates a playlist in the database
     *
     * @param playlist the playlist to be created
     * @return the id of the playlist created
     */
    int createPlaylist(Playlist playlist);

    /**
     * Deletes a playlist from the database by its id
     *
     * @param id the id of the playlist to be deleted
     * @return {@code true} if the playlist was deleted successfully, if not {@code false}
     */
    boolean deletePlaylist(int id);

    /**
     * Adds a song to a playlist
     *
     * @param playlistId the id of the playlist to add the song to
     * @param songId the id of the song to be added to the playlist
     * @return {@code true} if the song was added successfully, if not {@code false}
     */
    boolean addSongToPlaylist(int playlistId, int songId);

    /**
     * Removes a song from a playlist
     *
     * @param playlistId the id of the playlist to remove the song from
     * @param songId the id of the song to be removed from the playlist
     * @return {@code true} if the song was removed successfully, if not {@code false}
     */
    boolean removeSongFromPlaylist(int playlistId, int songId);

    /**
     * Renames a playlist
     *
     * @param playlistId the id of the playlist to be renamed
     * @param newName the new name of the playlist
     * @return {@code true} if the playlist was renamed successfully, if not {@code false}
     */
    boolean renamePlaylist(int playlistId, String newName);

//Extended Functionality
}
