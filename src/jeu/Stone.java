package jeu;


public class Stone {
    private Colors color;
    private int[] position;

    public Stone(Colors c){
        this.color = c;
        this.position = new int[]{-1, -1};
    }

    public Stone(Colors c, int[] p){
        this.color = c;
        this.position = p;
    }
}
