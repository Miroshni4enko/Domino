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
    
    static final String CREATE_SET = "INSERT INTO public.sets(chain_id, value) VALUES (?, ?);";
    static final String CREATE_CHAIN = "INSERT INTO public.chains(value, date) VALUES (?, ?);";
    static final String GET_ALL_SETS_BY_CHAIN = "SELECT set_id, chain_id, set\n" +
            "FROM sets \n" +
            "WHERE chain_id = ?;";
}
