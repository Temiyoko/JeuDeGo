package player;

import go.Stones;
import go.Goban;
import ihm.IPlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements IPlayer {
    private final Stones stoneColor;
    private int score;
    private List<String> moveHistory;

    protected Player(Stones stoneColor) {
        this.stoneColor = stoneColor;
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
        return moveHistory.isEmpty() ? " " : moveHistory.get(moveHistory.size() - 1);
    }

    public Stones getStoneColor() {
        return stoneColor;
    }

    public void reset() {
        score = 0;
        moveHistory = new ArrayList<>();
    }

    public static boolean isSuicide(int[] position, Goban board) {
        return board.getLiberties(position) == 0;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        String symbole = stoneColor == Stones.WHITE ? " (0) " : " (X) ";
        sb.append(stoneColor).append(symbole).append("has captured ").append(score).append(" stones");
        return sb.toString();
    }
}
