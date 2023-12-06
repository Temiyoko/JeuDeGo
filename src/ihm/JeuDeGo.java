package ihm;

import go.Stones;
import go.Goban;
import go.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Arrays;


public class JeuDeGo {
    private static final int MINSIZE = 1, MAXSIZE = 19;
    private static Goban goban;
    private static Player blackP, whiteP;
    private static Map<Player, List<String>> history;

    public static void main(String[] args) {
        goban = new Goban();
        blackP = new Player(Stones.BLACK);
        whiteP = new Player(Stones.WHITE);
        history = new HashMap<>();
        String id, cmd;
        String[] arg;

        Scanner sc = new Scanner(System.in);

        while(sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            String[] arguments = input.split("\\s+");

            if(isInt(arguments[0])){
                id = arguments[0];
                cmd = arguments[1];
                arg = Arrays.copyOfRange(arguments, 2, arguments.length);
            }
            else{
                id = "";
                cmd = arguments[0];
                arg = Arrays.copyOfRange(arguments, 1, arguments.length);
            }

            if (cmd.equals("quit")){
                System.out.println("=" + id);
                break;
            }
            else if (cmd.equals("boardsize")) {
                boardsize(arg, id);
            }
            else if (cmd.equals("showboard")){
                System.out.println("=" + id);
                System.out.println(goban.show(blackP, whiteP));
            }
            else if (cmd.equals("clear_board")) {
                resetGame(goban.getSize());
                System.out.println("=" + id);
            }
            else if (cmd.equals("play")) {
                play(arg, id);
            }
            else{
                System.out.println("?" + id +" unknown command");
            }
        }
        sc.close();
    }

    private static void play(String[] arg, String id) {
        try{
            if(!arg[0].equalsIgnoreCase("white") && !arg[0].equalsIgnoreCase("black")){
                throw new IllegalArgumentException();
            }
            else if (!arg[1].equalsIgnoreCase("pass") && !goban.isInBoard(arg[1])) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.out.println("?" + id + " invalid color or coordinate");
        }
    }

    private static void boardsize(String[] args, String id) {
        try {
            int nb = Integer.parseInt(args[0]);

            if(nb < MINSIZE || nb > MAXSIZE){
                System.out.println("?" + id + " unacceptable size");
                return;
            }

            resetGame(nb);
            System.out.println("=" + id);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("?" + id + " boardsize not an integer");
        }
        catch (NumberFormatException e) {
            System.out.println("?" + id + " unknown command");
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

    private static boolean isInt(String s){
        try{
           Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
