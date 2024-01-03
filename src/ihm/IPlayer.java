package ihm;

import go.Goban;

public interface IPlayer {
    boolean play(String move, String id, int[] coord, Goban board);
    public void addMove(String move);

    public int getScore();

    public void setScore(int score);

    public void reset();

    public String getLastMove();
}
