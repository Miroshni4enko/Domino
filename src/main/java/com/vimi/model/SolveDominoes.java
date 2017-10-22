package com.vimi.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by vymi1016 on 10/18/2017.
 */
public class SolveDominoes{
    
   
    
    public static void main(String[] args) throws CloneNotSupportedException {
        // [(1|4), (2|6), (4|5), (1|5), (3|5)]

        LinkedBlockingDeque<Chain> demoForDraw;
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
        pool.add(new Domino(1,4));
        pool.add(new Domino(0,0));
        // new DominoPool().getDominoesWithFixedSize(5);
        System.out.println(pool);
        demoForDraw = generateChains(pool);
        
        //System.out.println(drawChains(demoForDraw));
        
        System.out.println(demoForDraw.size());
            
        
        /*System.out.println(hasChain(dominoes, 5, 5));   // true
        System.out.println(hasChain(dominoes, 1, 5));   // true
        System.out.println(hasChain(dominoes, 1, 3));   // true
        System.out.println(hasChain(dominoes, 1, 6));   // false
        System.out.println(hasChain(dominoes, 1, 2));   // false*/
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
    }

  

    /* public static boolean hasChain(List<Domino> dominoes,
                                    int start, int end) {
         if (start == end) {
             return true;                         // base case
         } else {
             for (int i = 0; i < dominoes.size(); i++) {
                 Domino d = dominoes.remove(i);   // choose
                 if (d.getFirstSide() == start) {        // explore
                     if (hasChain(dominoes, d.getSecondSide(), end)) {
                         return true;
                     }
                 } else if (d.getSecondSide() == start) {
                     if (hasChain(dominoes, d.getFirstSide(), end)) {
                         return true;
                     }
                 }
                 dominoes.add(i, d);              // un-choose
             }
             return false;
         }
     }
 */

    public static LinkedBlockingDeque<Chain> generateChains(List<Domino> dominoesPool) throws CloneNotSupportedException {
        LinkedBlockingDeque<Chain> resultChains = new LinkedBlockingDeque<>();
        Chain resultChain;
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        for (Domino domino : dominoesPool) {
            resultChain = new Chain();
            resultChain.add(domino);
            LinkedBlockingDeque<Domino> dominoesPoolWithOutCurrent = new LinkedBlockingDeque<>(dominoesPool);
            dominoesPoolWithOutCurrent.remove(domino);
            forkJoinPool.invoke(new GenerateChain(dominoesPoolWithOutCurrent, resultChain, resultChains));
            //generateChains(dominoesPoolWithOutCurrent, resultChain, resultChains);
        }
        return resultChains;

    }

/*    public static void generateChains(LinkedList<Domino> dominoesPool, Chain resultChain, List<Chain> resultChains) throws CloneNotSupportedException {
        Chain resultChainWithPassedDomino;
        for (Domino domino : dominoesPool) {

            if (resultChain.addToChain(domino.clone())) {
                LinkedList<Domino> dominoesPoolWithOutCurrent = new LinkedList<>(dominoesPool);
                dominoesPoolWithOutCurrent.remove(domino);

                generateChains(dominoesPoolWithOutCurrent, resultChain, resultChains);

                if (!isRepeatedChain(resultChains, resultChain)) {
                    resultChainWithPassedDomino = new Chain(resultChain);
                    resultChains.add(resultChainWithPassedDomino);
                }
                resultChain.remove(domino);
            }
        }
    }*/
    
    
    public static String drawChains(LinkedBlockingDeque<Chain> chains){
        StringBuilder builder = new StringBuilder();
        for (Chain childChain : chains) {
            builder.append(childChain.drawChain());
        }
        return builder.toString();
    }
    
}