package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.exception.BadMoveException;

import java.util.*;


public class CheckersPablo extends CheckersBoard {
    List<CheckersMove> successors = new ArrayList<>(1000);
    protected CheckersMove getBestMove(CheckersBoard board, boolean myPlayer, int level) {
        CheckersBoard.Player startingPlayer;
        startingPlayer = CheckersBoard.initBoard().getCurrentPlayer();
        generatePossibleMovesAndCaptures(board);
        return getBestMoveOption(board, myPlayer, level, startingPlayer);
    }
    private CheckersMove getBestMoveOption(CheckersBoard board, boolean myPlayer, int level, Player startingPlayer) {
        if (successors.isEmpty()) {
            return null;
        }
        CheckersMove bestMoveOption = null;
        int maxValue = Integer.MIN_VALUE;
        int childScore = 0;
        for (CheckersMove successor : successors) {
            CheckersBoard child = board.clone();
            try {
                child.processMove(successor);
            } catch (BadMoveException ex) {
                throw new IllegalStateException("Invalid Move ?.... What happened my friend, you used to be cool."+ ex.getMessage());
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
        int alpha =Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        if (successors.isEmpty()) {
            return heuristic(myPlayer.getBoard());
        }
        if (myPlayer.getLevel() == 0) {
            return heuristic(myPlayer.getBoard());
        }
        generatePossibleMovesAndCaptures(myPlayer.getBoard());
        MyPlayerGame myPlayerGame = new MyPlayerGame(myPlayer.getBoard(),myPlayer.isMyPlayer(),myPlayer.getLevel(), myPlayer.getStartingPlayer());
        if (myPlayer.isMyPlayer()) {
            return maxUtilityForMyPlayer(myPlayerGame, alpha, beta);
        } else {
            return minUtilityForOtherBot(myPlayerGame, alpha, beta);
        }
    }

    private int minUtilityForOtherBot(MyPlayerGame myPlayer, int alpha, int beta) {
        CheckersBoard child;
        int minUtility = Integer.MAX_VALUE;
        for (CheckersMove successor : successors) {
            child = myPlayer.getBoard().clone();
            try {
                child.processMove(successor);
            } catch (BadMoveException ex) {
                System.err.println(ex.getMessage());

            }
            MyPlayerGame myPlayerGame = new MyPlayerGame(child, !myPlayer.isMyPlayer(), myPlayer.getLevel() - 1, otherPlayer(myPlayer.getStartingPlayer()));
            int utility = getUtility(myPlayerGame);
            minUtility = Math.min(utility, minUtility);
            beta = Math.min(beta, minUtility);
            if(alpha >= beta) {
                break;
            }
        }
        return minUtility;
    }

    private int maxUtilityForMyPlayer(MyPlayerGame myPlayer, int alpha, int beta) {
        CheckersBoard child;
        int maxUtility = Integer.MIN_VALUE;
        for (CheckersMove successor : successors) {
            child = myPlayer.getBoard().clone();
            try {
                child.processMove(successor);
            } catch (BadMoveException ex) {
                System.err.println(ex.getMessage());

            }
            MyPlayerGame myPlayerGame = new MyPlayerGame(child, !myPlayer.isMyPlayer(), myPlayer.getLevel() - 1, otherPlayer(myPlayer.getStartingPlayer()));

            int utility = getUtility(myPlayerGame);
            maxUtility = Math.max(utility, maxUtility);
            alpha = Math.max(alpha, maxUtility);
            if(alpha >= beta) {
                break;

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
