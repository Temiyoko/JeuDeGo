package ihm;

import go.Stones;
import go.Goban;
import player.Human;

import java.util.*;


public class JeuDeGo {
    private static Goban goban;
    private static IPlayer blackP, whiteP;
    public static void main(String[] args) {
        initializeGame(args);
        IPlayer lastPlayer = whiteP;

        Scanner sc = new Scanner(System.in);

        while(sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            String[] arguments = input.split("\\s+");

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
                        return;
                    }
                    showboard(id);
                    break;
                default:
                    System.out.println("?" + id + " unknown command");
                    break;
            }
        }
        sc.close();
    }

    private static void initializeGame(String[] args) {
        assert isInt(args[0]);
        goban = new Goban();
        blackP = new Human(Stones.BLACK);
        whiteP = new Human(Stones.WHITE);
    }

    private static void showboard(String id) {
        System.out.println("=" + id);
        System.out.println(goban.show(blackP, whiteP));
    }

    private static int[] convert(String s){
        if(s.equalsIgnoreCase("pass")){
            return new int[]{0, 0};
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

            if(!p.play(arg[1], id, convert(arg[1]), goban)){
                return null;
            }
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

        goban = new Goban(nb);
        blackP.reset();
        whiteP.reset();
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