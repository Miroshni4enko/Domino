package com.vimi.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by vymi1016 on 10/18/2017.
 */
public class DBConnection {
    public static final String DB_DRIVER_CLASS = "DB_DRIVER_CLASS";
    public static final String DB_URL = "DB_URL";
    public static final String DB_USERNAME = "DB_USERNAME";
    public static final String DB_PASSWORD = "DB_PASSWORD";

    private Properties props = new Properties();
    
    private DBConnection(){
        loadProp();
    }

    protected static class Singleton {
        public static final DBConnection _INSTANCE = new DBConnection();
    }
    public static Connection getDBConnection() {
        return DBConnection.Singleton._INSTANCE.getConnection();
    }
    
    public void loadProp() {
        try (InputStream inputStream = new FileInputStream("src\\main\\resources\\db.properties")){
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        Connection con = null;
        try{
            Class.forName(props.getProperty(DB_DRIVER_CLASS));
            con = DriverManager.getConnection(props.getProperty(DB_URL),
                    props.getProperty(DB_USERNAME),
                    props.getProperty(DB_PASSWORD));
        } catch ( ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }
}
