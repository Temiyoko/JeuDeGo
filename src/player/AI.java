package player;

import go.Goban;
import go.Stones;

import java.util.ArrayList;
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
        if (move.equalsIgnoreCase("pass")) {
            if (getLastMove().equalsIgnoreCase("pass")) {
                System.out.println("=" + id + " resigns");
                return false;
            }

            addMove(move);
            System.out.println("=" + id);
            return true;
        }

        // Your logic for handling non-pass moves
        if (!board.isInBoard(coord)) {
            throw new IllegalArgumentException();
        } else if (!board.isPlayable(coord) || isSuicide(coord, board)) {
            throw new RuntimeException();
        } else {
            // Get the list of legal moves
            List<int[]> legalMoves = getLegalMoves(board);

            // Check if there are legal moves available
            if (!legalMoves.isEmpty()) {
                // Choose a random move
                int randomIndex = random.nextInt(legalMoves.size());
                int[] randomMove = legalMoves.get(randomIndex);

                // Set the stone on the board
                board.setStone(randomMove, getStoneColor());

                // Update the score based on captured stones
                setScore(getScore() + board.captureStones(randomMove));

                // Print the result
                System.out.println("=" + id);
                return true;
            } else {
                // No legal moves available, print a message or handle accordingly
                System.out.println("=" + id + " has no legal moves.");
                return false;
            }
        }
    }

    // Helper method to get all legal moves on the board
    private List<int[]> getLegalMoves(Goban board) {
        List<int[]> legalMoves = new ArrayList<>();

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                int[] move = new int[]{i, j};
                if (board.isPlayable(move) && !isSuicide(move, board)) {
                    legalMoves.add(move);
                }
            }
        }

        return legalMoves;
    }
}