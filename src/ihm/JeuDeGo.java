package ihm;

import Go.Goban;

import java.util.Scanner;

public class JeuDeGo {
    private static final int MINSIZE = 1, MAXSIZE = 19;
    private static Goban goban;
    public static void main(String[] args) {
        goban = new Goban();
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

            goban = new Goban(nb); // The board size is changed and attributes are reset
            // The count of stones captured by each player will be reset to an arbitrary state.
            // The history of moves made in the game will be reset to an arbitrary state.
            System.out.println("=");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("? boardsize not an integer");
        }
        catch (NumberFormatException e) {
            System.out.println("? unknown command");
        }
    }

}
