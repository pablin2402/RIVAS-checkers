package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.exception.BadMoveException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CheckersPablo extends CheckersBoard {
    List<CheckersMove> successors = new ArrayList<>(1000);
    protected CheckersMove getBestMove(CheckersBoard board, boolean myPlayer, int level) {
        CheckersBoard.Player startingPlayer;
        startingPlayer = CheckersBoard.initBoard().getCurrentPlayer();
        generatePossibleMovesAndCaptures(board);
        return getCheckersMove(board, myPlayer, level, startingPlayer);
    }

    private CheckersMove getCheckersMove(CheckersBoard board, boolean myPlayer, int level, Player startingPlayer) {
        if (successors.isEmpty()) {
            return null;
        }
        return getBestMoveOption(board, myPlayer, level, startingPlayer);
    }

    private CheckersMove getBestMoveOption(CheckersBoard board, boolean myPlayer, int level, Player startingPlayer) {
        CheckersMove bestMoveOption = null;

        int maxValue = Integer.MIN_VALUE;
        int childScore = 0;
        for (CheckersMove successor : successors) {
            CheckersBoard child = board.clone();
            try {
                child.processMove(successor);
            } catch (BadMoveException ex) {
                System.err.println(ex.getMessage());
            }
            MyPlayerGame myPlayerGame = new MyPlayerGame(child, !myPlayer, level - 1, otherPlayer(startingPlayer));
            childScore = getUtility(myPlayerGame);
            if (childScore > maxValue) {
                maxValue = childScore;
                bestMoveOption = successor;
            }
            if (bestMoveOption == null) {
                bestMoveOption = successor;
            }
        }
        return bestMoveOption;
    }

    private void generatePossibleMovesAndCaptures(CheckersBoard board) {
        List<CheckersMove> children;
        List<CheckersMove> possibleCaptures = board.possibleCaptures();
        if (possibleCaptures.isEmpty()) {
            children = board.possibleMoves();
        } else {
            children = board.possibleCaptures();
        }
        this.successors = children;
    }

    private int getUtility(MyPlayerGame myPlayer) {

        if (successors.isEmpty()) {
            return heuristic(myPlayer.getBoard());
        }
        if (myPlayer.getLevel() == 0) {
            return heuristic(myPlayer.getBoard());
        }
        generatePossibleMovesAndCaptures(myPlayer.getBoard());
        if (myPlayer.isMyPlayer()) {
            return maxUtilityForMyPlayer(myPlayer.getLevel(), myPlayer.getStartingPlayer(), myPlayer.getBoard(), myPlayer.isMyPlayer());
        } else {
            return minUtilityForOtherBot(myPlayer.getLevel(), myPlayer.getStartingPlayer(), myPlayer.getBoard(), myPlayer.isMyPlayer());
        }
    }

    private int minUtilityForOtherBot(int level, Player player, CheckersBoard board, boolean myPlayer) {
        CheckersBoard child;
        int minUtility = Integer.MAX_VALUE;
        for (CheckersMove successor : successors) {
            child = board.clone();
            try {
                child.processMove(successor);
            } catch (BadMoveException ex) {
                System.err.println(ex.getMessage());

            }
            MyPlayerGame myPlayerGame = new MyPlayerGame(child, !myPlayer, level - 1, otherPlayer(player));
            int utility = getUtility(myPlayerGame);
            if (utility < minUtility) {
                minUtility = utility;
            }
        }
        return minUtility;
    }

    private int maxUtilityForMyPlayer(int level, Player player, CheckersBoard board, boolean myPlayer) {
        CheckersBoard child;
        int maxUtility = Integer.MIN_VALUE;
        for (CheckersMove successor : successors) {
            child = board.clone();
            try {
                child.processMove(successor);
            } catch (BadMoveException ex) {
                System.err.println(ex.getMessage());

            }
            MyPlayerGame myPlayerGame = new MyPlayerGame(child, !myPlayer, level - 1, otherPlayer(player));

            int utility = getUtility(myPlayerGame);
            if (utility > maxUtility) {
                maxUtility = utility;
            }
        }
        return maxUtility;
    }
    private int heuristic(CheckersBoard b) {
        int result = 0;
        char[][] board = b.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == BLACK_PLAIN) {
                    result += 2;
                } else if (board[i][j] == RED_PLAIN) {
                    result -= 2;
                } else if (board[i][j] == BLACK_CROWNED) {
                    result += 5;
                } else if (board[i][j] == RED_CROWNED) {
                    result -= 5;
                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CheckersPablo that = (CheckersPablo) o;
        return Objects.equals(successors, that.successors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), successors);
    }

}
