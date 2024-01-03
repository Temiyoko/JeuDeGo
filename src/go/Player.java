package go;

import java.util.List;
import java.util.ArrayList;

public class Player {
    private final Stones stoneColor;
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
        return moveHistory.isEmpty() ? " " : moveHistory.get(moveHistory.size() - 1);
    }

    public void reset() {
        score = 0;
        moveHistory = new ArrayList<>();
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
        board.setStone(coord, this.stoneColor);

        setScore(getScore() + board.captureStones(coord));

        System.out.println("=" + id);
        return true;
    }

    private static boolean isSuicide(int[] position, Goban board) {
        return board.getLiberties(position) == 0;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        String symbole = stoneColor == Stones.WHITE ? " (0) " : " (X) ";
        sb.append(stoneColor).append(symbole).append("has captured ").append(score).append(" stones");
        return sb.toString();
    }
}
