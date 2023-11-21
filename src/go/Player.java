package go;

public class Player {
    private Stones stoneColor;
    private int score;

    public Player(Stones c){
        this.stoneColor = c;
        this.score = 0;
    }

    public void resetScore(){
        this.score = 0;
    }
}
