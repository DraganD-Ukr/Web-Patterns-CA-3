package com.dragand.spring_tutorial.webpatternsca3.controller;

import com.dragand.spring_tutorial.webpatternsca3.business.Playlist;
import com.dragand.spring_tutorial.webpatternsca3.business.Rating;
import com.dragand.spring_tutorial.webpatternsca3.business.Song;
import com.dragand.spring_tutorial.webpatternsca3.persistence.PlaylistDAO;
import com.dragand.spring_tutorial.webpatternsca3.persistence.PlaylistSongsDaoImpl;
import com.dragand.spring_tutorial.webpatternsca3.business.User;
import com.dragand.spring_tutorial.webpatternsca3.persistence.RatingDAO;
import com.dragand.spring_tutorial.webpatternsca3.utils.AuthUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PlaylistController {

    private final PlaylistDAO playlistDao;
    private final PlaylistSongsDaoImpl playlistSongsDao;
    private final AuthUtils authUtils;
    private final RatingDAO ratingDao;

    @GetMapping("/create-playlist")
    public String createPlaylist(
            @RequestParam(value = "playlistName", required = false) String playlistName,
            @RequestParam(value = "isPublic", required = false) String isPublic,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        User user = (User) session.getAttribute("loggedInUser");
        String currentUser = user.getUserName();
        int currentUserId = user.getUserID();
        if (playlistName != null && !playlistName.trim().isEmpty()) {
            boolean publicPlaylist = isPublic != null && isPublic.equals("on");
            playlistDao.createPlaylist(Playlist.builder()
                    .name(playlistName)
                    .isPublic(publicPlaylist)
                    .userId(currentUserId)
                    .build());
        } else {
            redirectAttributes.addFlashAttribute("error", "Playlist name cannot be empty");
        }
        getUserRatings(session, redirectAttributes);
        return "redirect:/playlists";
    }

    @GetMapping("/playlists")
    public String viewPlaylists(
            @RequestParam(value = "userQuery", required = false) String userQuery,
            @RequestParam(value = "publicQuery", required = false) String publicQuery,
            @RequestParam(value = "playlistId", required = false) Integer playlistId,
            HttpSession session,
            Model model) {

        // Fetch user playlists
        User user = (User) session.getAttribute("loggedInUser");
        String currentUser = user.getUserName();
        List<Playlist> userPlaylists;
        if (userQuery != null && !userQuery.trim().isEmpty()) {
            userPlaylists = playlistDao.getPlaylistByUsernameAndName(currentUser, userQuery);
            model.addAttribute("userQuery", userQuery);
        } else {
            userPlaylists = playlistDao.getPlaylistByUsername(currentUser);
        }

        // Fetch public playlists
        List<Playlist> publicPlaylists;
        if (publicQuery != null && !publicQuery.trim().isEmpty()) {
            publicPlaylists = playlistDao.getAllPlaylistbyName(publicQuery, true);
            model.addAttribute("publicQuery", publicQuery);
        } else {
            publicPlaylists = playlistDao.getAllPublicPlaylists();
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
        session.setAttribute("currentPage", "playlists");
        // Add data to the model
        model.addAttribute("userPlaylists", userPlaylists);
        model.addAttribute("publicPlaylists", publicPlaylists);
        model.addAttribute("songs", songs); // Add the songs to the model
        model.addAttribute("selectedPlaylistId", selectedPlaylistId); // Add selectedPlaylistId to the model
        getUserRatings(session, model);
        return "playlists"; // Return the Thymeleaf template
    }
    @PostMapping("/rename-playlist")
    public String renamePlaylist(
            @RequestParam(value = "playlistId", required = false) Integer playlistId,
            @RequestParam(value = "newName", required = false) String newName,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        if (playlistId != null && newName != null && !newName.trim().isEmpty()) {
            playlistDao.renamePlaylist(playlistId, newName);
        } else {
            redirectAttributes.addFlashAttribute("error", "Playlist name cannot be empty");
        }
        getUserRatings(session, redirectAttributes);
        return "redirect:/playlists";
    }

    /**
     * Add a song to user's playlist
     * @param playlistId the playlist ID
     * @param songId the song ID
     * @param session the session
     * @param model the model
     * @return the redirect URL
     */
    @PostMapping("/addSongToPlaylist")
    public String addSongToPlaylist(
            @RequestParam(value = "playlistId", required = true) Integer playlistId,
            @RequestParam(value = "songId", required = true) Integer songId,
            HttpSession session,
            Model model
    ) {
        try {
            authUtils.authenticateUser(session, model);
        } catch (IOException e) {
            log.error("Failed to authenticate user", e);
        }

        boolean result = playlistSongsDao.addSongToPlaylist(playlistId, songId);

        if (result) {
            model.addAttribute("success", "Song added to playlist");
        } else {
            model.addAttribute("error", "Failed to add song to playlist");
        }
        getUserRatings(session, model);
        return "redirect:/playlists";
    }

    /**
     * Remove a song from us playlist
     * @param playlistId the playlist ID
     * @param songId the song ID
     * @param session the session
     * @param model the model
     * @return the redirect URL
     */
    @PostMapping("/removeSongFromPlaylist")
    public String removeSongFromPlaylist(
            @RequestParam(value = "playlistId", required = true) Integer playlistId,
            @RequestParam(value = "songId", required = true) Integer songId,
            HttpSession session,
            Model model
    ) {
        try {
            authUtils.authenticateUser(session, model);
        } catch (IOException e) {
            log.error("Failed to authenticate user", e);
        }

        boolean result = playlistSongsDao.removeSongFromPlaylist(playlistId, songId);

        if (result) {
            model.addAttribute("success", "Song removed from playlist");
        } else {
            model.addAttribute("error", "Failed to remove song from playlist");
        }
        getUserRatings(session, model);
        return "redirect:/playlists";
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
        return "playlists";
    }
}
