package player;

import go.IPlayer;
import go.Stones;
import go.Goban;

public abstract class Player implements IPlayer {
    private final Stones stoneColor;
    private int score;

    protected Player(Stones stoneColor) {
        this.stoneColor = stoneColor;
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Stones getStoneColor() {
        return stoneColor;
    }

    public void reset() {
        score = 0;
    }

    public boolean isSuicide(int[] position, Goban board) {
        board.setStone(position, getStoneColor());
        boolean suicide = (board.getLiberties(position, stoneColor) == 0) && (board.countCaptureStones(position, getStoneColor()) == 0);
        board.setStone(position, null);
        return suicide;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        String symbole = stoneColor == Stones.WHITE ? " (0) " : " (X) ";
        sb.append(stoneColor).append(symbole).append("has captured ").append(score).append(" stones");
        return sb.toString();
    }
}
