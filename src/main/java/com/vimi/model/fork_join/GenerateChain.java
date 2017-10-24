package com.vimi.model.fork_join;

import com.vimi.model.Chain;
import com.vimi.model.Domino;

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
        Chain chain = null;
        List<GenerateChain> subTasks = new LinkedList<>();
        for (Domino domino : dominoesPool) {
            if (resultChain.addToChain(domino.clone())) {
                LinkedBlockingDeque<Domino> dominoesPoolWithOutCurrent = new LinkedBlockingDeque<>(dominoesPool);
                dominoesPoolWithOutCurrent.remove(domino);
                chain = new Chain(resultChain);
                if(dominoesPool.size()>5) {
                    GenerateChain task = new GenerateChain(dominoesPoolWithOutCurrent, chain, resultChains);
                    task.fork();
                    subTasks.add(task);

                }
                else{
                    generateChains(dominoesPoolWithOutCurrent, chain, resultChains);
                    //return resultChain;
                }
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
        return chain;
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
    public void generateChains(LinkedBlockingDeque<Domino> dominoesPool, Chain resultChain, LinkedBlockingDeque<Chain> resultChains){
        Chain resultChainWithPassedDomino;
        for (Domino domino : dominoesPool) {

            if (resultChain.addToChain(domino.clone())) {
                LinkedBlockingDeque<Domino> dominoesPoolWithOutCurrent = new LinkedBlockingDeque<>(dominoesPool);
                dominoesPoolWithOutCurrent.remove(domino);

                generateChains(dominoesPoolWithOutCurrent, resultChain, resultChains);
                ReentrantLock reentLock = new ReentrantLock();
                reentLock.lock();
                if (!isRepeatedChain(resultChains, resultChain)) {
                    resultChainWithPassedDomino = new Chain(resultChain);
                    resultChains.add(resultChainWithPassedDomino);
                }
                reentLock.unlock();
                resultChain.remove(domino);
            }
        }
        ReentrantLock reentLock = new ReentrantLock();
        reentLock.lock();
        if (!isRepeatedChain(resultChains, resultChain)) {
            resultChains.add(new Chain(resultChain));
        }
        reentLock.unlock();
    }

}
