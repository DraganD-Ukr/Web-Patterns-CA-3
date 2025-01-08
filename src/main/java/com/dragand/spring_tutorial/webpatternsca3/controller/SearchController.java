package com.dragand.spring_tutorial.webpatternsca3.controller;


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

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class SearchController {


    private static final Logger log = LoggerFactory.getLogger(SearchController.class);
    private final AlbumDAO albumDao;
    private final ArtistDAO artistDao;
    private final SongDAO songDao;
    private final AuthUtils authUtils;

    @GetMapping("/search")
    public String search(@RequestParam(value = "search", required = false) String query, Model model, HttpSession session)  {

        try {
            authUtils.authenticateUser(session, model);
        } catch (IOException e) {
            log.error("Error authenticating user", e);
        }


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
        }

        return "search"; // Return to the search.html page
    }

}






