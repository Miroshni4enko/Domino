package com.vimi.db.util;

import com.vimi.db.dao.ChainOfDominoes;
import com.vimi.db.dao.SetOfDominoes;
import com.vimi.exception.DataBaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.vimi.db.util.SQLScripts.*;

/**
 * Created by vymi1016 on 10/18/2017.
 */
public class DataAccessService {
   
    private DBConnection dbConnection = DBConnection.getDBConnection();

    public void createSets(List<SetOfDominoes> setOfDominoesList) throws DataBaseException {
        Connection connection = dbConnection.getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(CREATE_CHAIN, Statement.RETURN_GENERATED_KEYS);
            for (SetOfDominoes setOfDominoes: setOfDominoesList) {
                statement.setInt(1, setOfDominoes.getChain_id());
                statement.setString(2, setOfDominoes.getSet());
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            dbConnection.disconnect(connection, result, statement);
        }
    }
    
    public int createChain(ChainOfDominoes chainOfDominoes) throws DataBaseException {
        Connection connection = dbConnection.getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        int chain_id;
        try {
            statement = connection.prepareStatement(CREATE_CHAIN, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, chainOfDominoes.getChain());
            statement.setDate(2, (Date) chainOfDominoes.getDate());
            statement.executeUpdate();
            result = statement.getGeneratedKeys();
            result.next();
            chain_id = result.getInt(1);
        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            dbConnection.disconnect(connection, result, statement);
        }
        return chain_id;
    }
    
    public List<SetOfDominoes> getSetsByChainId(int chain_id) throws DataBaseException {
        Connection connection = dbConnection.getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        
        List<SetOfDominoes> setOfDominoesList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(GET_ALL_SETS_BY_CHAIN);
            statement.setInt(1, chain_id);
            result = statement.executeQuery();
            setOfDominoesList.add(getSet(result));
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            dbConnection.disconnect(connection, result, statement);
        }
        return setOfDominoesList;
    }
    
    private SetOfDominoes getSet(ResultSet result) throws DataBaseException {
        SetOfDominoes setOfDominoes;
        try {
            int set_id = result.getInt(SET_ID);
            int chain_id = result.getInt(CHAIN_ID);
            String set = result.getString(SET);
            setOfDominoes = new SetOfDominoes(set_id, chain_id, set);

        } catch (SQLException e) {
            throw new DataBaseException("Exception with data from result set", e);
        }
        return setOfDominoes;
    }
    
}