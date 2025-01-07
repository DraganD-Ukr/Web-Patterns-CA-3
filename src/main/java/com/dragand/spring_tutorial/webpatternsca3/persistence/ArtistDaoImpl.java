package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Artist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Dmytro Drahan
 */
@Slf4j
@Repository
public class ArtistDaoImpl extends MySQLDao implements ArtistDAO {


    public ArtistDaoImpl(){
        super();
    }

    public ArtistDaoImpl(Connection conn){
        super(conn);
    }

    public ArtistDaoImpl(String dbName) {
        super(dbName);
    }

    /**
     * Retrieve an artist by id.
     * @param id the id of the artist to retrieve.
     * @return the artist with the given id, null if not found.
     */
    @Override
    public Artist getArtistById(int id) {


        Artist artist = null;
        String query = "SELECT * FROM Artists WHERE artistID = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
//                Using builder for easier object creation
                    artist = Artist.builder()
                            // Get the properties of an artist from the resultset
                            .artistId(rs.getInt("artistId"))
                            .name(rs.getString("name"))
                            .build();
                }
            } catch (SQLException e) {
                log.error("Error retrieving artist by ID: {}", id, e);
            }


        } catch (SQLException e) {
            log.error("Error accessing the database: ", e);
        }
        return artist;

    }

    /**
     * Retrieve an only artist by name.
     * @param name the name of the artist to retrieve.
     * @return the artist with the given name, null if not found.
     */
    @Override
    public Artist getArtistByName(String name) {


        Artist artist = null;
        String query = "SELECT * FROM Artists WHERE name = ?";

        try(Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()){

                if (rs.next()) {
//                Using builder for easier object creation
                    artist = Artist.builder()
                            // Get the properties of an artist from the resultset
                            .artistId(rs.getInt("artistId"))
                            .name(rs.getString("name"))
                            .build();
                }

            } catch (SQLException e) {
                log.error("Error retrieving artist by name: {}", query, e);
            }


        } catch (SQLException e) {
            log.error("Error accessing the database: ", e);
        }
        return artist;

    }


    /**
     * Retrieve all artists containing provided query.
     * @return a list of all matching artists.
     */
    @Override
    public List<Artist> getAllArtistsWhereNameLike(String artistName) {


        Artist artist;
        List<Artist> result = new ArrayList<>();

        String query = "SELECT * FROM Artists WHERE name LIKE ? ORDER BY artistID";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)
        ){

            ps.setString(1, "%"+artistName+"%");

            try(ResultSet rs = ps.executeQuery()){

                while (rs.next()) {
//                Using builder for easier object creation
                    artist = Artist.builder()
                            // Get the properties of an artist from the resultset
                            .artistId(rs.getInt("artistId"))
                            .name(rs.getString("name"))
                            .build();
//                Add the artist to the result list
                    result.add(artist);
                }

            } catch (SQLException e) {
                log.error("Error retrieving artists where name like: {}", artistName, e);
            }


        } catch (SQLException e) {
            log.error("Error accessing the database: ", e);
        }

        return result;

    }


    /**
     * Retrieve all artists.
     * @return a list of all artists in the library.
     */
    @Override
    public List<Artist> getAllArtists() {

        Artist artist;
        List<Artist> result = new ArrayList<>();

        String query = "SELECT * FROM Artists ORDER BY artistID";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)
        ) {

            try(ResultSet rs = ps.executeQuery()){

                while (rs.next()) {
//                Using builder for easier object creation
                    artist = Artist.builder()
                            // Get the properties of an artist from the resultset
                            .artistId(rs.getInt("artistId"))
                            .name(rs.getString("name"))
                            .build();
//                Add the artist to the result list
                    result.add(artist);
                }

            } catch (SQLException e) {
                log.error("Error retrieving all artists: {}", e);
            }


        } catch (SQLException e) {
            log.error("Error accessing the database: ", e);
        }

        return result;
    }
}