package com.vimi.db.dao;

import java.util.Date;

/**
 * Created by vymi1016 on 10/24/2017.
 */
public class ChainOfDominoes {
    private int chain_id;
    private Date date;
    private String chain;

    public String getChain() {
        return chain;
    }

    public int getChain_id() {
        return chain_id;
    }

    public Date getDate() {
        return date;
    }

    public void setChain_id(int chain_id) {
        this.chain_id = chain_id;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void setChain(String chain) {
        this.chain = chain;
    }

}
