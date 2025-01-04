-- Insert data into Artists table
INSERT INTO Artists (name)
VALUES
    ('The Beatles'), ('Led Zeppelin'), ('Taylor Swift'), ('Beyoncé'), ('Eminem'),
    ('Drake'), ('Ariana Grande'), ('Ed Sheeran'), ('Post Malone'), ('Rihanna'),
    ('Billie Eilish'), ('Bruno Mars'), ('The Weeknd'), ('Kanye West'), ('Coldplay'),
    ('Queen'), ('AC/DC'), ('Pink Floyd'), ('Metallica'), ('Imagine Dragons'),
    ('Kendrick Lamar'), ('J. Cole'), ('Travis Scott'), ('Dua Lipa'), ('Lady Gaga'),
    ('Justin Bieber'), ('Selena Gomez'), ('Shawn Mendes'), ('Cardi B'), ('Khalid'),
    ('Maroon 5'), ('Harry Styles'), ('Lil Nas X'), ('Lizzo'), ('Doja Cat'),
    ('The Rolling Stones'), ('Frank Ocean'), ('Lana Del Rey'), ('Nirvana'),
    ('Red Hot Chili Peppers'), ('The Killers'), ('Tame Impala'), ('Fleetwood Mac'),
    ('Gorillaz'), ('Daft Punk'), ('Linkin Park'), ('Radiohead'), ('Oasis'),
    ('Elton John'), ('John Mayer'), ('David Bowie'), ('Sia'), ('olezhakash');



-- Insert data into Albums table
INSERT INTO Albums (title, artistID, releaseDate)
VALUES
    ('Abbey Road', 1, '1969-09-26'), ('Led Zeppelin IV', 2, '1971-11-08'),
    ('1989', 3, '2014-10-27'), ('Lemonade', 4, '2016-04-23'), ('The Eminem Show', 5, '2002-05-26'),
    ('Take Care', 6, '2011-11-15'), ('Thank U, Next', 7, '2019-02-08'),
    ('Divide', 8, '2017-03-03'), ('Hollywood\'s Bleeding', 9, '2019-09-06'),
    ('ANTI', 10, '2016-01-28'), ('When We All Fall Asleep, Where Do We Go?', 11, '2019-03-29'),
    ('Doo-Wops & Hooligans', 12, '2010-10-05'), ('After Hours', 13, '2020-03-20'),
    ('My Beautiful Dark Twisted Fantasy', 14, '2010-11-22'), ('A Rush of Blood to the Head', 15, '2002-08-26'),
    ('A Night at the Opera', 16, '1975-11-21'), ('Back in Black', 17, '1980-07-25'),
    ('The Dark Side of the Moon', 18, '1973-03-01'), ('Metallica', 19, '1991-08-12'),
    ('Evolve', 20, '2017-06-23'), ('DAMN.', 21, '2017-04-14'), ('2014 Forest Hills Drive', 22, '2014-12-09'),
    ('Astroworld', 23, '2018-08-03'), ('Future Nostalgia', 24, '2020-03-27'),
    ('Born This Way', 25, '2011-05-23'), ('Purpose', 26, '2015-11-13'),
    ('Rare', 27, '2020-01-10'), ('Wonder', 28, '2020-12-04'), ('Invasion of Privacy', 29, '2018-04-06'),
    ('Free Spirit', 30, '2019-04-05'), ('V', 31, '2014-08-29'), ('Fine Line', 32, '2019-12-13'),
    ('Montero', 33, '2021-09-17'), ('Cuz I Love You', 34, '2019-04-19'),
    ('Planet Her', 35, '2021-06-25'), ('Sticky Fingers', 36, '1971-04-23'),
    ('Blonde', 37, '2016-08-20'), ('Norman Fucking Rockwell!', 38, '2019-08-30'),
    ('Nevermind', 39, '1991-09-24'), ('Californication', 40, '1999-06-08'),
    ('Hot Fuss', 41, '2004-06-07'), ('The Slow Rush', 42, '2020-02-14'),
    ('Rumours', 43, '1977-02-04'), ('Plastic Beach', 44, '2010-03-03'),
    ('Random Access Memories', 45, '2013-05-17'), ('Hybrid Theory', 46, '2000-10-24'),
    ('OK Computer', 47, '1997-05-21'), ('(What\'s the Story) Morning Glory?', 48, '1995-10-02'),
    ('Goodbye Yellow Brick Road', 49, '1973-10-05'), ('Continuum', 50, '2006-09-12');




-- Insert data into Users table
INSERT INTO Users (firstName, lastName, username, password)
VALUES
    ('John', 'Doe', 'johndoe', 'password123'),
    ('Jane', 'Smith', 'janesmith', 'password456'),
    ('Alice', 'Johnson', 'alicej', 'password789'),
    ('Bob', 'Brown', 'bobbrown', 'password321'),
    ('Emily', 'Williams', 'emilyw', 'password234'),
    ('Michael', 'Davis', 'michaeld', 'password567'),
    ('Jessica', 'Miller', 'jessicam', 'password890'),
    ('Daniel', 'Wilson', 'danielw', 'password345'),
    ('Sarah', 'Moore', 'sarahm', 'password678'),
    ('James', 'Taylor', 'jamest', 'password901');




-- Insert data into Songs table
INSERT INTO Songs (title, artistID, albumID, length)
VALUES
    ('Come Together', 1, 1, '00:04:20'), --  (The Beatles)
    ('Let It Be', 1, 1, '00:03:50'),
    ('Stairway to Heaven', 2, 2, '00:08:02'), -- (Led Zeppelin)
    ('Whole Lotta Love', 2, 2, '00:05:34'),
    ('Blank Space', 3, 3, '00:03:51'), -- (Taylor Swift)
    ('Formation', 4, 4, '00:03:26'), --  (Beyoncé)
    ('Halo', 4, 4, '00:03:44'),
    ('Lose Yourself', 5, 5, '00:05:26'), --  (Eminem)
    ('Without Me', 5, 5, '00:04:00'),
    ('God\'s Plan', 6, 6, '00:03:18'), -- (Drake)
    ('Hotline Bling', 6, 6, '00:03:27'),
    ('7 Rings', 7, 7, '00:02:58'), -- (Ariana Grande)
    ('thank u, next', 7, 7, '00:03:27'),
    ('Shape of You', 8, 8, '00:03:53'), -- (Ed Sheeran)
    ('Castle on the Hill', 8, 8, '00:04:21'),
    ('Supermarket Flowers', 8, 8, '00:03:41'),
    ('Thinking Out Loud', 8, 8, '00:04:41'),
    ('Circles', 9, 9, '00:03:38'), -- (Post Malone)
    ('Better Now', 9, 9, '00:03:38'),
    ('Diamonds', 10,10, '00:03:45'), -- (Rihanna)
    ('We Found Love', 10, 10, '00:03:36'),
    ('Shake It Off', 3, 3, '00:03:39'); -- (Taylor Swift)





-- Insert data into ArtistsSongs (Junction) table
INSERT INTO ArtistsSongs (artistID, songID)
VALUES
    (1, 1), -- The Beatles - Come Together
    (1, 2), -- The Beatles - Let It Be
    (2, 3), -- Led Zeppelin - Stairway to Heaven
    (2, 4), -- Led Zeppelin - Whole Lotta Love
    (3, 5), -- Taylor Swift - Blank Space
    (3, 6), -- Taylor Swift - Shake It Off
    (4, 7), -- Beyoncé - Formation
    (4, 8), -- Beyoncé - Halo
    (5, 9), -- Eminem - Lose Yourself
    (5, 10), -- Eminem - Without Me
    (6, 11), -- Drake - God's Plan
    (6, 12), -- Drake - Hotline Bling
    (7, 13), -- Ariana Grande - 7 Rings
    (7, 14), -- Ariana Grande - thank u, next
    (8, 15), -- Ed Sheeran - Shape of You
    (8, 16), -- Ed Sheeran - Castle on the Hill
    (8, 17), -- Ed Sheeran - Supermarket Flowers
    (8, 18), -- Ed Sheeran - Thinking Out Loud
    (9, 19), -- Post Malone - Circles
    (9, 20), -- Post Malone - Better Now
    (10, 21), -- Rihanna - Diamonds
    (10, 22); -- Rihanna - We Found Love


-- Insert data into Playlists table
INSERT INTO Playlists (name, userID, isPublic)
VALUES
    ('Top Hits', 1, true), -- Playlist created by User 1
    ('Chill Out', 2, true), -- Playlist created by User 2
    ('Workout Jams', 3, true), -- Playlist created by User 3
    ('Feel Good Songs', 4, true), -- Playlist created by User 4
    ('Throwback Classics', 5,true); -- Playlist created by User 5

-- Insert data into PlaylistSongs(Junction Table) table
INSERT INTO PlaylistSongs (playlistID, songID) VALUES
                                                   (1, 1), -- Top Hits - Come Together
                                                   (1, 2), -- Top Hits - Stairway to Heaven
                                                   (1, 3), -- Top Hits - Blank Space
                                                   (2, 4), -- Chill Out - Formation
                                                   (2, 5), -- Chill Out - Lose Yourself
                                                   (3, 6), -- Workout Jams - God's Plan
                                                   (3, 7), -- Workout Jams - 7 Rings
                                                   (4, 8), -- Feel Good Songs - Shape of You
                                                   (5, 9), -- Throwback Classics - Circles
                                                   (5, 10); -- Throwback Classics - Diamonds


-- Insert data into Ratings table
INSERT INTO Ratings (userID, songID, ratingValue) VALUES
                                                      (1, 3, 4),  -- User 1 rates Stairway to Heaven
                                                      (2, 5, 3),  -- User 2 rates Blank Space
                                                      (3, 6, 4),  -- User 3 rates Formation
                                                      (4, 8, 5),  -- User 4 rates Lose Yourself
                                                      (1, 10, 3), -- User 1 rates God's Plan
                                                      (2, 11, 4), -- User 2 rates 7 Rings
                                                      (3, 14, 5), -- User 3 rates Shape of You
                                                      (4, 18, 2), -- User 4 rates Circles
                                                      (1, 20, 5), -- User 1 rates Diamonds
                                                      (2, 9, 4),  -- User 2 rates Without Me
                                                      (3, 8, 5),  -- User 3 rates Lose Yourself
                                                      (4, 11, 3), -- User 4 rates Hotline Bling
                                                      (1, 12, 4), -- User 1 rates thank u, next
                                                      (2, 15, 2), -- User 2 rates Castle on the Hill
                                                      (3, 17, 5), -- User 3 rates Thinking Out Loud
                                                      (4, 16, 4), -- User 4 rates Supermarket Flowers
                                                      (1, 21, 5), -- User 1 rates We Found Love
                                                      (2, 19, 3), -- User 2 rates Better Now
                                                      (3, 22, 4), -- User 3 rates Shake It Off
                                                      (4, 20, 5), -- User 4 rates Diamonds
                                                      (1, 5, 4),  -- User 1 rates Blank Space
                                                      (2, 18, 5), -- User 2 rates Circles
                                                      (3, 8, 3),  -- User 3 rates Lose Yourself
                                                      (4, 12, 4), -- User 4 rates thank u, next
                                                      (1, 3, 2),  -- User 1 rates Stairway to Heaven
                                                      (2, 6, 4),  -- User 2 rates Formation
                                                      (3, 10, 5), -- User 3 rates God's Plan
                                                      (4, 7, 4),  -- User 4 rates 7 Rings
                                                      (1, 14, 3), -- User 1 rates Shape of You
                                                      (2, 12, 5), -- User 2 rates thank u, next
                                                      (3, 20, 4), -- User 3 rates Diamonds
                                                      (4, 8, 5),  -- User 4 rates Lose Yourself
                                                      (1, 7, 3),  -- User 1 rates 7 Rings
                                                      (2, 18, 5); -- User 2 rates Circles