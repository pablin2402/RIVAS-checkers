package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.CheckersPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RivasBot implements CheckersPlayer {
    @Override
    public CheckersMove play(CheckersBoard board) {
       return  null;
    }
    char currentPlayer;



    public CheckersMove getBestMove(){
        int maxUtility = Integer.MIN_VALUE;
            CheckersMove bestMove = null;

            for (RivasBot rivasBot : children){
                int utility = rivasBot.getUtility(this.currentPlayer);
                if(maxUtility<utility){
                    maxUtility = utility;
                    bestMove = rivasBot.moveFromParent;
                }
            }
            return bestMove;
    }
    List< CheckersMove > children;

    public  void generateSuccesors( CheckersBoard board){
        children = new ArrayList<>();
        List<CheckersMove> possibleCaptures = board.possibleCaptures();
        if (possibleCaptures.isEmpty()) {
            List<CheckersMove> possibleMoves = board.possibleMoves();
        }
    }

    public int getUtility (int level, char actualplayer){
        if (children.isEmpty()){
            if (this.currentPlayer == actualplayer){
                return 1;
            }else{
                return -1;
            }
            return  0;
        }
        Map <RivasBot> utilityMap = new HashMap<>();

        for (CheckersMove successor: utilitytyMap) {
            utilityMap.put(successor, successor.getUtility(player));
        }
        int max_Utility = Integer.MIN_VALUE;
        // it is my turn, so i am going to pick the best choice
        if (this.currentPlayer == actualplayer) {
            int maxUtility = Integer.MIN_VALUE;
            for (Map.Entry<RivasBot, Integer> utilityEntry: utilityMap.entrySet()) {
                if (utilityEntry.getValue() > maxUtility) {
                    maxUtility = utilityEntry.getValue();
                }
            }
            return maxUtility;
        }else{
            int minUtility = Integer.MAX_VALUE; // it is a kind of cheat
            for (Map.Entry<RivasBot, Integer> utilityEntry: utilityMap.entrySet()) {
                if (utilityEntry.getValue() < minUtility) {
                    minUtility = utilityEntry.getValue();
                }
            }
            return minUtility;

        }

    }

}
