package com.vimi.model;

/**
 * Created by vymi1016 on 10/18/2017.
 */
public class Domino implements Cloneable {
    private  int firstSide;
    private  int secondSide;
    private  boolean flip;
    
    public boolean isFlip() {
        return flip;
    }

    public Domino flip() {
        flip = !flip;
        return this;
    }
    
    public Domino(int firstSide, int secondSide) {
        this.firstSide = firstSide;
        this.secondSide = secondSide;
    }

    public int getFirstSide()  {
        return flip ? secondSide : firstSide;
    }        
    public int getSecondSide() {
        return flip ? firstSide : secondSide;
    }    
    
    public String toString() {
        return "|" + getFirstSide() + "|" + getSecondSide() + "|";
    } 
    
    @Override
    public int hashCode(){
        return 31 * super.hashCode() +  getFirstSide() * 8 + getSecondSide()*3;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Domino otherDomino = (Domino)obj;
        if (isFlip() == otherDomino.isFlip()) {
            if (getFirstSide() != otherDomino.getFirstSide() || getSecondSide() != otherDomino.getSecondSide()) {
                return false;
            }
        } else {
            if (getFirstSide() != otherDomino.getSecondSide() || getSecondSide() != otherDomino.getFirstSide()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public Domino clone()  {
        try {
            return (Domino) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
