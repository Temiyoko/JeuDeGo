package go;

public class Player {
    private Stones stoneColor;
    private int score;

    public Player(Stones c){
        this.stoneColor = c;
        this.score = 0;
    }

    public void resetScore() {
        this.score = 0;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        String symbole = stoneColor == Stones.WHITE ? " (0) " : " (X) ";
        sb.append(stoneColor).append(symbole).append("has captured ").append(score).append(" stones");
        return sb.toString();
    }
}
