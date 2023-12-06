package go;

public class Goban {
    private static final int DEFAULT_SIZE = 19;
    private int size;
    private Stones[][] stones;

    public Goban(){
        this.size = DEFAULT_SIZE;
        this.stones = new Stones[DEFAULT_SIZE][DEFAULT_SIZE];
    }

    public Goban(int n){
        this.size = n;
        this.stones = new Stones[n][n];
    }

    public int getSize(){
        return size;
    }

    public boolean isInBoard(String s){
        char c = s.toUpperCase().charAt(0);
        if (c == 'I'){
            return false;
        }
        int col = c > 'I' ? (int)c - 'A' - 1 : (int)c - 'A';
        int line = Integer.parseInt(s.substring(1)) - 1;
        return col >= 0 && col < size && line >= 0 && line < size;
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

    private void letters(StringBuilder sb) {
        for (int i = 0; i < size; ++i) {
            char c = (char)('A' + i);
            sb.append(c >= 'I' ? (char)(c + 1) : c).append(" ");
        }
    }
}
