    package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.exception.BadMoveException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


    public class CheckersPablo extends CheckersBoard {
    List<CheckersMove> successors = new ArrayList<>();

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

    public int heuristic(CheckersBoard b) {
        int result = 0;
        char[][] board = b.getBoard();
        for ( int i = 0; i < 8; i++ ) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == BLACK_PLAIN) {
                    result += 2;
                } else if ( board[ i ][ j ] == RED_PLAIN ) {
                     result -= 1;
                } else if (board[ i ][ j ] == BLACK_CROWNED) {
                    result += 5;
                } else if (board[ i ][ j ] == RED_CROWNED) {
                    result -= 5;
                }
            }
        }
        return result;
    }
    protected CheckersMove getBestMove(CheckersBoard board, boolean myPlayer, int level)  {
        CheckersBoard.Player startingPlayer;
        startingPlayer = CheckersBoard.initBoard().getCurrentPlayer();
        generatePossibleMovesAndCaptures(board);
        if (successors.isEmpty()){
            return  null;
        }
        return getCheckersMove(board, myPlayer, level, startingPlayer);
    }
    private CheckersMove getCheckersMove(CheckersBoard board, boolean myPlayer, int level, Player startingPlayer) {
        CheckersMove bestMoveOption= null;
        CheckersBoard child = null;
        int maxValue = Integer.MIN_VALUE;
        for (CheckersMove successor : successors) {
             child = board.clone();
             try {
                 child.processMove(successor);
             } catch (BadMoveException ex) {
                 System.err.println(ex.getMessage());
             }
             int childScore = getUtility(level - 1, otherPlayer(startingPlayer), child, !myPlayer);
             if (childScore > maxValue){
                 maxValue= childScore;
                 bestMoveOption = successor;
             }
         }

        return bestMoveOption;
    }

    private void generatePossibleMovesAndCaptures(CheckersBoard board) {
        List<CheckersMove> possibleCaptures = board.possibleCaptures();
        if (possibleCaptures.isEmpty()) {
            successors= board.possibleMoves();
        }else{
            successors = board.possibleCaptures();
        }
    }

    private int getUtility(int level, CheckersBoard.Player player, CheckersBoard board, boolean myPlayer) {
        if (successors.isEmpty()){
            return heuristic(board);
        }
        if (level == 0) {
            return heuristic(board);
        }
        generatePossibleMovesAndCaptures(board);


        if (myPlayer) {
            return maxUtilityForMyPlayer(level, player, board, myPlayer);
        }else {
            return minUtilityForOtherBot(level, player, board, myPlayer);
        }
    }

    private int minUtilityForOtherBot(int level, Player player, CheckersBoard board, boolean myPlayer) {
        CheckersBoard child;
        int minUtility= Integer.MAX_VALUE;
        for (CheckersMove successor : successors) {
            child = board.clone();
            try {
                child.processMove(successor);
            } catch (BadMoveException ex) {
                System.err.println(ex.getMessage());

            }
            int utility = getUtility(level - 1, otherPlayer(player), child, !myPlayer);
            if (utility < minUtility) {
                minUtility = utility;
            }
        }
        return minUtility;
    }

    private int maxUtilityForMyPlayer(int level, Player player, CheckersBoard board, boolean myPlayer) {
        CheckersBoard child;
        int maxUtility= Integer.MIN_VALUE;
        for (CheckersMove successor : successors) {
            child = board.clone();
            try {
                child.processMove(successor);
            } catch (BadMoveException ex) {
                System.err.println(ex.getMessage());

            }
            int utility = getUtility(level - 1, otherPlayer(player), child, !myPlayer);
            if (utility > maxUtility) {
                maxUtility = utility;
            }
        }
        return maxUtility;
    }
}
