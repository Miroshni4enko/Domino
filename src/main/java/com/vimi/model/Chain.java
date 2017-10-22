package com.vimi.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by vymi1016 on 10/20/2017.
 */
public class Chain extends LinkedBlockingDeque<Domino> {

    public Chain() {
    }

    public Chain(Collection<? extends Domino> chain) {
        super(chain);
    }

    public int getFirstSideOfChain() {
        return getFirst().getFirstSide();
    }

    public int getSecondSideOfChain() {
        return getLast().getSecondSide();
    }

    public  boolean addToChain(Domino domino) {
        if (domino.getFirstSide() == getSecondSideOfChain()) {
            return add(domino);
        } else if (domino.getSecondSide() == getFirstSideOfChain()) {
            addFirst(domino);
            return true;
        }
        return !domino.isFlip() && addToChain(domino.flip());
    }

    public boolean containsAllDominoesInRightOrder(Chain anotherChain) {
        Domino firstElementOfAnotherChain = anotherChain.getFirst();
        Domino lastElementOfAnotherChain = anotherChain.getLast();
        Iterator<Domino> thisIterator = iterator();
        while (thisIterator.hasNext()) {
            Domino indexDomino = thisIterator.next();
            if (indexDomino.equals(firstElementOfAnotherChain)) {
                return containAllDominoes(thisIterator, anotherChain.iterator());
            }
            if (indexDomino.equals(lastElementOfAnotherChain)) {
                return containAllDominoes(thisIterator, anotherChain.descendingIterator());
            }
        }
        return false;
    }
    
    private boolean containAllDominoes(Iterator<Domino> dominoIterator, Iterator<Domino> antoherChainDominoIterator){
        antoherChainDominoIterator.next();
        while (dominoIterator.hasNext() && antoherChainDominoIterator.hasNext()){
            if (!dominoIterator.next().equals(antoherChainDominoIterator.next())){
                return false;
            }
        }
        if (antoherChainDominoIterator.hasNext()){
            return false;
        }
        return true;
    }
    
    public StringBuilder drawChain() {
        StringBuilder builder = new StringBuilder();
        drawBorder( " ___ ", builder);
        for (Domino domino : this) {
                builder.append("|").append(domino.getFirstSide()).append("|").append(domino.getSecondSide()).append("|");
        }
        builder.append("\n");
        drawBorder( " ¯¯¯ ", builder);
        return builder;
    }
    
    private void drawBorder(String border, StringBuilder builder){
        for (int i = 0; i < size(); i++) {
            builder.append(border);
        }
        builder.append("\n");
    }
    
}
