package go;

public class Player {
    private Colors color;
    private int score;

    public Player(Colors c){
        this.color = c;
        this.score = 0;
    }

    public void resetScore(){
        this.score = 0;
    }
}
