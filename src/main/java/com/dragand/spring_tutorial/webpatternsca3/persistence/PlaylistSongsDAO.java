package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.*;

import java.util.List;

public interface PlaylistSongsDAO {
    //Search query methods
    /**
     * Retrieve a list of songs in a playlist by the playlist id
     *
     * @param playlistId the id of the playlist to retrieve the songs from
     * @return a list of songs in the playlist
     */
    List<Song> getSongsInPlaylistByPlaylistId(int playlistId);

    /**
     * Retrieve a list of songs in a playlist by the playlist name
     *
     * @param playlistName the name of the playlist to retrieve the songs from
     * @return a list of songs in the playlist
     */
    List<Song> getSongsInPlaylistByPlaylistName(String playlistName);

    //Database Data Entry/Edit query
    /**
     * Adds a song to a playlist
     *
     * @param playlistId the id of the playlist to add the song to
     * @param songId the id of the song to add to the playlist
     * @return {@code true} if the song was added to the playlist successfully, if not {@code false}
     */
    boolean addSongToPlaylist(int playlistId, int songId);

    /**
     * Removes a song from a playlist
     *
     * @param playlistId the id of the playlist to remove the song from
     * @param songId the id of the song to remove from the playlist
     * @return {@code true} if the song was removed from the playlist successfully, if not {@code false}
     */
    boolean removeSongFromPlaylist(int playlistId, int songId);

    //Extended Functionality
}
