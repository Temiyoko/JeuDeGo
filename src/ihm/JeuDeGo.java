package ihm;

import go.Colors;
import go.Goban;
import go.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class JeuDeGo {
    private static final int MINSIZE = 1, MAXSIZE = 19;
    private static Goban goban;
    private static Player blackP, whiteP;
    private static Map<Player, List<String>> history;

    public static void main(String[] args) {
        goban = new Goban();
        blackP = new Player(Colors.BLACK);
        whiteP = new Player(Colors.WHITE);
        history = new HashMap<>();

        Scanner sc = new Scanner(System.in);

        while(sc.hasNextLine()) {
            String saisie = sc.nextLine().trim();

            if (saisie.equals("quit")){
                System.out.println("=");
                break;
            }
            else if (saisie.startsWith("boardsize")) {
                boardsize(saisie);
            }
            else{
                System.out.println("? unknown command");
            }
        }
        sc.close();
    }

    private static void boardsize(String input) {
        try {
            String[] words = input.split("\\s+");
            int nb = Integer.parseInt(words[1]);

            if(nb < MINSIZE || nb > MAXSIZE){
                System.out.println("? unacceptable size");
                return;
            }

            resetGame(nb);
            System.out.println("=");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("? boardsize not an integer");
        }
        catch (NumberFormatException e) {
            System.out.println("? unknown command");
        }
    }

    private static void resetGame(int nb) {
        assert nb >= MINSIZE && nb <= MAXSIZE;

        goban = new Goban(nb); // The board size is changed and attributes are reset
        // The count of stones captured by each player will be reset to an arbitrary state.
        blackP.resetScore();
        whiteP.resetScore();
        // The history of moves made in the game will be reset to an arbitrary state.
        history = new HashMap<>();
    }

}
