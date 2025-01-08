package com.dragand.spring_tutorial.webpatternsca3.controller;

import com.dragand.spring_tutorial.webpatternsca3.business.Song;
import com.dragand.spring_tutorial.webpatternsca3.persistence.SongDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class SongController {

    private final SongDAO songDao;

    // This method displays all songs
    @GetMapping("/songs")
    public String viewAllSongs(Model model) {
        List<Song> songs = songDao.getAllSongs();
        model.addAttribute("songs", songs);
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
}
