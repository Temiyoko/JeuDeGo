package jeu;

import java.util.Scanner;

public class JeuDeGo {
    private static final int MINSIZE = 1, MAXSIZE = 19;
    public static void main(String[] args) {
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
            String[] parts = input.split("\\s+");
            int nb = Integer.parseInt(parts[1]);

            if(nb < MINSIZE || nb > MAXSIZE){
                System.out.println("? unacceptable size");
                return;
            }

            // The board size is changed.
            // The board configuration, number of captured stones, and move history become arbitrary
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
