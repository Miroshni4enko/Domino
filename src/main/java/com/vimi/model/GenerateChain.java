package com.vimi.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by vymi1016 on 10/22/2017.
 */
public class GenerateChain extends RecursiveTask<Chain> {
    private LinkedBlockingDeque<Domino> dominoesPool;
    private Chain resultChain;
    private LinkedBlockingDeque<Chain> resultChains;

    public GenerateChain(LinkedBlockingDeque<Domino> dominoesPool, Chain resultChain, LinkedBlockingDeque<Chain> resultChains) {
        this.dominoesPool = dominoesPool;
        this.resultChain = resultChain;
        this.resultChains = resultChains;
    }

    @Override
    protected Chain compute() {
        List<GenerateChain> subTasks = new LinkedList<>();
        for (Domino domino : dominoesPool) {
            if (resultChain.addToChain(domino.clone())) {
                LinkedBlockingDeque<Domino> dominoesPoolWithOutCurrent = new LinkedBlockingDeque<>(dominoesPool);
                dominoesPoolWithOutCurrent.remove(domino);
                GenerateChain task = new GenerateChain(dominoesPoolWithOutCurrent, new Chain(resultChain), resultChains);
                task.fork();
                subTasks.add(task);
                resultChain.remove(domino);
                //generateChains(dominoesPoolWithOutCurrent, resultChain, resultChains);
            }
        }
        for (GenerateChain task : subTasks) {
            Chain newChain = task.join();
            ReentrantLock rentad = new ReentrantLock();
            rentad.lock();
            if (!isRepeatedChain(resultChains, newChain)) {
                resultChains.add(new Chain(newChain));
            }
            rentad.unlock();
        }
        return resultChain;
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
