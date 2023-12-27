package go;

import java.util.ArrayList;
import java.util.List;

public class Goban {
    private static final int MINSIZE = 1, MAXSIZE = 19; // Dans le Goban ?
    private int size;
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

    public Stones getStone(int[] pos) {
        return stones[pos[0]][pos[1]];
    }

    public boolean isInBoard(int[] tab){ // Ex : "P9"
        return tab[0] >= 0 && tab[0] < size && tab[1] >= 0 && tab[1] < size;
    }

    public int getLiberties(int[] position) {
        int liberties = 0;

        for (int[] adjPos : getAdjacentPositions(position)) {
            if (stones[adjPos[0]][adjPos[1]] == null) {
                liberties++;
            }
        }
        return liberties;
    }

    public List<int[]> getAdjacentPositions(int[] position) {
        int x = position[0];
        int y = position[1];
        List<int[]> adjacentPositions = new ArrayList<>();

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (isInBoard(new int[]{newX, newY})) {
                adjacentPositions.add(new int[]{newX, newY});
            }
        }

        return adjacentPositions;
    }

    public void setStones(int[] tab, Stones s) {
        stones[tab[0]][tab[1]] = s;
    }

    public String show(Player p1, Player p2) {
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

    public boolean isPlayable(int[] tab){
        return stones[tab[0]][tab[1]] == null;
    }

    private void letters(StringBuilder sb) {
        for (int i = 0; i < size; ++i) {
            char c = (char) ('A' + i);
            sb.append(c >= 'I' ? (char) (c + 1) : c).append(" ");
        }
    }
}
