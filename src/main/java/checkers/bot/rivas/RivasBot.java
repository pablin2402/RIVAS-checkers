package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.CheckersPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RivasBot implements CheckersPlayer {
    private static final int LEVEL = 6;
    private static boolean currentPlayer = true; //true is black
    List<CheckersMove> children ;

    @Override
    public CheckersMove play(CheckersBoard board) {

        List<CheckersMove> possibleCaptures = board.possibleCaptures();
        if (possibleCaptures.isEmpty()) {
            bestMove(board);
            return getBuild(board);
        }
        return getBuild(board);
    }

    private CheckersMove getBuild(CheckersBoard board) {
        return CheckersMove.builder()
                .fromPosition(getBestMove(LEVEL, board).getStartRow(), getBestMove(LEVEL, board).getStartCol())
                .toPosition(getBestMove(LEVEL, board).getEndRow(), getBestMove(LEVEL, board).getEndCol())
                .build();
    }

    private void bestMove(CheckersBoard board){

        System.out.println("Mi movimiento será de :"+ getBestMove(LEVEL,board).getStartRow() +" y: "+getBestMove(LEVEL,board).getStartCol());
        System.out.println("A: ");
        System.out.println(getBestMove(LEVEL,board).getEndRow()+ " y: "+getBestMove(LEVEL,board).getEndCol());

    }
    protected CheckersMove getBestMove(int level, CheckersBoard board) {
        int maxUtility = -Integer.MAX_VALUE;
        generateSuccessors(board);
        CheckersMove bestMove = null;
        CheckersBoard.Player currentPlayer;
        currentPlayer = CheckersBoard.initBoard().getCurrentPlayer();
        return getCheckersMove(level, board, maxUtility, bestMove, currentPlayer);
    }
    private CheckersMove getCheckersMove(int level, CheckersBoard board, int maxUtility, CheckersMove bestMove,CheckersBoard.Player player) {
        if (children == null){
            return  null;
        }
        for (CheckersMove child : children) {
            int utility = getUtility(level - 1, ch.otherPlayer(player), board);
            if (utility > maxUtility) {
                maxUtility = utility;
                bestMove = child;
            }

        }
        return bestMove;
    }
    private void generateSuccessors(CheckersBoard board) {
        List<CheckersMove> successors = new ArrayList<>(100);
        List<CheckersMove> possibleCaptures = board.possibleCaptures();
        if (possibleCaptures.isEmpty()) {
            List<CheckersMove> possibleMoves = board.possibleMoves();
            successors.addAll(possibleMoves);
        }else{
            successors.addAll(possibleCaptures);
        }
        this.children = successors;
    }

    private void movementsList (){
        System.out.println("LISTA DE MOVIMIENTOS");
        for (CheckersMove checkersMove : children) {
            System.out.println("-+".repeat(6));

            System.out.println("de: " + checkersMove.getStartRow() + " a " + checkersMove.getStartCol());
            System.out.println("A");
            System.out.println("de: " + checkersMove.getEndRow() + " a " + checkersMove.getEndCol());

        }
    }
    CheckersBoard ch = new CheckersBoard();
    private int getUtility(int level, CheckersBoard.Player player, CheckersBoard board) {
        if (children.isEmpty()){
            return heuristic();
        }
        if (level == 0) {
            return heuristic();
        }
        generateSuccessors(board);
        Map<CheckersMove, Integer> utilityMap = new HashMap<>();
        for (CheckersMove successor: children) {
            utilityMap.put(successor, getUtility(level-1,ch.otherPlayer(player), board));
        }

        // it is my turn, so i am going to pick the best choice
        if (CheckersBoard.Player.BLACK == player) {
            int maxUtility = Integer.MIN_VALUE;
            for (Map.Entry<CheckersMove, Integer> utilityEntry: utilityMap.entrySet()) {
                if (utilityEntry.getValue() > maxUtility) {
                    maxUtility = utilityEntry.getValue();
                }
            }

            return maxUtility;
        }
        int minUtility = Integer.MAX_VALUE; // it is a kind of cheat
        for (Map.Entry<CheckersMove, Integer> utilityEntry: utilityMap.entrySet()) {
            if (utilityEntry.getValue() < minUtility) {
                minUtility = utilityEntry.getValue();
            }
        }
        return minUtility;

    }
    private int heuristic() {
        int result = 0;
        for ( int i = 0; i < 8; i++ ) {
            for ( int j = 0; j < 8; j++ ) {
                if ( ch.getBoard()[ i ][ j ] == 'b' ) {
                    result += 2;
                } else if ( ch.getBoard()[ i ][ j ] == 'r' ) {
                    result -= 2;
                } else if ( ch.getBoard()[ i ][ j ] == Character.toUpperCase( 'b' ) ) {
                    result += 5;
                } else if ( ch.getBoard()[ i ][ j ] == Character.toUpperCase( 'r' ) ) {
                    result -= 5;
                }
            }
        }
        return result;
    }






}
