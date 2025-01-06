package com.dragand.spring_tutorial.webpatternsca3.persistence;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

@Slf4j
public class MySQLDao {
    private Properties properties;
    private Connection conn;

    public MySQLDao(){
    }

    public MySQLDao(Connection conn){
        this.conn = conn;
    }

    public MySQLDao(String propertiesFilename){
        properties = new Properties();
        try {
            // Get the path to the specified properties file
            String rootPath = Thread.currentThread().getContextClassLoader().getResource(propertiesFilename).getPath();
            // Load in all key-value pairs from properties file
            properties.load(new FileInputStream(rootPath));
        }catch(IOException e){
            log.error("An exception occurred when attempting to load properties from \"{}\": {}", propertiesFilename, e.getMessage());
//            e.printStackTrace();
        }

    }

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

    public void freeConnection(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            log.error("{}: An SQLException occurred while trying to close the database connection.", LocalDateTime.now());
            log.error("Error: {}", e.getMessage());
//            e.printStackTrace();
        }
    }
}
