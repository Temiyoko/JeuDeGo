package player;

import go.Stones;
import go.Goban;

public class Human extends Player {
    public Human(Stones c){
        super(c);
    }
    public boolean play(String move, String id, int[] coord, Goban board) {
        if (move.equalsIgnoreCase("pass")) {

            if (getLastMove().equalsIgnoreCase("pass")){
                System.out.println("=" + id + " resigns");
                return false;
            }

            addMove(move);
            System.out.println("=" + id);
            return true;
        }

        if (!board.isInBoard(coord)) {
            throw new IllegalArgumentException();
        }
        else if (!board.isPlayable(coord) || isSuicide(coord, board)) {
            throw new RuntimeException();
        }

        addMove(move);
        board.setStone(coord, getStoneColor());

        setScore(getScore() + board.captureStones(coord));

        System.out.println("=" + id);
        return true;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        String symbole = getStoneColor() == Stones.WHITE ? " (0) " : " (X) ";
        sb.append(getStoneColor()).append(symbole).append("has captured ").append(getScore()).append(" stones");
        return sb.toString();
    }
}
