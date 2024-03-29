package go;

import java.util.*;

public class Goban {
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int MINSIZE = 1, MAXSIZE = 19;
    private final int size;
    private Stones[][] stones;
    private Map<IPlayer, List<int[]>> history;

    public Goban(IPlayer b, IPlayer w){
        this(MAXSIZE, b, w);
    }

    public Goban(int n, IPlayer b, IPlayer w){
        assert n >= MINSIZE && n <= MAXSIZE;
        this.size = n;
        this.stones = new Stones[n][n];
        this.history = new HashMap<>(Map.of(b, new ArrayList<>(), w, new ArrayList<>()));
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

    public Stones getStone(int[] position) {
        return stones[position[1]][position[0]];
    }

    public void setStone(int[] position, Stones s) {
        assert isInBoard(position);
        stones[position[1]][position[0]] = s;
    }

    public void addMove(IPlayer p, int[] move){
        history.get(p).add(move);
    }

    public boolean isInBoard(int[] tab){
        return tab[0] >= 0 && tab[0] < size && tab[1] >= 0 && tab[1] < size;
    }

    public boolean isPlayable(int[] tab){
        return getStone(tab) == null;
    }

    public boolean isSuicide(int[] position, Stones color) {
        setStone(position, color);
        boolean suicide = ((getLiberties(position, color) == 0) && (countCaptureStones(position, color) == 0));
        setStone(position, null);
        return suicide;
    }

    public int getLiberties(int[] position, Stones color) {
        if (!isInBoard(position)) {
            return 0;
        }
        return countLiberties(position, color, new boolean[size][size]);
    }

    private int countLiberties(int[] position, Stones color, boolean[][] visited) {
        if (!isInBoard(position) || visited[position[1]][position[0]]) {
            return 0;
        }
        visited[position[1]][position[0]] = true;

        if (getStone(position) == null) {
            return 1;
        }
        else if (getStone(position) != color) {
            return 0;
        }
        int liberties = 0;

        for (int[] dir : DIRECTIONS) {
            int[] newPos = new int[]{position[0] + dir[0], position[1] + dir[1]};

            if (isInBoard(newPos)){
                liberties += countLiberties(newPos, color, visited);
            }
        }

        return liberties;
    }

    public int countCaptureStones(int[] position, Stones color){
        return stonesToCapture(position, color).size();
    }

    public List<int[]> stonesToCapture(int[] position, Stones color) {

        Stones advColor = color == Stones.BLACK ? Stones.WHITE : Stones.BLACK;
        List<int[]> stonesToRemove = new ArrayList<>();
        boolean[][] visited = new boolean[size][size];

        for (int[] dir : DIRECTIONS) {
            int[] adjacentPosition = new int[]{position[0] + dir[0], position[1] + dir[1]};
            if (isInBoard(adjacentPosition) && getStone(adjacentPosition) == advColor
                    && !visited[adjacentPosition[1]][adjacentPosition[0]]) {
                List<int[]> group = new ArrayList<>();
                captureGroup(adjacentPosition, advColor, group, visited);
                if (getLiberties(adjacentPosition, advColor) == 0) {
                    stonesToRemove.addAll(group);
                }
            }
        }

        return stonesToRemove;
    }

    public void captureStones(int[] position, Stones color) {
        for (int[] pos : stonesToCapture(position, color)) {
            setStone(pos, null);
        }
    }

    private void captureGroup(int[] position, Stones color, List<int[]> group, boolean[][] visited) {
        if (!isInBoard(position) || visited[position[1]][position[0]] || getStone(position) != color) {
            return;
        }

        visited[position[1]][position[0]] = true;
        group.add(position);

        for (int[] dir : DIRECTIONS) {
            int[] adjacentPosition = new int[]{position[0] + dir[0], position[1] + dir[1]};
            captureGroup(adjacentPosition, color, group, visited);
        }
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
