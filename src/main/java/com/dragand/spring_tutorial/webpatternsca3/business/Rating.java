package com.dragand.spring_tutorial.webpatternsca3.business;

import lombok.*;

/**
 * @Author: Jo Art Mahilaga
 */

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode


public class Rating {


    private int ratingID;
    @NonNull
    private int userID;
    @NonNull
    private int songID;
    @NonNull
    private int ratingValue;

    /**
     * Constructor for creating a rating object
     * @param userID The id of the user who made the rating
     * @param songID The id of the song being rated
     * @param ratingValue The value of the rating
     */

    public Rating(int userID, int songID, int ratingValue) {
        this.userID = userID;
        this.songID = songID;
        this.ratingValue = ratingValue;

    }
}
