package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.CheckersPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RivasBot implements CheckersPlayer {
    private static final int LEVEL = 2;

    @Override
    public CheckersMove play(CheckersBoard board) {

        // List<CheckersMove> possibleMoves = board.possibleMoves();
        return CheckersMove.builder()
                .fromPosition(getBestMove(LEVEL, board).getStartRow(), getBestMove(LEVEL, board).getStartCol())
                .toPosition(getBestMove(LEVEL, board).getEndRow(), getBestMove(LEVEL, board).getEndCol()).build();

    }

    List<CheckersMove> children = new ArrayList<>();

    public void generateSuccessors(CheckersBoard board) {
        List<CheckersMove> possibleCaptures = board.possibleCaptures();
        List<CheckersMove> possibleMoves = board.possibleMoves();
        for (CheckersMove check : possibleCaptures) {
            for (CheckersMove checkLEVEL : possibleMoves) {
                // if (board.isCapturePossible()) {
                children.add(check);
                // } else {
                children.add(checkLEVEL);
                // }
            }
        }

    }

    public CheckersMove getBestMove(int level, CheckersBoard board) {
        int maxUtility = Integer.MIN_VALUE;
        if (level == 0) {
            return null;
        }
        generateSuccessors(board);
        CheckersMove bestMove = null;
        for (CheckersMove child : children) {
            int utility = getUtility(level - 1, CheckersBoard.Player.BLACK, board);
            System.out.println("caca LEVEL");

            if (maxUtility < utility) {
                maxUtility = utility;
                bestMove = child;
            }
        }
        return bestMove;
    }

    public int getUtility(int level, CheckersBoard.Player actualplayer, CheckersBoard board) {

        if (children.isEmpty() || children == null) {
            if (CheckersBoard.Player.BLACK == actualplayer) {
                return 1;
            } else {
                return -1;
            }
        }
        generateSuccessors(board);

        // it is my turn, so i am going to pick the best choice
        if (CheckersBoard.Player.BLACK == actualplayer) {
            int maxUtility = Integer.MIN_VALUE;
            for (CheckersMove child : children) {
                int score = getUtility(level - 1, actualplayer, board);
                if (score > maxUtility) {
                    maxUtility = score;
                }
                System.out.println("caca 3");

            }
            return maxUtility;
        } else {
            int minUtility = Integer.MAX_VALUE; // it is a kind of cheat
            for (CheckersMove child : children) {
                int score = getUtility(level - 1, actualplayer, board);
                if (score < minUtility) {
                    minUtility = score;
                }
            }
            return minUtility;
        }

    }

}
