package com.dragand.spring_tutorial.webpatternsca3.business;

import lombok.*;

import java.time.LocalTime;

/**
 * Song DTO used to carry song data arround
 *
 * @author Aloysius Wilfred Pacheco D00253302
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Song {
    @NonNull
    private int songID;
    @NonNull
    private String title;
    @NonNull
    private int albumID;
    @NonNull
    private int artistID;
    @NonNull
    private LocalTime length;
    private int ratingCount;
    private double averageRating;
    private int ratingsSum;

    /**
     * Constructor for creating a song object
     *
     * @param title    The title of the song
     * @param albumID  The id of the album the song is in
     * @param artistID The id of the artist who created the song
     * @param length   The length of the song in LocalTime format
     */
    public Song(String title, int albumID, int artistID, LocalTime length) {
        this.title = title;
        this.albumID = albumID;
        this.artistID = artistID;
        this.length = length;
    }

}
