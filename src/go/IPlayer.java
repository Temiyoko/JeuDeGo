package go;

public interface IPlayer {
    boolean play(int[] coord, Goban board);
    void reset();
}
