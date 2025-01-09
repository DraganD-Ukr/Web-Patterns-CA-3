package com.dragand.spring_tutorial.webpatternsca3.controller;

import com.dragand.spring_tutorial.webpatternsca3.business.Playlist;
import com.dragand.spring_tutorial.webpatternsca3.business.Rating;
import com.dragand.spring_tutorial.webpatternsca3.business.Song;
import com.dragand.spring_tutorial.webpatternsca3.business.User;
import com.dragand.spring_tutorial.webpatternsca3.persistence.PlaylistDAO;
import com.dragand.spring_tutorial.webpatternsca3.persistence.RatingDAO;
import com.dragand.spring_tutorial.webpatternsca3.persistence.SongDAO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class SongController {

    private static final Logger log = LoggerFactory.getLogger(SongController.class);
    private final SongDAO songDao;
    private final PlaylistDAO playlistDao;
    private final RatingDAO ratingDao;

    // This method displays all songs
    @GetMapping("/songs")
    public String viewAllSongs(Model model, HttpSession session) {

        List<Song> songs = songDao.getAllSongs();
        model.addAttribute("songs", songs);
        getUsersPlaylists(session, model);
        getUserRatings(session, model);
        session.setAttribute("currentPage", "songs");
        return "songs"; // This maps to a Thymeleaf template named 'songs.html'
    }

    // Change this mapping to something unique, like "/search-songs"
    @GetMapping("/search-songs")
    public String searchSongs(
            @RequestParam(value = "query", required = false) String query,
            Model model,
            HttpSession session
    ) {
        if (query != null && !query.trim().isEmpty()) {
            List<Song> songs = songDao.getAllSongsByTitle(query); // Fetch songs by title

            model.addAttribute("songs", songs);
            model.addAttribute("searchQuery", query); // Store the query in the model
            getUsersPlaylists(session, model);
            getUserRatings(session, model);
        } else {
            List<Song> songs = songDao.getAllSongs(); // Fetch all songs if no query
            model.addAttribute("songs", songs);
            getUsersPlaylists(session, model);
            getUserRatings(session, model);
        }
        return "songs"; // Return to the 'songs' page
    }

    @PostMapping("/rateSong")
    public String rateSong(
            @RequestParam("songId") int songId,
            @RequestParam("ratingValue") int ratingValue,
            HttpSession session,
            Model model
    ) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            log.warn("Unauthenticated user attempted to rate a song.");
            return "redirect:/login";
        }

        boolean success = ratingDao.addOrUpdateRating("song", songId, user.getUserID(), ratingValue);
        if (success) {
            log.info("User {} rated song {} with value {}", user.getUserName(), songId, ratingValue);
        } else {
            log.error("Failed to submit rating for song {} by user {}", songId, user.getUserName());
        }
        if(session.getAttribute("currentPage").equals("songs")) {
            return "redirect:/songs";
        } else if(session.getAttribute("currentPage").equals("search")) {
            return "redirect:/search";
        } else if(session.getAttribute("currentPage").equals("playlists")) {
            return "redirect:/playlists";
        }else{
            return "redirect:/songs";
        }
    }


    /**
     * Get the playlists for the logged in user. Used to display the playlists on the songs page to be eble to add songs to them
     * @param session the session to get the user from
     * @param model the model to add the playlists to
     * @return the songs page
     */
    public String getUsersPlaylists(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        List<Playlist> userPlaylists = playlistDao.getPlaylistByUserID(user.getUserID());
        log.info("Fetched {} playlists for user {}", userPlaylists.size(), user.getUserName());

        model.addAttribute("userPlaylists", userPlaylists);
        return "songs";

    }

    /**
     * Get the ratings for the logged in user. Used to display the ratings on the songs page
     * @param session the session to get the user from
     * @param model the model to add the ratings to
     * @return the songs page
     */

    // Get user ratings
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

