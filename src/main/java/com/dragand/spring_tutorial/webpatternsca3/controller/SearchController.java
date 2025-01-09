package com.dragand.spring_tutorial.webpatternsca3.controller;


import com.dragand.spring_tutorial.webpatternsca3.business.Playlist;
import com.dragand.spring_tutorial.webpatternsca3.business.Song;
import com.dragand.spring_tutorial.webpatternsca3.business.dto.SearchResponse;
import com.dragand.spring_tutorial.webpatternsca3.persistence.*;
import com.dragand.spring_tutorial.webpatternsca3.utils.AuthUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class SearchController {


    private static final Logger log = LoggerFactory.getLogger(SearchController.class);
    private final AlbumDAO albumDao;
    private final ArtistDAO artistDao;
    private final SongDAO songDao;
    private final PlaylistDAO playlistDao;
    private final PlaylistSongsDaoImpl playlistSongsDao;
    private final AuthUtils authUtils;

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "search", required = false) String query,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {

            try {
                authUtils.authenticateUser(session, model);
            } catch (IOException e) {
                log.error("Error authenticating user", e);
            }
            String subscriptionCheckResult = authUtils.isSubscriptionActive(session, redirectAttributes);
            if (subscriptionCheckResult != null) {
                return subscriptionCheckResult;
            }




        if (query != null && !query.trim().isEmpty()) {
            // Perform search in the database and get results
            SearchResponse searchResponse = new SearchResponse(
                    songDao.getAllSongsByTitle(query),
                    artistDao.getAllArtistsWhereNameLike(query),
                    albumDao.getAllAlbumsWhereNameLike(query),
                    playlistDao.getAllPlaylistbyName(query, true)

            );

            model.addAttribute("searchResponse", searchResponse);
            model.addAttribute("searchQuery", query); // Store the query in the model to display on the results page
            log.info("Search results for query '{}': {}", query, searchResponse);
        } else {
            model.addAttribute("searchResponse", new SearchResponse(null, null, null,null)); // Empty response if no query is provided
        }

        return "search"; // Return to the search.html page
    }

    @GetMapping("/playlistsSearchView")
    public String viewPlaylistsSearchView(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "playlistId", required = false) Integer playlistId,
            HttpSession session,
            Model model) {

        // Fetch public playlists
        List<Playlist> publicPlaylists;
        if (query != null && !query.trim().isEmpty()) {
            publicPlaylists = playlistDao.getAllPlaylistbyName(query, true);
        } else{
            publicPlaylists = new ArrayList<>();
        }

        // Get the currently selected playlist ID from the session
        Integer selectedPlaylistId = (Integer) session.getAttribute("selectedPlaylistId");

        // Handle "View Songs" and "Close" toggle
        List<Song> songs = null;
        if (playlistId != null) {
            if (selectedPlaylistId != null && selectedPlaylistId.equals(playlistId)) {
                // Close the playlist view
                session.removeAttribute("selectedPlaylistId");
                selectedPlaylistId = null;
            } else {
                // View songs in the selected playlist
                songs = playlistSongsDao.getSongsInPlaylistByPlaylistId(playlistId);
                session.setAttribute("selectedPlaylistId", playlistId);
                selectedPlaylistId = playlistId;
            }
        }

        // Add data to the model
        model.addAttribute("searchResponse", new SearchResponse(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), publicPlaylists));
        model.addAttribute("songs", songs); // Add the songs to the model
        model.addAttribute("selectedPlaylistId", selectedPlaylistId); // Add selectedPlaylistId to the model

        return "search";
    }
}






