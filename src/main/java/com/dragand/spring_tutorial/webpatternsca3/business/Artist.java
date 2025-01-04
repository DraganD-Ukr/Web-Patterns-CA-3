package com.dragand.spring_tutorial.webpatternsca3.business;

import lombok.*;

/**
 * @Author: Dmytro Drahan
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Artist {


    @EqualsAndHashCode.Include
    private Integer artistId;

    private String name;

    @Override
    public String toString() {
        return
                "Id=" + artistId +
                        ", Name=" + name + '\'';
    }



}