package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.Album;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class AlbumDaoImpl extends MySQLDao implements AlbumDAO{

    public AlbumDaoImpl(){
        super();
    }

    public AlbumDaoImpl(Connection conn){
        super(conn);
    }

    public AlbumDaoImpl(String propertiesFilename){
        super(propertiesFilename);
    }


    /**
     * Retrieve an album by artist's id.
     * @param artistId - the id of the artist to retrieve his albums.
     * @return - a list of all artist's albums.
     */
    @Override
    public List<Album> getAllAlbumsByArtistId(int artistId) {



        Album album;
        List<Album> result = new ArrayList<>();
        String query = "SELECT * FROM Albums WHERE artistID = ? ORDER BY albumID";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {


            ps.setInt(1, artistId);

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
//                Using builder for easier object creation
                    album = fromResultSet(rs);
//                Add the artist to the result list
                    result.add(album);
                }
            } catch (SQLException e) {
                log.error("Error retrieving albums by ArtistID: {}", artistId, e);
            }


        } catch (SQLException e) {
            log.error("Error accessing the database: ", e);
        }

        return result;
    }

    /**
     * Retrieve all albums where artist's name contains passed query.
     * @param artistName - the name of the artist to retrieve his albums.
     * @return - a list of all artist's albums.
     */
    @Override
    public List<Album> getAllAlbumsWhereArtistNameLike(String artistName) {



        Album album;
        List<Album> result = new ArrayList<>();
        String query = """
                    SELECT Albums.*\s
                    FROM Albums
                    JOIN Artists ON Albums.artistID = Artists.artistID
                    WHERE Artists.name LIKE ?""";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setString(1, "%" + artistName + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
//                Using builder for easier object creation
                    album = fromResultSet(rs);
//                Add the artist to the result list
                    result.add(album);
                }

            } catch (SQLException e) {
                log.error("Error retrieving albums where artist name like: {}", artistName, e);
            }


        } catch (SQLException e) {
            log.error("Error accessing the database: ", e);
        }

        return result;
    }

    /**
     * Retrieve all albums by artist's name.
     * @param artistName - the name of the artist to retrieve his albums.
     * @return - a list of all artist's albums.
     */
    @Override
    public List<Album> getAllAlbumsByArtistName(String artistName) {



        Album album;
        List<Album> result = new ArrayList<>();

        String query = """
                    SELECT Albums.*\s
                    FROM Albums
                    JOIN Artists ON Albums.artistID = Artists.artistID
                    WHERE Artists.name = ?""";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query);
        ) {

            ps.setString(1, artistName);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
//                Using builder for easier object creation
                    album = fromResultSet(rs);
//                Add the artist to the result list
                    result.add(album);
                }

            } catch (SQLException e) {
                log.error("Error retrieving albums by artist name: {}", artistName, e);
            }


        } catch (SQLException e) {
            log.error("Error accessing the database: ", e);
        }

        return result;
    }

    /**
     * Retrieve album by id.
     * @param albumId - the id of the album to retrieve.
     * @return - the album with the given id.
     */
    @Override
    public Album getAlbumById(int albumId) {

        Album album = null;

        String query = "SELECT * FROM Albums WHERE albumID = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query);
        ) {
            ps.setInt(1, albumId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    album = fromResultSet(rs);
                }

            } catch (SQLException e) {
                log.error("Error retrieving album by ID: {}", albumId, e);
            }

        } catch (SQLException e) {
            log.error("Error accessing the database: ", e);
        }
        return album;
    }

    @Override
    public List<Album> getAllAlbumsWhereNameLike(String artistName){

        Album album;
        List<Album> result = new ArrayList<>();

        String query = "SELECT * FROM Albums WHERE title LIKE ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)
        ){

            ps.setString(1, "%"+artistName+"%");

            try(ResultSet rs = ps.executeQuery()){

                while (rs.next()) {
//                Using builder for easier object creation
                    album = fromResultSet(rs);
//                Add the artist to the result list
                    result.add(album);
                }

            } catch (SQLException e) {
                log.error("Error retrieving artists where name like: {}", artistName, e);
            }


        } catch (SQLException e) {
            log.error("Error accessing the database: ", e);
        }

        return result;
    }

    private static Album fromResultSet(ResultSet rs) throws SQLException {
        Album album;
        album = Album.builder()
                .albumId(rs.getInt("artistId"))
                .title(rs.getString("title"))
                .artistId(rs.getInt("artistId"))
                .releaseDate(rs.getDate("releaseDate"))
                .build();
        return album;
    }

    /**
     * Retrieve album id by name from the database using the album name.
     * Uses prepared statement to prevent SQL injection.
     *
     * @param albumName - the name of the album to retrieve.
     * @return - the id of the album with the given name.
     */
    @Override
    public Album getAlbumByName(String albumName) {
            Album album = null;
            String query = "SELECT * FROM Albums WHERE title = ?";

            try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(query);
            ) {

                ps.setString(1, albumName);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        album = Album.builder()
                                .albumId(rs.getInt("artistId"))
                                .title(rs.getString("title"))
                                .artistId(rs.getInt("artistId"))
                                .releaseDate(rs.getDate("releaseDate"))
                                .build();
                    }

                } catch (SQLException e) {
                    log.error("Error retrieving album by name: {}", albumName, e);
                }

            } catch (SQLException e) {
                log.error("Error accessing the database: ", e);
            }
            return album;
    }

}
