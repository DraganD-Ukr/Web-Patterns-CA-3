package com.dragand.spring_tutorial.webpatternsca3.controller;


import com.dragand.spring_tutorial.webpatternsca3.business.Playlist;
import com.dragand.spring_tutorial.webpatternsca3.business.Rating;
import com.dragand.spring_tutorial.webpatternsca3.business.Song;
import com.dragand.spring_tutorial.webpatternsca3.business.User;
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
import java.util.Map;
import java.util.stream.Collectors;

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
    private final RatingDAO ratingDao;

    /**
     * Search for songs, artists, albums, and playlists
     * @param query the search query
     * @param model the model to add the search results to
     * @param session the session to check if the user is logged in
     * @param redirectAttributes the redirect attributes to add errors to
     * @return the search page with the search results
     */
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
            model.addAttribute("searchResponse", new SearchResponse(null, null, null, null)); // Empty response if no query is provided
        }
        getUserRatings(session, model);
        session.setAttribute("currentPage", "search");
        return "search"; // Return to the search.html page
    }

    private String getUserRatings(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            List<Rating> userRatings = ratingDao.getRatingsByUserID(user.getUserID());

            // Create a map of Song ID to Rating Value for the current user
            Map<Integer, Integer> userRatingsMap = userRatings.stream()
                    .collect(Collectors.toMap(Rating::getSongID, Rating::getRatingValue));

            // Add a map of Song ID to Rating Value for the current user
            model.addAttribute("userRatings", userRatingsMap);

            log.info("Fetched {} ratings for user {}", userRatings.size(), user.getUserName());
        }
        return "songs";
    }
}