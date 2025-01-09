package com.dragand.spring_tutorial.webpatternsca3.controller;

import com.dragand.spring_tutorial.webpatternsca3.business.Playlist;
import com.dragand.spring_tutorial.webpatternsca3.business.Song;
import com.dragand.spring_tutorial.webpatternsca3.business.User;
import com.dragand.spring_tutorial.webpatternsca3.persistence.PlaylistDAO;
import com.dragand.spring_tutorial.webpatternsca3.persistence.SongDAO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class SongController {

    private static final Logger log = LoggerFactory.getLogger(SongController.class);
    private final SongDAO songDao;
    private final PlaylistDAO playlistDao;

    // This method displays all songs
    @GetMapping("/songs")
    public String viewAllSongs(Model model, HttpSession session) {
        List<Song> songs = songDao.getAllSongs();
        model.addAttribute("songs", songs);
        getUsersPlaylists(session, model);
        return "songs"; // This maps to a Thymeleaf template named 'songs.html'
    }

    // Change this mapping to something unique, like "/search-songs"
    @GetMapping("/search-songs")
    public String searchSongs(
            @RequestParam(value = "query", required = false) String query,
            Model model
    ) {
        if (query != null && !query.trim().isEmpty()) {
            List<Song> songs = songDao.getAllSongsByTitle(query); // Fetch songs by title
            model.addAttribute("songs", songs);
            model.addAttribute("searchQuery", query); // Store the query in the model
        } else {
            List<Song> songs = songDao.getAllSongs(); // Fetch all songs if no query
            model.addAttribute("songs", songs);
        }
        return "songs"; // Return to the 'songs' page
    }



    public String getUsersPlaylists(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        List<Playlist> userPlaylists = playlistDao.getPlaylistByUserID(user.getUserID());
        log.info("Fetched {} playlists for user {}", userPlaylists.size(), user.getUserName());

        model.addAttribute("userPlaylists", userPlaylists);
        return "songs";

    }


}

