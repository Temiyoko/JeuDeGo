package go;

import java.util.List;
import java.util.ArrayList;

public class Player {
    private Stones stoneColor;
    private int score;
    private List<String> moveHistory;

    public Player(Stones c){
        this.stoneColor = c;
        this.score = 0;
        this.moveHistory = new ArrayList<>();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addMove(String move) {
        moveHistory.add(move);
    }

    public String getLastMove(){
        return moveHistory.isEmpty() ? null : moveHistory.get(moveHistory.size() - 1);
    }

    public void reset() {
        score = 0;
        moveHistory = new ArrayList<>();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        String symbole = stoneColor == Stones.WHITE ? " (0) " : " (X) ";
        sb.append(stoneColor).append(symbole).append("has captured ").append(score).append(" stones");
        return sb.toString();
    }
}
