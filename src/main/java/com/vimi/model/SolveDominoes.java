package com.vimi.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vymi1016 on 10/18/2017.
 */
public class SolveDominoes{
    
   
    
    public static void main(String[] args) throws CloneNotSupportedException {
        // [(1|4), (2|6), (4|5), (1|5), (3|5)]
        SolveDominoes solveDominoes = new SolveDominoes();
        List<Chain> demoForDraw = null;
        long start = System.currentTimeMillis();
        List<Domino> pool = new LinkedList<>();
        pool.add(new Domino(2,6));
        pool.add(new Domino(0,1));
        pool.add(new Domino(3,5));
        pool.add(new Domino(4,6));
        pool.add(new Domino(2,3));
        pool.add(new Domino(2,4));
        pool.add(new Domino(3,4));
        pool.add(new Domino(1,3));
        pool.add(new Domino(0,2));
        pool.add(new Domino(4,5));
        pool.add(new Domino(1,4));/*
        pool.add(new Domino(0,0));*/
        // new DominoPool().getDominoesWithFixedSize(5);
        System.out.println(pool);
       /* for(int i = 0; i < 1000; i++) {
            demoForDraw = solveDominoes.generateChains(pool);
            if(demoForDraw.size()!=16) {
                System.out.println(demoForDraw.size());
            }
        }*/
      
        demoForDraw = solveDominoes.generateChains(pool);
        
      System.out.println(drawChains(demoForDraw));
      System.out.println(demoForDraw.size());
        
            
        
        /*System.out.println(hasChain(dominoes, 5, 5));   // true
        System.out.println(hasChain(dominoes, 1, 5));   // true
        System.out.println(hasChain(dominoes, 1, 3));   // true
        System.out.println(hasChain(dominoes, 1, 6));   // false
        System.out.println(hasChain(dominoes, 1, 2));   // false*/
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
    }



    public List<Chain> generateChains(List<Domino> dominoesPool) throws CloneNotSupportedException {
        List<Chain> resultChains = new ArrayList<>();
        Chain resultChain = new Chain();
        for (Domino domino : dominoesPool) {
            resultChain.add(domino);
            List<Domino> dominoesPoolWithOutCurrent = new LinkedList<>(dominoesPool);
            dominoesPoolWithOutCurrent.remove(domino);
            generateChains(dominoesPoolWithOutCurrent, resultChain, resultChains);
            resultChain.remove(domino);
        }
        return resultChains;

    }

    public void generateChains(List<Domino> dominoesPool, Chain resultChain, List<Chain> resultChains) throws CloneNotSupportedException {
        for (Domino domino : dominoesPool) {
            if (resultChain.addToChain(domino.clone())) {
                LinkedList<Domino> dominoesPoolWithOutCurrent = new LinkedList<>(dominoesPool);
                dominoesPoolWithOutCurrent.remove(domino);

                generateChains(dominoesPoolWithOutCurrent, resultChain, resultChains);

                if (!isRepeatedChain(resultChains, resultChain)) {
                    resultChains.add(new Chain(resultChain));
                }
                resultChain.remove(domino);
            }
        }
    }
    public boolean isRepeatedChain(List<Chain> childChains, Chain parentChain) {
        if (childChains.size() == 0) {
            return false;
        }
        for (Chain childChain : childChains) {
            if (childChain.containsAllDominoesInRightOrder(parentChain)) {
                return true;
            }
        }
        return false;
    }
    
    
    public static String drawChains(List<Chain> chains){
        StringBuilder builder = new StringBuilder();
        for (Chain childChain : chains) {
            builder.append(childChain.drawChain());
        }
        return builder.toString();
    }
    
}