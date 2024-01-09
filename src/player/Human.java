package player;

import go.Stones;
import go.Goban;

import java.util.Arrays;

public class Human extends Player {
    public Human(Stones c){
        super(c);
    }

    public boolean play(int[] coord, Goban board) {
        if (Arrays.equals(coord, new int[]{-1, -1})) {
            board.addMove(this, coord);
            return false;
        }

        if (!board.isInBoard(coord)) {
            throw new IllegalArgumentException();
        }
        else if (!board.isPlayable(coord) || board.isSuicide(coord, getStoneColor())) {
            throw new RuntimeException();
        }

        board.addMove(this, coord);
        board.setStone(coord, getStoneColor());

        setScore(getScore() + board.countCaptureStones(coord, getStoneColor()));
        board.captureStones(coord, getStoneColor());

        return true;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        String symbole = getStoneColor() == Stones.WHITE ? " (0) " : " (X) ";
        sb.append(getStoneColor()).append(symbole).append("has captured ").append(getScore()).append(" stones");
        return sb.toString();
    }
}
