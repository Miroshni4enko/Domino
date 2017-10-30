package com.vimi.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by vymi1016 on 10/22/2017.
 */
public class DominoPool {
   static private List<Domino>  dominoes;
    static {
        dominoes = new LinkedList<>();
        dominoes.add(new Domino(0, 0));
        dominoes.add(new Domino(0, 1));
        dominoes.add(new Domino(0, 2));
        dominoes.add(new Domino(0, 3));
        dominoes.add(new Domino(0, 4));
        dominoes.add(new Domino(0, 5));
        dominoes.add(new Domino(0, 6));
        dominoes.add(new Domino(1, 1));
        dominoes.add(new Domino(1, 2));
        dominoes.add(new Domino(1, 3));
        dominoes.add(new Domino(1, 4));
        dominoes.add(new Domino(1, 5));
        dominoes.add(new Domino(1, 6));
        dominoes.add(new Domino(2, 2));
        dominoes.add(new Domino(2, 3));
        dominoes.add(new Domino(2, 4));
        dominoes.add(new Domino(2, 5));
        dominoes.add(new Domino(2, 6));
        dominoes.add(new Domino(3, 3));
        dominoes.add(new Domino(3, 4));
        dominoes.add(new Domino(3, 5));
        dominoes.add(new Domino(3, 6));
        dominoes.add(new Domino(4, 4));
        dominoes.add(new Domino(4, 5));
        dominoes.add(new Domino(4, 6));
        dominoes.add(new Domino(5, 5));
        dominoes.add(new Domino(5, 6));
        dominoes.add(new Domino(6, 6));
    }
    
    public static List<Domino> getDominoesWithRandomSize(){
        Collections.shuffle(dominoes);
        return dominoes.subList(0,new Random().nextInt(12));   
    }
    
    public static List<Domino> getDominoesWithFixedSize(int size){
        Collections.shuffle(dominoes);
        //TODO if size  more then 12, it will be done, but take lots of time
        if(size > 12) {
            size = 12;    
        }
        return dominoes.subList(0,size);
    }
    
}
