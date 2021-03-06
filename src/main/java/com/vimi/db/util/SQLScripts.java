package com.vimi.db.util;

/**
 * Created by vimi on 10/18/2017.
 */
public class SQLScripts {
    static final String SET ="set";
    static final String DATE ="date";
    static final String CHAIN ="chain";
    
    static final String CREATE_SET = "INSERT INTO sets(chain_id, set) VALUES (?, ?);";
    static final String CREATE_CHAIN = "INSERT INTO chains(date, chain) VALUES (?, ?) RETURNING chain_id;";
    static final String GET_ALL_HISTORY = "SELECT date, chain, set\n" +
            "FROM chains ch\n" +
            "LEFT JOIN sets s\n" +
            "ON ch.chain_id = s.chain_id\n" +
            "Order by ch.date desc;";
}
