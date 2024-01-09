package go.ihm;

import go.Stones;
import go.Goban;
import player.AI;
import player.Human;
import go.IPlayer;

import java.util.*;


public class Main {
    private static Goban goban;
    private static IPlayer blackP, whiteP;
    private static IPlayer lastPlayer, currentPlayer;
    private static boolean isAITurn;

    public static void main(String[] args) {
        initializeGame(args);

        Scanner sc = new Scanner(System.in);

        while(true) {
            String input;
            String[] arguments = new String[]{"play", currentPlayer == blackP ? "black" : "white", "a1"};

            if (!isAITurn) {
                input = sc.nextLine().trim();
                arguments = input.split("\\s+");
            }

            String id, cmd;
            String[] arg;

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

            switch (cmd) {
                case "quit":
                    System.out.println("=" + id);
                    sc.close();
                    return;
                case "boardsize":
                    boardsize(arg, id);
                    break;
                case "showboard":
                    showboard(id);
                    break;
                case "clear_board":
                    resetGame(goban.getSize());
                    System.out.println("=" + id);
                    break;
                case "play":
                    lastPlayer = play(arg, id, lastPlayer);
                    if (lastPlayer == null) {
                        sc.close();
                        return;
                    }

                    currentPlayer = lastPlayer == whiteP ? blackP : whiteP;
                    isAITurn = currentPlayer.getClass() == AI.class;

                    showboard(id);
                    break;
                default:
                    System.out.println("?" + id + " unknown command");
                    break;
            }
        }
    }

    private static void initializeGame(String[] args) {
        String[] arg = Arrays.copyOfRange(args, 0, args.length);
        assert isInt(args[0]);
        int nbAI = Integer.parseInt(args[0]);

        blackP = nbAI == 2 ? new AI(Stones.BLACK) : new Human(Stones.BLACK);
        whiteP = nbAI == 0 ? new Human(Stones.WHITE) : new AI(Stones.WHITE);
        goban = new Goban(blackP, whiteP);

        lastPlayer = whiteP;
        currentPlayer = blackP;
        isAITurn = nbAI == 2;

        if(arg.length > 1){
            assert isInt(args[1]);
            boardsize(new String[]{arg[1]}, "");
        }
    }

    private static void showboard(String id) {
        System.out.println("=" + id);
        System.out.println(goban.show(blackP, whiteP));
    }

    private static int[] convert(String s){
        if(s.equalsIgnoreCase("pass")){
            return new int[]{-1, -1};
        }

        char c = s.toUpperCase().charAt(0);
        if (c == 'I') {
            throw new IllegalArgumentException();
        }
        int col = c > 'I' ? (int) c - 'A' - 1 : (int) c - 'A';
        int ligne = Integer.parseInt(s.substring(1)) - 1;
        return new int[]{col,  ligne};
    }

    public static IPlayer play(String[] arg, String id, IPlayer lastP){
        try {
            if (!arg[0].equalsIgnoreCase("white") && !arg[0].equalsIgnoreCase("black")) {
                throw new IllegalArgumentException();
            }
            IPlayer p = arg[0].equalsIgnoreCase("black") ? blackP : whiteP;

            if(p == lastP){
                throw new RuntimeException();
            }

            if(!p.play(convert(arg[1]), goban)){
                System.out.println("=" + id + " resigns");
                return null;
            }
            System.out.println("=" + id);
            return p;

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.out.println("?" + id + " invalid color or coordinate");
        } catch (RuntimeException e) {
            System.out.println("?" + id + " illegal move");
        }
        return lastP;
    }

    private static void boardsize(String[] args, String id) {
        try {
            int nb = Integer.parseInt(args[0]);

            if(nb < goban.getMinSize() || nb > goban.getMaxSize()){
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
        assert nb >= goban.getMinSize() && nb <= goban.getMaxSize();

        blackP.reset();
        whiteP.reset();
        goban = new Goban(nb, blackP, whiteP);

        lastPlayer = whiteP;
        currentPlayer = blackP;
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