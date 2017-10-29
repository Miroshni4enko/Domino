package com.vimi.db.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by vymi1016 on 10/18/2017.
 */
public class DBConnection {
    public static final String DB_DRIVER_CLASS = "DB_DRIVER_CLASS";
    public static final String DB_URL = "DB_URL";
    public static final String DB_USERNAME = "DB_USERNAME";
    public static final String DB_PASSWORD = "DB_PASSWORD";

    private static final Logger LOG = LoggerFactory.getLogger(DBConnection.class);

    private Properties props = new Properties();
    
    public DBConnection(){
        loadProp();
    }
    
    private void loadProp() {
        try (InputStream inputStream = new FileInputStream("src\\main\\resources\\db.properties")){
            props.load(inputStream);
        } catch (IOException e) {
           LOG.error("Can't load db.properties {}", e);
        }
    }
    
    public Connection getConnection() {
        Connection con = null;
        try{
            Class.forName(props.getProperty(DB_DRIVER_CLASS));
            con = DriverManager.getConnection(props.getProperty(DB_URL),
                    props.getProperty(DB_USERNAME),
                    props.getProperty(DB_PASSWORD));
        } catch (ClassNotFoundException | SQLException e) {
            LOG.error("Can't get connection from  DB {}", e);
        }
        return con;
    }

    public void disconnect(Connection connection, ResultSet result, Statement statement) {
        try {
            if(statement != null)
                statement.close();
            if(connection != null)
                connection.close();
            if(result != null)
                result.close();
        } catch (SQLException e) {
            LOG.error("Can't close connection {}", e);
        }
    }
}
