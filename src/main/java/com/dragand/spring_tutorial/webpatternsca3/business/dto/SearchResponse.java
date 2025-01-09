package com.dragand.spring_tutorial.webpatternsca3.business.dto;

import com.dragand.spring_tutorial.webpatternsca3.business.Album;
import com.dragand.spring_tutorial.webpatternsca3.business.Artist;
import com.dragand.spring_tutorial.webpatternsca3.business.Playlist;
import com.dragand.spring_tutorial.webpatternsca3.business.Song;

import java.util.List;

public record SearchResponse(
        List<Song> songs,
        List<Artist> artists,
        List<Album> albums,
        List<Playlist> playlists
) {
}
