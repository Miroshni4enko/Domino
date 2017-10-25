package com.vimi.db.dao;

import java.util.Date;

/**
 * Created by vymi1016 on 10/25/2017.
 */
public class HistoryObject {
    private Date date;
    private String chain;
    private String set;

    public HistoryObject(Date date, String chain, String set) {
        this.date = date;
        this.chain = chain;
        this.set = set;
    }

    public Date getDate() {
        return date;
    }

    public String getChain() {
        return chain;
    }

    public String getSet() {
        return set;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public void setSet(String set) {
        this.set = set;
    }
}
