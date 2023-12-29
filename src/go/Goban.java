package go;

import java.util.ArrayList;
import java.util.List;

public class Goban {
    private static final int MINSIZE = 1, MAXSIZE = 19;
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

    public boolean isInBoard(int[] tab){ // Ex : "P9"
        return tab[0] >= 0 && tab[0] < size && tab[1] >= 0 && tab[1] < size;
    }

    public List<int[]> getAdjacentPositions(int[] position) {
        List<int[]> adjacentPositions = new ArrayList<>();

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newX = position[0] + dir[0];
            int newY = position[1] + dir[1];
            if (isInBoard(new int[]{newX, newY})) {
                adjacentPositions.add(new int[]{newX, newY});
            }
        }

        return adjacentPositions;
    }

    public int getLiberties(int[] position) {
        int liberties = 0;

        for (int[] adjPos : getAdjacentPositions(position)) {
            if (isInBoard(adjPos) && stones[adjPos[1]][adjPos[0]] == null) {
                liberties++;
            }
        }
        return liberties;
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
        return stones[tab[1]][tab[0]] == null;
    }

    private void letters(StringBuilder sb) {
        for (int i = 0; i < size; ++i) {
            char c = (char)('A' + i);
            sb.append(c >= 'I' ? (char)(c + 1) : c).append(" ");
        }
    }

    public void setGoban(int[] tab, Stones s) {
        stones[tab[1]][tab[0]] = s;
    }
}
