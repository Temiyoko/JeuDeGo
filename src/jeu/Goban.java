package jeu;

public class Goban {
    private static final int DEFAULT_SIZE = 19;
    private int size;
    private Stone[] stones;

    public Goban(){
        this.size = DEFAULT_SIZE;
        this.stones = new Stone[DEFAULT_SIZE * DEFAULT_SIZE];
    }
}
