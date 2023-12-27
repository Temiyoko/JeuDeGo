package go;

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

    public boolean isInBoard(String s){ // Ex : "P9"
        char c = s.toUpperCase().charAt(0); // 'P'
        if (c == 'I'){
            return false;
        }
        int col = c > 'I' ? (int)c - 'A' - 1 : (int)c - 'A'; // Détermine la colonne associée à la lettre entrée
        int line = Integer.parseInt(s.substring(1)) - 1; // Récupère la ligne entrée
        // Vérifie que la colonne et la ligne sont dans les bornes du plateau
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
