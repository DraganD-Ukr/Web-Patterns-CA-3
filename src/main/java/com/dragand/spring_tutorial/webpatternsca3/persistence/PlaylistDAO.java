package com.dragand.spring_tutorial.webpatternsca3.persistence;

import java.util.List;
import com.dragand.spring_tutorial.webpatternsca3.business.*;

public interface PlaylistDAO {
//Search query methods
    //get all playlist belonging to the user that have name like the query
    /**
     * Retrieve all playlists in the database that belong to the user and have a name like the query.
     *
     * @param username the username of the user who created the playlist
     * @param query the query to search for in the playlist name
     * @return a list of all playlists in the database that belong to the user and have a name like the query
     */
    List<Playlist> getPlaylistByUsernameAndName(String username, String query);
    /**
     * Retrieve all playlists in the database that are public.
     *
     * @return a list of all playlists in the database
     */
    List<Playlist> getAllPublicPlaylists();
    /**
     * Retrieve all playlists in the database that are public.
     *
     * @return a list of all playlists in the database
     */
    List<Playlist> getAllPublicPlaylists();
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

    //retrieve all playlists that have a name and if they are public or not
    /**
     * Retrieve all playlists in the database that have a name like the query and are public or not.
     *
     * @param name the name of the playlist to search for
     * @param isPublic the boolean value to determine if the playlist is public or not
     * @return a list of all playlists in the database that have a name like the query and are public or not
     */
    List<Playlist> getAllPlaylistbyName(String name, boolean isPublic);


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
     * Renames a playlist
     *
     * @param playlistId the id of the playlist to be renamed
     * @param newName the new name of the playlist
     * @return {@code true} if the playlist was renamed successfully, if not {@code false}
     */
    boolean renamePlaylist(int playlistId, String newName);

//Extended Functionality
}
