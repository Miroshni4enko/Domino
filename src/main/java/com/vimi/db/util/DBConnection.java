package com.vimi.db.util;

import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by vimi on 10/18/2017.
 */
public class DBConnection {
    public static final String DB_DRIVER_CLASS = "DB_DRIVER_CLASS";
    public static final String DB_URL = "DB_URL";
    public static final String DB_USERNAME = "DB_USERNAME";
    public static final String DB_PASSWORD = "DB_PASSWORD";
    public static final String UNIQUE_RES_NAME = "jdbc/dominoes";
    public static final String PATH_TO_DB_PROP = "src\\main\\resources\\db.properties";

    private static final Logger LOG = LoggerFactory.getLogger(DBConnection.class);
    private DataSource dataSource;
    private Properties props = new Properties();
    
    public DBConnection(){
        loadProp();
        try {
            dataSource = (javax.sql.DataSource) setupDataSources().lookup(UNIQUE_RES_NAME);
        } catch (NamingException e) {
            LOG.error("Can't lookup data source {}", e);
        }
    }
    
    private void loadProp() {
        try (InputStream inputStream = new FileInputStream(PATH_TO_DB_PROP)){
            props.load(inputStream);
        } catch (IOException e) {
           LOG.error("Can't load db.properties {}", e);
        }
    }
    
    public Connection getConnection() {
        Connection con = null;
        try{
            con = dataSource.getConnection();
        } catch (SQLException e) {
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

    public InitialContext setupDataSources(){
        AtomikosNonXADataSourceBean dataSource = new AtomikosNonXADataSourceBean();
        dataSource.setUniqueResourceName(UNIQUE_RES_NAME);
        dataSource.setDriverClassName(props.getProperty(DB_DRIVER_CLASS));
        dataSource.setUrl(props.getProperty(DB_URL));
        dataSource.setUser(props.getProperty(DB_USERNAME));
        dataSource.setPassword(props.getProperty(DB_PASSWORD));
        dataSource.setMaxPoolSize(10);
        InitialContext initialContext = null;
        try {
            initialContext = new InitialContext();
            initialContext.createSubcontext("jdbc").bind("dominoes", dataSource);
        } catch (NamingException e) {
            LOG.error("Can't create Initial Context {}", e);
        }
        return initialContext;
    }
}
