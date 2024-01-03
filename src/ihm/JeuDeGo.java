package ihm;

import go.Stones;
import go.Goban;
import go.Player;

import java.util.*;


public class JeuDeGo {
    private static Goban goban;
    private static Player blackP, whiteP;
    private static Map<Player, List<String>> history;

    public static void main(String[] args) {
        initializeGame();
        Player lastPlayer = whiteP;

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
                    break;
                default:
                    System.out.println("?" + id + " unknown command");
                    break;
            }
        }
        sc.close();
    }

    private static void initializeGame() {
        goban = new Goban();
        blackP = new Player(Stones.BLACK);
        whiteP = new Player(Stones.WHITE);
        history = new HashMap<>(Map.of(blackP, new ArrayList<>(), whiteP, new ArrayList<>()));
    }

    private static void showboard(String id) {
        System.out.println("=" + id);
        System.out.println(goban.show(blackP, whiteP));
    }

    private static int[] convert(String s){ //A1
        char c = s.toUpperCase().charAt(0);
        if (c == 'I') {
            throw new IllegalArgumentException();
        }
        int col = c > 'I' ? (int) c - 'A' - 1 : (int) c - 'A';
        int ligne = Integer.parseInt(s.substring(1)) - 1;
        return new int[]{col,  ligne};
    }

    private static Player play(String[] arg, String id, Player lastPlayer) {
        try {
            if (!arg[0].equalsIgnoreCase("white") && !arg[0].equalsIgnoreCase("black")) {
                throw new IllegalArgumentException();
            }
            Player p = arg[0].equalsIgnoreCase("black") ? blackP : whiteP;

            if(p == lastPlayer){
                throw new RuntimeException();
            };

            if (arg[1].equalsIgnoreCase("pass")) {
                List<String> listMove = history.get(p);
                System.out.println(history + "liste des moves : " + listMove);
                if (!listMove.isEmpty() && listMove.get(listMove.size() - 1).equalsIgnoreCase("pass")){
                    System.out.println("=" + id + " resigns");
                    return null;
                }

                history.get(p).add(arg[1]);
                System.out.println("=" + id);
                return p;
            }

            int[] coord = convert(arg[1]);
            if (!goban.isInBoard(coord)) {
                throw new IllegalArgumentException();
            }
            else if (!goban.isPlayable(coord) || isSuicide(coord)) {
                throw new RuntimeException();
            }

            Stones color = (p == blackP) ? Stones.BLACK : Stones.WHITE;

            history.get(p).add(arg[1]);
            goban.setStone(coord, color);

            p.setScore(p.getScore() + goban.captureStones(coord));

            System.out.println("=" + id);
            return p;

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.out.println("?" + id + " invalid color or coordinate");
        } catch (RuntimeException e) {
            System.out.println("?" + id + " illegal move");
        }
        return lastPlayer;
    }

    private static boolean isSuicide(int[] position) {
        return goban.getLiberties(position) == 0;
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
        blackP.resetScore();
        whiteP.resetScore();
        history = new HashMap<>(Map.of(blackP, new ArrayList<>(), whiteP, new ArrayList<>()));
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

