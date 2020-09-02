    package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.exception.BadMoveException;

import java.util.*;

public class CheckersPablo extends CheckersBoard {

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
            for ( int j = 0; j < 8; j++ ) {
                if ( board[ i ][ j ] == BLACK_PLAIN ) {
                    result += 2;
                } else if ( board[ i ][ j ] == RED_PLAIN ) {
                    result -= 2;
                } else if (board[ i ][ j ] == BLACK_CROWNED) {
                    result += 5;
                } else if (board[ i ][ j ] == RED_CROWNED) {
                    result -= 5;
                }
            }
        }
        return result;
    }
    List<CheckersMove> successors = new ArrayList<>();

    protected CheckersMove getBestMove(CheckersBoard board, boolean myPlayer, int level)  {
        CheckersBoard.Player startingPlayer;
        startingPlayer = CheckersBoard.initBoard().getCurrentPlayer();

        generatePossibleMoves(board);
        CheckersBoard child = null;


        List<Integer> heuristics = new ArrayList<>(100);
        if (successors.isEmpty()){
            return  null;
        }
        for (CheckersMove successor : successors) {
            child = board.clone();
            try {
                child.processMove(successor);
            } catch (BadMoveException ex) {
                System.err.println(ex.getMessage());

            }
            heuristics.add(getUtility(level - 1, otherPlayer(startingPlayer), child, !myPlayer));
        }
        CheckersMove checkersMove = null;
        int maxUtility = Integer.MIN_VALUE;
        for (Integer heuristic : heuristics) {
            if (heuristic > maxUtility) {
                maxUtility = heuristic;

            }
        }
        System.out.println(maxUtility);

        System.out.println("ANTES");
        for (int i=0; i< successors.size();i++){
            System.out.println(":"+successors.get(i).getStartCol());
        }
        for (int i = 0; i < heuristics.size(); i++) {
            if (heuristics.get(i) < maxUtility) {
                    heuristics.remove(i);
                    successors.remove(i);
                    i--;
            }
        }
        System.out.println("DESPUES");
        for (int i=0; i<successors.size();i++){
            System.out.println(":"+successors.get(i).getStartCol());
        }
        Random rand = new Random();
        return successors.get(rand.nextInt(successors.size()));
    }

    private void generatePossibleMoves(CheckersBoard board) {
        List<CheckersMove> possibleCaptures = board.possibleCaptures();
        if (possibleCaptures.isEmpty()) {
            successors= board.possibleMoves();
        }else{
            successors = board.possibleCaptures();
        }
    }

    private int getUtility(int level, CheckersBoard.Player player, CheckersBoard board, boolean myPlayer) {
        if (possibleMoves().isEmpty()){
            return heuristic(board);
        }
        if (level == 0) {
            return heuristic(board);
        }
        List<CheckersMove> possibleMoves = board.possibleMoves();
        CheckersBoard checkersBoard = null;
        if (myPlayer) {
            int maxUtility= Integer.MIN_VALUE;
            for(int i=0; i<possibleMoves.size(); i++){
                checkersBoard= board.clone();
                int result = getUtility(level-1, otherPlayer(player),checkersBoard, !myPlayer);
                if(result >maxUtility){
                    maxUtility = result;
                }
            }
            System.out.println("MAX UTILITY"+maxUtility);
            return maxUtility;
        }else {
            int minUtility= Integer.MAX_VALUE;
            for(int i=0; i<possibleMoves.size(); i++){
                checkersBoard= board.clone();
                int result = getUtility(level-1, otherPlayer(player),checkersBoard, !myPlayer );
                if(result <minUtility){
                    minUtility = result;
                }
            }
            System.out.println("MIN UTILITY"+minUtility);
            return minUtility;
        }
    }
}
