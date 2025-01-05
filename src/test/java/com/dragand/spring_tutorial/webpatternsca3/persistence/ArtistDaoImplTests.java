package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Artist;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArtistDaoImplTests {

    private static ArtistDaoImpl artistDao;
    private static List<Artist> allArtists;

    @BeforeAll
    public static void init() {

        artistDao = new ArtistDaoImpl("database-test.properties");

//      Artists are in order by id for easier comparison
        allArtists = new ArrayList<>();

        allArtists.add(
                Artist.builder()
                        .artistId(1)
                        .name("The Beatles")
                        .build()
        );
        allArtists.add(
                Artist.builder()
                        .artistId(2)
                        .name("Led Zeppelin")
                        .build()
        );
        allArtists.add(
                Artist.builder()
                        .artistId(3)
                        .name("Taylor Swift")
                        .build()
        );
        allArtists.add(
                Artist.builder()
                        .artistId(4)
                        .name("Beyoncé")
                        .build()
        );
        allArtists.add(
                Artist.builder()
                        .artistId(5)
                        .name("Eminem")
                        .build()
        );

    }


    @Test
    public void testGetAllArtists() {

        List<Artist> result = artistDao.getAllArtists();

//      Assert that the list is not null
        assertNotNull(result);
//      Assert that the list is the same size as the list of allArtists
        assertEquals(allArtists.size(), result.size());

//      Assert that all artists's fields in the list are not null
        for (Artist artist : result) {
            assertNotNull(artist.getArtistId());
            assertNotNull(artist.getName());
        }

//      Finally, assert that the list of artists is the same as the list of allArtists
        for (int i = 0; i < result.size(); i++) {
            assertEquals(result.get(i), allArtists.get(i));
        }

    }



    @Test
    public void testGetArtistById() {

        Artist result = artistDao.getArtistById(1);

        assertNotNull(result);

        assertNotNull(result.getArtistId());
        assertNotNull(result.getName());

        assertEquals(result, allArtists.get(0));
    }

    @Test
    public void testGetArtistById_NotFound() {

        Artist result = artistDao.getArtistById(7);

        assertNull(result);

    }

    @Test
    public void testGetArtistByName() {

        Artist result = artistDao.getArtistByName("Eminem");

        assertNotNull(result);

        assertNotNull(result.getArtistId());
        assertNotNull(result.getName());

        assertEquals(result, allArtists.get(4));
    }

    @Test
    public void testGetArtistByName_NotFound() {

        Artist result = artistDao.getArtistByName("Invalid Name");

        assertNull(result);
    }


    @Test
    public void getAllArtistsWhereNameLike() {

        List<Artist> result = artistDao.getAllArtistsWhereNameLike("e");

        assertNotNull(result);

        for (Artist artist : result) {
            assertNotNull(artist.getArtistId());
            assertNotNull(artist.getName());
        }

        assertEquals(4, result.size());

        assertEquals(result.get(0), allArtists.get(0));
        assertEquals(result.get(1), allArtists.get(1));
        assertEquals(result.get(2), allArtists.get(3));
        assertEquals(result.get(3), allArtists.get(4));
    }

    @Test
    public void getAllArtistsWhereNameLike_NotFound() {

        List<Artist> result = artistDao.getAllArtistsWhereNameLike("Dra");

        assertEquals(0, result.size());
    }



}
