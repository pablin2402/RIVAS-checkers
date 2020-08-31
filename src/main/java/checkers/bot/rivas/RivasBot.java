package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.CheckersPlayer;

import java.util.ArrayList;
import java.util.List;

public class RivasBot implements CheckersPlayer {
    private static final int LEVEL = 6;
    private static List<CheckersMove> children ;

    @Override
    public CheckersMove play(CheckersBoard board) {

        List<CheckersMove> possibleCaptures = board.possibleCaptures();
        if (possibleCaptures.isEmpty()) {
            bestMove(board);
            return CheckersMove.builder()
                    .fromPosition(getBestMove(LEVEL,board).getStartRow(), getBestMove(LEVEL,board).getStartCol())
                    .toPosition(getBestMove(LEVEL,board).getEndRow(),getBestMove(LEVEL,board).getEndCol())
                    .build();
        }
        return CheckersMove.builder()
                .fromPosition(getBestMove(LEVEL,board).getStartRow(), getBestMove(LEVEL,board).getStartCol())
                .toPosition(getBestMove(LEVEL,board).getEndRow(),getBestMove(LEVEL,board).getEndCol())
                .build();
    }

    public CheckersMove getBestMove(int level, CheckersBoard board) {
        int maxUtility = -Integer.MAX_VALUE;
        generateSuccessors(board);
        CheckersMove bestMove = null;
        for (CheckersMove child : children) {
            int utility = getUtility(level - 1, CheckersBoard.Player.BLACK, board);

            if (utility >maxUtility ) {
                maxUtility = utility;
                bestMove = child;
            }
        }
        return bestMove;
    }


    public void generateSuccessors(CheckersBoard board) {
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
    /* TODO: need to implement this method , but i cant access to board and current player */
    /*
    public int utility() {
        int result = 0;
        for ( int i = 0; i < 8; i++ ) {
            for ( int j = 0; j < 8; j++ ) {
                if ( board[ i ][ j ] == 'b' ) {
                    result += 1;
                } else if ( board[ i ][ j ] == Character.toUpperCase( 'b' ) ) {
                    result += 2;
                } else if ( board[ i ][ j ] == 'r' ) {
                    result -= 1;
                } else if ( board[ i ][ j ] == Character.toUpperCase( 'r' ) ) {
                    result -= 2;
                }
            }
        }
        return result;
    }*/
    public int getUtility(int level, CheckersBoard.Player actualplayer,CheckersBoard board) {
        generateSuccessors(board);

        if (level == 0){
            return board.utility();
        }
        if (children.isEmpty() ) {
            return board.utility();
        }
        // it is my turn, so i am going to pick the best choice
        if (CheckersBoard.Player.BLACK == actualplayer) {
            int maxUtility = Integer.MIN_VALUE;
            for (CheckersMove child : children) {
                int score = getUtility(level - 1, actualplayer,board);
                if (score > maxUtility)
                    maxUtility = score;
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

    private void bestMove(CheckersBoard board){
        System.out.println("me movere desde :"+ getBestMove(LEVEL,board).getStartRow() +" col: "+getBestMove(LEVEL,board).getStartRow());
        System.out.println("a");
        System.out.println(getBestMove(LEVEL,board).getEndRow()+ " a "+getBestMove(LEVEL,board).getEndCol());

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

}
