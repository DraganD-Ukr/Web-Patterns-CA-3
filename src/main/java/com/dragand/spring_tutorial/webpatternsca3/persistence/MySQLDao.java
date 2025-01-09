package com.dragand.spring_tutorial.webpatternsca3.persistence;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

@Slf4j
public class MySQLDao {
    private Properties properties;
    private Connection conn;

    /**
     * Default constructor. Loads properties from the default file name(database.properties).
     */
    public MySQLDao() {
        loadProperties("database.properties"); // Default file name
    }

    /**
     * Constructor that takes a connection as a parameter.
     * @param conn - the connection to the database.
     */
    public MySQLDao(Connection conn){
        this.conn = conn;
    }

    /**
     * Constructor that takes a properties file name as a parameter.
     * @param propertiesFilename - the name of the properties file.
     */
    public MySQLDao(String propertiesFilename){
        properties = new Properties();
        loadProperties(propertiesFilename);
    }

    /**
     * Get a connection to the database.
     * @return - the connection to the database.
     */
    public Connection getConnection(){
        if(conn != null){
            return conn;
        }

        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String database = properties.getProperty("database");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password", "");
        try{
            Class.forName(driver);

            try{
                Connection conn = DriverManager.getConnection(url+database, username, password);
                return conn;
            }catch(SQLException e){
                log.error("{}: An SQLException  occurred while trying to connect to the {}database.", LocalDateTime.now(), url);
                log.error("Error: {}", e.getMessage());
//                e.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            log.error("{}: A ClassNotFoundException occurred while trying to load the MySQL driver.", LocalDateTime.now());
            log.error("Error: {}", e.getMessage());
//            e.printStackTrace();
        }
        return null;
    }

    /**
     * Load properties from a file.
     * @param propertiesFilename - the name of the properties file.
     */
    private void loadProperties(String propertiesFilename) {
        properties = new Properties();
        try (InputStream input = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(propertiesFilename)) {

            if (input == null) {
                throw new FileNotFoundException("Property file '" + propertiesFilename + "' not found in the classpath");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties from: " + propertiesFilename, e);
        }
    }

}
