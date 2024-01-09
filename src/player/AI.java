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
    public boolean play(int[] coord, Goban board) {
        List<int[]> legalMoves = getLegalMoves(board);
        if (!legalMoves.isEmpty()) {
            int randomIndex = random.nextInt(legalMoves.size());
            int[] randomMove = legalMoves.get(randomIndex);

            board.setStone(randomMove, getStoneColor());

            setScore(getScore() + board.countCaptureStones(randomMove, getStoneColor()));
            board.captureStones(randomMove, getStoneColor());

            board.addMove(this, randomMove);
        }
        else {
            if (Arrays.equals(board.getLastMove(this), new int[]{-1, -1})) {
                return false;
            }
            board.addMove(this, new int[]{-1, -1});
        }
        return true;
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