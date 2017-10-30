package com.vimi.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vimi on 10/18/2017.
 */
public class DominoService {
    
    public static List<Chain> generateChains(List<Domino> dominoesPool) {
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

    private static void generateChains(List<Domino> dominoesPool, Chain resultChain, List<Chain> resultChains) {
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
    public static boolean isRepeatedChain(List<Chain> childChains, Chain parentChain) {
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