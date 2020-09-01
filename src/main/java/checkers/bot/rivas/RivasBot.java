package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.CheckersPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RivasBot implements CheckersPlayer {
    private static final int LEVEL =5;
    private static final boolean MYPLAYER = true; //true is black
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
                .fromPosition(getBestMove(board, MYPLAYER).getStartRow(), getBestMove(board, MYPLAYER).getStartCol())
                .toPosition(getBestMove(board, MYPLAYER).getEndRow(), getBestMove(board, MYPLAYER).getEndCol())
                .build();
    }

    private void bestMove(CheckersBoard board){

        System.out.println("Mi movimiento será de :"+ getBestMove(board, MYPLAYER).getStartRow() +" y: "+getBestMove(board, MYPLAYER).getStartCol());
        System.out.println("A: ");
        System.out.println(getBestMove(board, MYPLAYER).getEndRow()+ " y: "+getBestMove(board, MYPLAYER).getEndCol());

    }
    protected CheckersMove getBestMove(CheckersBoard board, boolean myPlayer)  {
        if (RivasBot.LEVEL <1){
            System.out.println("Level must be bigger than 0");
        }
        int maxUtility = -Integer.MAX_VALUE;
        generateSuccessors(board);
        CheckersMove bestMove = null;
        CheckersBoard.Player currentPlayer;
        currentPlayer = CheckersBoard.initBoard().getCurrentPlayer();
        return getCheckersMove(board, maxUtility, bestMove, currentPlayer,myPlayer, RivasBot.LEVEL);
    }

    private CheckersMove getCheckersMove(CheckersBoard board, int maxUtility, CheckersMove bestMove, CheckersBoard.Player player, boolean myPlayer,int level) {
        if (children == null){
            return  null;
        }
        for (CheckersMove child : children) {
            int utility = getUtility(level - 1, ch.otherPlayer(player), board, !myPlayer);
            if (utility > maxUtility) {
                maxUtility = utility;
                bestMove = child;
            }
        }
        return bestMove;
    }
    private int getUtility(int level, CheckersBoard.Player player, CheckersBoard board, boolean myPlayer) {
        if (children.isEmpty()){
            return heuristic();
        }
        if (level == 0) {
            return heuristic();
        }
        generateSuccessors(board);
        Map<CheckersMove, Integer> utilityMap = new HashMap<>();
        for (CheckersMove successor: children) {
            utilityMap.put(successor, getUtility(level-1,ch.otherPlayer(player), board, !myPlayer));
        }
        // it is my turn, so i am going to pick the best choice
        if (myPlayer) {
            int maxUtility = Integer.MIN_VALUE;
            for (Map.Entry<CheckersMove, Integer> utilityEntry: utilityMap.entrySet()) {
                if (utilityEntry.getValue() > maxUtility) {
                    maxUtility = utilityEntry.getValue();
                }
            }

            return maxUtility;
        }else {
            int minUtility = Integer.MAX_VALUE; // it is a kind of cheat
            for (Map.Entry<CheckersMove, Integer> utilityEntry : utilityMap.entrySet()) {
                if (utilityEntry.getValue() < minUtility) {
                    minUtility = utilityEntry.getValue();

                }
            }
            return minUtility;
        }
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




}
