DROP DATABASE IF EXISTS CA3;
CREATE DATABASE IF NOT EXISTS CA3;

USE CA3;




-- Create Artists table
CREATE TABLE Artists (
                         artistID INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL UNIQUE
);

-- Create Albums table
CREATE TABLE Albums (
                        albumID INT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        artistID INT NOT NULL,
                        releaseDate DATE NOT NULL,
                        FOREIGN KEY (artistID) REFERENCES Artists(artistID) ON DELETE CASCADE
);

-- Create Users table
CREATE TABLE Users (
                       userID INT AUTO_INCREMENT PRIMARY KEY,
                       firstName VARCHAR(100) NOT NULL,
                       lastName VARCHAR(100) NOT NULL,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       registrationDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create Songs table
CREATE TABLE Songs (
                       songID INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       albumID INT NOT NULL,
                       artistID INT NOT NULL,
                       length TIME NOT NULL,
                       ratingCount INT NOT NULL DEFAULT 0,
                       averageRating DECIMAL(3, 2) DEFAULT 0,
                       ratingsSum INT DEFAULT 0,
                       FOREIGN KEY (albumID) REFERENCES Albums(albumID) ON DELETE CASCADE,
                       FOREIGN KEY (artistID) REFERENCES Artists(artistID) ON DELETE CASCADE
);


-- Create Ratings table
CREATE TABLE Ratings (
                         ratingID INT AUTO_INCREMENT PRIMARY KEY,
                         userID INT NOT NULL,
                         songID INT NOT NULL,
                         ratingValue TINYINT NOT NULL CHECK (ratingValue >= 1 AND ratingValue <= 5),
                         createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE CASCADE,
                         FOREIGN KEY (songID) REFERENCES Songs(songID) ON DELETE CASCADE
);

-- Create Playlists table
CREATE TABLE Playlists (
                           playlistID INT AUTO_INCREMENT PRIMARY KEY,
                           userID INT NOT NULL,
                           name VARCHAR(255) NOT NULL,
                           isPublic BOOLEAN DEFAULT FALSE,
                           FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE CASCADE
);

-- Create PlaylistSongs table (Junction table for Playlists and Songs)
CREATE TABLE PlaylistSongs (
                               playlistID INT NOT NULL,
                               songID INT NOT NULL,
                               PRIMARY KEY (playlistID, songID),
                               FOREIGN KEY (playlistID) REFERENCES Playlists(playlistID) ON DELETE CASCADE,
                               FOREIGN KEY (songID) REFERENCES Songs(songID) ON DELETE CASCADE
);

-- Create ArtistsSongs table (Junction table for Artists and Songs)
CREATE TABLE ArtistsSongs (
                              artistID INT NOT NULL,
                              songID INT NOT NULL,
                              PRIMARY KEY (artistID, songID),
                              FOREIGN KEY (artistID) REFERENCES Artists(artistID) ON DELETE CASCADE,
                              FOREIGN KEY (songID) REFERENCES Songs(songID) ON DELETE CASCADE
);

-- Trigger for updating ratings
DELIMITER //

CREATE TRIGGER after_rating_insert
    AFTER INSERT ON Ratings
    FOR EACH ROW
BEGIN
    -- Update the ratingCount and ratingsSum first
    UPDATE Songs
    SET
        ratingCount = ratingCount + 1,
        ratingsSum = ratingsSum + NEW.ratingValue
    WHERE
        songID = NEW.songID;

    -- Update the averageRating based on the updated ratingCount and ratingsSum
    UPDATE Songs
    SET
        averageRating = ratingsSum / ratingCount
    WHERE
        songID = NEW.songID;
END; //

DELIMITER ;