package com.vimi.db.util;

/**
 * Created by vymi1016 on 10/18/2017.
 */
public class SQLScripts {
    static final String CHAIN_ID ="chain_id";
    static final String SET_ID ="set_id";
    static final String SET ="set";
    static final String DATE ="date";
    static final String CHAIN ="chain";
    
    static final String CREATE_SET = "INSERT INTO sets(chain_id, set) VALUES (?, ?);";
    static final String CREATE_CHAIN = "INSERT INTO chains(date, chain) VALUES (?, ?) RETURNING chain_id;";
    static final String GET_ALL_HISTORY = "SELECT date, chain, set\n" +
            "FROM chains ch\n" +
            "INNER JOIN sets s\n" +
            "ON ch.chain_id = s.chain_id" +
            "Order By ch.date;";
}
