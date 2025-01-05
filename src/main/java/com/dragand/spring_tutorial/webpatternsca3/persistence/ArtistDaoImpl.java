package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Dmytro Drahan
 */
public class ArtistDaoImpl extends MySQLDao implements ArtistDAO {



    /**
     * Get the connection from the superclass by passing the database name
     * @param dbName - the name of the database
     */
    public ArtistDaoImpl(String dbName) {
        super(dbName);
    }


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
                System.out.println("Exception occurred in the getArtistById() method: " + e.getMessage());
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the getArtistById() method: " + e.getMessage());
        }
        return artist;

    }

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
                System.out.println("Exception occurred in the getArtistById() method: " + e.getMessage());
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the getArtistById() method: " + e.getMessage());
        }
        return artist;

    }

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
                System.out.println("Exception occurred in the getArtistById() method: " + e.getMessage());
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the getArtistById() method: " + e.getMessage());
        }

        return result;

    }


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
                System.out.println("Exception occurred in the getArtistById() method: " + e.getMessage());
            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in the getArtistById() method: " + e.getMessage());
        }

        return result;
    }
}