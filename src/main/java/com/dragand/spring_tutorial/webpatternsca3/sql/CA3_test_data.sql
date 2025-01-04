-- Insert data into Artists table
INSERT INTO Artists (name)
VALUES
    ('The Beatles'), ('Led Zeppelin'), ('Taylor Swift'), ('Beyoncé'), ('Eminem');



-- Insert data into Albums table
INSERT INTO Albums (title, artistID, releaseDate)
VALUES
    ('Abbey Road', 1, '1969-09-26'), ('Led Zeppelin IV', 2, '1971-11-08'),
    ('1989', 3, '2014-10-27'), ('Lemonade', 4, '2016-04-23'), ('The Eminem Show', 5, '2002-05-26');




-- Insert data into Users table
INSERT INTO Users (firstName, lastName, username, password)
VALUES
    ('John', 'Doe', 'johndoe', 'password123'),
    ('Jane', 'Smith', 'janesmith', 'password456');




-- Insert data into Songs table
INSERT INTO Songs (title, artistID, albumID, length)
VALUES
    ('Come Together', 1, 1, '00:04:20'),
    ('Let It Be', 1, 1, '00:03:50'),
    ('Stairway to Heaven', 2, 2, '00:08:02'),
    ('Whole Lotta Love', 2, 2, '00:05:34'),
    ('Blank Space', 3, 3, '00:03:51'),
    ('Formation', 4, 4, '00:03:26'),
    ('Halo', 4, 4, '00:03:44'),
    ('Lose Yourself', 5, 5, '00:05:26'),
    ('Without Me', 5, 5, '00:04:00');





-- Insert data into ArtistsSongs (Junction) table
INSERT INTO ArtistsSongs (artistID, songID)
VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (2, 4),
    (3, 5),
    (4, 6),
    (4, 7),
    (5, 8),
    (5, 9);


-- Insert data into Playlists table
INSERT INTO Playlists (name, userID, isPublic)
VALUES
    ('Top Hits', 1, true), -- Playlist created by User 1
    ('Chill Out', 2, true);

-- Insert data into PlaylistSongs(Junction Table) table
INSERT INTO PlaylistSongs (playlistID, songID) VALUES
                                                   (1, 1),
                                                   (1, 2),
                                                   (1, 5),
                                                   (1, 3),
                                                   (2, 4),
                                                   (2, 5);



-- Insert data into Ratings table
INSERT INTO Ratings (userID, songID, ratingValue) VALUES
                                                      (1, 1, 4),
                                                      (1, 2, 3),
                                                      (1, 3, 4),
                                                      (1, 4, 5),
                                                      (2, 1, 2),
                                                      (2, 2, 4),
                                                      (2, 3, 5),
                                                      (2, 4, 3),
                                                      (2, 5, 4);