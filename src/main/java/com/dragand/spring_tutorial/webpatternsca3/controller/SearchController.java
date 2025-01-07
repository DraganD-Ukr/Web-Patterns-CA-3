package com.dragand.spring_tutorial.webpatternsca3.controller;


import com.dragand.spring_tutorial.webpatternsca3.business.dto.SearchResponse;
import com.dragand.spring_tutorial.webpatternsca3.persistence.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class SearchController {


    private static final Logger log = LoggerFactory.getLogger(SearchController.class);
    private final AlbumDAO albumDao;
    private final ArtistDAO artistDao;
    private final SongDAO songDao;

    @GetMapping("/search")
    public String search(@RequestParam(value = "search", required = true) String query, Model model) {


        if (query != null && !query.trim().isEmpty()) {
            // Perform search in the database and get results
            SearchResponse searchResponse = new SearchResponse(
                    songDao.getAllSongsByTitle(query),
                    artistDao.getAllArtistsWhereNameLike(query),
                    albumDao.getAllAlbumsWhereNameLike(query)
            );

            model.addAttribute("searchResponse", searchResponse);
            model.addAttribute("searchQuery", query); // Store the query in the model to display on the results page
            log.info("Search results for query '{}': {}", query, searchResponse);
        } else {
            model.addAttribute("searchResponse", new SearchResponse(null, null, null)); // Empty response if no query is provided
            model.addAttribute("searchQuery", "");
        }

        return "search"; // Return to the search.html page
    }

}






