package com.vimi.db.util;

import com.vimi.db.dao.HistoryObject;
import com.vimi.exception.DataBaseException;
import com.vimi.model.Chain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

import static com.vimi.db.util.SQLScripts.*;

/**
 * Created by vimi on 10/18/2017.
 */
public class DataAccessService {
    private static final Logger LOG = LoggerFactory.getLogger(DataAccessService.class);

    private DBConnection dbConnection;

    private DataAccessService() {
        this.dbConnection = new DBConnection();
    }

    protected static class Singleton {
        public static final DataAccessService _INSTANCE = new DataAccessService();
    }
    
    public static DataAccessService getInstance() {
        return DataAccessService.Singleton._INSTANCE;
    }
    
    

    public void createSets(int chain_id, List<Chain> sets) throws DataBaseException, SQLException {
        Connection connection = dbConnection.getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(CREATE_SET);
            connection.setAutoCommit(false);
            for (Chain set: sets) {
                statement.setInt(1, chain_id);
                statement.setString(2, set.toString());
                statement.addBatch();
            }
            
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new DataBaseException("Exception for create", e);
        } finally {
            dbConnection.disconnect(connection, result, statement);
        }
    }
    
    public int createChain(String chainOfDominoes, java.util.Date date) throws DataBaseException {
        Connection connection = dbConnection.getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        int chain_id;
        try {
            statement = connection.prepareStatement(CREATE_CHAIN);
            statement.setTimestamp(1, convertToTimestamp(date));
            statement.setString(2, chainOfDominoes);
            result = statement.executeQuery();
            result.next();
            chain_id = result.getInt(1);
        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            dbConnection.disconnect(connection, result, statement);
        }
        return chain_id;
    }
    
    public List<HistoryObject> getAllHistory() throws DataBaseException {
        Connection connection = dbConnection.getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<HistoryObject>  historyObjectList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(GET_ALL_HISTORY);
            result = statement.executeQuery();
            while (result.next()) {
                historyObjectList.add(getHistoryOfOneSet(result));
            }
        } catch (Exception e) {
            LOG.debug("Most of all db is empty or Exception with data from database", e);
            return null;
        } finally {
            dbConnection.disconnect(connection, result, statement);
        }
        return historyObjectList;
    }
    
    private HistoryObject getHistoryOfOneSet(ResultSet result) throws DataBaseException {
        HistoryObject historyObject = null;
        try {
            Timestamp date = result.getTimestamp(DATE);
            String chain = result.getString(CHAIN);
            String set = result.getString(SET);
            historyObject = new HistoryObject(date, chain, set);
        } catch (SQLException e) {
            throw new DataBaseException("Exception with data from result set", e);
        }
        return historyObject;
    }
    
    public Timestamp convertToTimestamp(java.util.Date date) {
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        return  timestamp;
    }
}