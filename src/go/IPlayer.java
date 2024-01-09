package go;

import go.Goban;

public interface IPlayer {
    boolean play(int[] coord, Goban board);
    void reset();
}
