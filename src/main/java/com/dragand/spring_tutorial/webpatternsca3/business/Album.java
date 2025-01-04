package com.dragand.spring_tutorial.webpatternsca3.business;

import lombok.*;

import java.util.Date;

/**
 * @author: Dmytro Drahan
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Album {

    @EqualsAndHashCode.Include
    private Integer albumId;
    private String title;
    private Integer artistId;
    private Date releaseDate;

    @Override
    public String toString() {
        return
                "Id=" + albumId +
                        ", title='" + title + '\'' +
                        ", artistId=" + artistId +
                        ", Release date=" + releaseDate;
    }
}
