package ihm;

import go.Goban;

public interface IPlayer {
    boolean play(String move, String id, int[] coord, Goban board);
    void reset();
}
