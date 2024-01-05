package player;

import go.Goban;
import go.Stones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AI extends Player {
    private final Random random;

    public AI(Stones c) {
        super(c);
        this.random = new Random();
    }

    @Override
    public boolean play(String move, String id, int[] coord, Goban board) {
        List<int[]> legalMoves = getLegalMoves(board);
        if (!legalMoves.isEmpty()) {
            int randomIndex = random.nextInt(legalMoves.size());
            int[] randomMove = legalMoves.get(randomIndex);

            board.setStone(randomMove, getStoneColor());
            System.out.println("L'ia a jouer en " + Arrays.toString(randomMove));
            setScore(getScore() + board.countCaptureStones(randomMove, getStoneColor()));
            board.captureStones(randomMove, getStoneColor());

            System.out.println(convertBack(randomMove));
            addMove(convertBack(randomMove));
        }
        else {
            if (getLastMove().equalsIgnoreCase("pass")) {
                System.out.println("=" + id + " resigns");
                return false;
            }
            addMove("pass");
        }
        System.out.println("=" + id);
        return true;
    }

    private static String convertBack(int[] coords) {
        char colChar = (char) (coords[0] >= 8 ? 'A' + coords[0] + 1 : 'A' + coords[0]);
        int row = coords[1] + 1;

        if (colChar >= 'I') {
            colChar++;
        }

        return "" + colChar + row;
    }


    private List<int[]> getLegalMoves(Goban board) {
        List<int[]> legalMoves = new ArrayList<>();

        for (int i = 0; i < board.getSize(); ++i) {
            for (int j = 0; j < board.getSize(); ++j) {
                int[] move = new int[]{i, j};

                if (board.isPlayable(move) && !isSuicide(move, board)) {
                    legalMoves.add(move);
                }
            }
        }
        return legalMoves;
    }
}