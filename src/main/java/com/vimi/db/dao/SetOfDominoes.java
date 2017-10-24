package com.vimi.db.dao;

/**
 * Created by vymi1016 on 10/24/2017.
 */
public class SetOfDominoes {
    private int id;
    private int chain_id;
    private String set;

    public SetOfDominoes(int id, int chain_id, String set) {
        this.id = id;
        this.chain_id = chain_id;
        this.set = set;
    }

    public SetOfDominoes(int chain_id, String set) {
        this.chain_id = chain_id;
        this.set = set;
    }

    public int getId() {
        return id;
    }

    public String getSet() {
        return set;
    }

    public int getChain_id() {
        return chain_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public void setChain_id(int chain_id) {
        this.chain_id = chain_id;
    }
    
    
}
