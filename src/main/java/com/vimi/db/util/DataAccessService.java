package com.vimi.db.util;

import com.vimi.db.dao.HistoryObject;
import com.vimi.exception.DataBaseException;
import com.vimi.model.Chain;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static com.vimi.db.util.SQLScripts.*;

/**
 * Created by vymi1016 on 10/18/2017.
 */
public class DataAccessService {

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
    
    

    public void createSets(int chain_id, List<Chain> sets) throws DataBaseException {
        Connection connection = dbConnection.getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(CREATE_SET);
            for (Chain set: sets) {
                statement.setInt(1, chain_id);
                statement.setString(2, set.toString());
            }
            statement.executeBatch();
        } catch (SQLException e) {
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
            statement.setDate(1, new java.sql.Date(date.getTime()));
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
            historyObjectList.add(getHistoryOfOneSet(result));
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            dbConnection.disconnect(connection, result, statement);
        }
        return historyObjectList;
    }
    
    private HistoryObject getHistoryOfOneSet(ResultSet result) throws DataBaseException {
        HistoryObject historyObject = null;
        try {
            Date date = result.getDate(DATE);
            String chain = result.getString(CHAIN);
            String set = result.getString(SET);
            historyObject = new HistoryObject(date, chain, set);

        } catch (SQLException e) {
            throw new DataBaseException("Exception with data from result set", e);
        }
        return historyObject;
    }
    
}