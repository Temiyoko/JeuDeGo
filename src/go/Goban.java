package go;

import ihm.IPlayer;

import java.util.ArrayList;
import java.util.List;

public class Goban {
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int MINSIZE = 1, MAXSIZE = 19;
    private final int size;
    private Stones[][] stones;

    public Goban(){
        this.size = MAXSIZE;
        this.stones = new Stones[MAXSIZE][MAXSIZE];
    }

    public Goban(int n){
        assert n >= MINSIZE && n <= MAXSIZE;
        this.size = n;
        this.stones = new Stones[n][n];
    }

    public int getSize(){
        return size;
    }

    public int getMinSize(){
        return MINSIZE;
    }

    public int getMaxSize(){
        return MAXSIZE;
    }

    private Stones getStone(int[] position) {
        return stones[position[1]][position[0]];
    }

    public void setStone(int[] position, Stones s) {
        assert isInBoard(position);
        stones[position[1]][position[0]] = s;
    }

    public boolean isInBoard(int[] tab){
        return tab[0] >= 0 && tab[0] < size && tab[1] >= 0 && tab[1] < size;
    }

    public boolean isPlayable(int[] tab){
        return stones[tab[1]][tab[0]] == null;
    }

    public int getLiberties(int[] position, Stones color) {
        if (!isInBoard(position)) {
            return 0;
        }

        setStone(position, color);
        int liberties = countLiberties(position, color, new boolean[size][size]);
        setStone(position, null);

        return liberties;
    }

    private int countLiberties(int[] position, Stones color, boolean[][] visited) {
        if (!isInBoard(position)|| visited[position[1]][position[0]]) {
            return 0;
        }

        if (getStone(position) == null) {
            return 1;
        }

        if (getStone(position) != color) {
            return 0;
        }

        visited[position[1]][position[0]] = true;
        int liberties = 0;

        for (int[] dir : DIRECTIONS) {
            liberties += countLiberties(new int[]{position[0] + dir[0], position[1] + dir[1]}, color, visited);
        }

        return liberties;
    }

    public int captureStones(int[] position, Stones color){
        int cpt = 0;
        Stones advColor = color == Stones.BLACK ? Stones.WHITE : Stones.BLACK;
        for(int[] pos : stonesToCatch(position, advColor)){
            setStone(pos, null);
            cpt++;
        }
        return cpt;
    }

    private List<int[]> stonesToCatch(int[] position, Stones color) {
        List<int[]> zeroLiberties = new ArrayList<>();

        for (int[] dir : DIRECTIONS) {
            int[] adjacentPosition = new int[]{position[0] + dir[0], position[1] + dir[1]};
            if (isInBoard(adjacentPosition) && getStone(adjacentPosition) == color && getLiberties(adjacentPosition, color) == 0) {
                zeroLiberties.add(adjacentPosition);
            }
        }
        System.out.println(zeroLiberties);
        return zeroLiberties;
    }


    public String show(IPlayer p1, IPlayer p2) {
        StringBuilder sb = new StringBuilder("   ");
        int cpt = size;

        letters(sb);
        sb.append(System.lineSeparator());

        for (int i = size - 1 ; i >= 0 ; --i){
            sb.append(cpt < 10 ? " " + cpt : cpt).append(" ");

            for (int j = 0 ; j < size ; ++j){
                Stones st = stones[i][j];
                sb.append(st == null ? ". " : st == Stones.BLACK ? "X " : "O ");
            }

            sb.append(cpt < 10 ? " " + cpt : cpt);
            sb.append(cpt == 2 ? "     " + p2.toString() : cpt == 1 ? "     " + p1.toString() : "");

            sb.append(System.lineSeparator());
            --cpt;
        }

        sb.append("   ");
        letters(sb);

        return sb.toString();
    }

    private void letters(StringBuilder sb) {
        for (int i = 0; i < size; ++i) {
            char c = (char)('A' + i);
            sb.append(c >= 'I' ? (char)(c + 1) : c).append(" ");
        }
    }
}
