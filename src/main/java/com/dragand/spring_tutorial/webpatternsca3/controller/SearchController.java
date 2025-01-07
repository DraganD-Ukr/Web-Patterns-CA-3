package com.dragand.spring_tutorial.webpatternsca3.controller;


import com.dragand.spring_tutorial.webpatternsca3.business.dto.SearchResponse;
import com.dragand.spring_tutorial.webpatternsca3.persistence.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class SearchController {


    private static final Logger log = LoggerFactory.getLogger(SearchController.class);
    private final AlbumDAO albumDao;
    private final ArtistDAO artistDao;
    private final SongDAO songDao;

    @GetMapping("/search")
    public String search(Model model) {

        String query = model.getAttribute("search").toString();

        SearchResponse searchResponse = new SearchResponse(
//                TODO: Use getAllSongsByTitleLike instead of getAllSongsByTitle
                songDao.getAllSongsByTitle(query),
                artistDao.getAllArtistsWhereNameLike(query),
                albumDao.getAllAlbumsWhereArtistNameLike(query)
        );
        model.addAttribute("searchResponse", searchResponse);

        log.info("Search results: {}", searchResponse);

        return "search";

    }





}
