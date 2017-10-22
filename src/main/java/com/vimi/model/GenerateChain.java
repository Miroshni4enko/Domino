package com.vimi.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RecursiveTask;

/**
 * Created by vymi1016 on 10/22/2017.
 */
public class GenerateChain extends RecursiveTask<Void> {
    private List<Domino> dominoesPool;
    private Chain resultChain;
    private LinkedBlockingDeque<Chain> resultChains;
    
    public GenerateChain (LinkedList<Domino> dominoesPool, Chain resultChain, LinkedBlockingDeque<Chain> resultChains) {
        this.dominoesPool = dominoesPool;
        this.resultChain = resultChain;
        this.resultChains = resultChains;
    }
    @Override
    protected Void compute() {
        List<GenerateChain> subTasks = new LinkedList<>();
        Chain resultChainWithPassedDomino;
        for (Domino domino : dominoesPool) {

            try {
                if (resultChain.addToChain(domino.clone())) {
                    LinkedList<Domino> dominoesPoolWithOutCurrent = new LinkedList<>(dominoesPool);
                    dominoesPoolWithOutCurrent.remove(domino);
                    if (!isRepeatedChain(resultChains, resultChain)) {
                        resultChainWithPassedDomino = new Chain(resultChain);
                        resultChains.add(resultChainWithPassedDomino);
                    }
                    GenerateChain task = new GenerateChain(dominoesPoolWithOutCurrent, resultChain, resultChains);
                    task.fork();
                    subTasks.add(task);
                    //generateChains(dominoesPoolWithOutCurrent, resultChain, resultChains);
                    resultChain.remove(domino);
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        for(GenerateChain task : subTasks) {
            task.join(); // дождёмся выполнения задачи и прибавим результат 
        }
        return null;
    }

    public boolean isRepeatedChain(LinkedBlockingDeque<Chain> childChains, Chain parentChain) {
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
}
