package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.exception.BadMoveException;

import java.util.Optional;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CheckersPablo extends CheckersBoard {
    List<CheckersMove> successors = new ArrayList<>(1000);
    protected Optional<CheckersMove> getBestMove(CheckersBoard board, boolean myPlayer, int level) {
        CheckersBoard.Player startingPlayer;
        startingPlayer = CheckersBoard.initBoard().getCurrentPlayer();
        generatePossibleMovesAndCaptures(board);
        return getBestMoveOption(board, myPlayer, level, startingPlayer);
    }
    private Optional<CheckersMove> getBestMoveOption(CheckersBoard board, boolean myPlayer, int level, Player startingPlayer) {
        if (successors.isEmpty()) {
            return Optional.empty();
        }
        CheckersMove bestMoveOption = null;
        int maxValue = Integer.MIN_VALUE;
        int childScore;
        for (CheckersMove successor : successors) {
            CheckersBoard child = board.clone();
            try {
                child.processMove(successor);
            } catch (BadMoveException ex) {
                throw new IllegalStateException("Invalid Move ?.... What happened my friend, you used to be cool."+ ex.getMessage());
            }
            MyPlayerGame builder =MyPlayerGame.build()
                    .builder(child, !myPlayer)
                    .builderTo(level - 1, otherPlayer(startingPlayer))
                    .build();
            childScore = getUtility(builder);
            if (childScore > maxValue) {
                maxValue = childScore;
                bestMoveOption = successor;
            }
            if (bestMoveOption == null) {
                bestMoveOption = successor;
            }
        }
        return Optional.of(bestMoveOption);
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
    private Map<CheckersMove, Integer> getUtilityMap(MyPlayerGame myPlayer) {
        CheckersBoard child;
        Map<CheckersMove, Integer> utilityMap = new HashMap<>();
        for (CheckersMove successor: successors) {
            child = myPlayer.getBoard().clone();
            try {
                child.processMove(successor);
            } catch (BadMoveException ex) {
                System.err.println(ex.getMessage());

            }
            MyPlayerGame builder = MyPlayerGame.build()
                    .builder(child, !myPlayer.isMyPlayer())
                    .builderTo(myPlayer.getLevel() - 1, otherPlayer(myPlayer.getStartingPlayer()))
                    .build();
            utilityMap.put(successor, getUtility(builder));
        }
        return utilityMap;
    }

    private int getUtility(MyPlayerGame myPlayer) {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        if (successors.isEmpty()) {
            return heuristic(myPlayer.getBoard());
        }
        if (myPlayer.getLevel() == 0) {
            return heuristic(myPlayer.getBoard());
        }
        generatePossibleMovesAndCaptures(myPlayer.getBoard());
        Map<CheckersMove, Integer> utilityMap = getUtilityMap(myPlayer);

        if (myPlayer.isMyPlayer()) {
            return maxUtilityForMyPlayer(utilityMap,alpha, beta);
        } else {
            return minUtilityForOtherBot(utilityMap, alpha, beta);
        }
    }
    private int minUtilityForOtherBot( Map<CheckersMove, Integer> utilityMap, int alpha, int beta) {
        int minUtility = Integer.MAX_VALUE;
        for (Map.Entry<CheckersMove, Integer> utilityEntry: utilityMap.entrySet()) {
            minUtility = Math.min(utilityEntry.getValue(), minUtility);
            beta = Math.min(beta, minUtility);
            if(alpha >= beta) {
                break;
            }
        }
        return minUtility;
    }

    private int maxUtilityForMyPlayer( Map<CheckersMove, Integer> utilityMap, int alpha, int beta) {
        int maxUtility = Integer.MIN_VALUE;
        for (Map.Entry<CheckersMove, Integer> utilityEntry: utilityMap.entrySet()) {
            maxUtility = Math.max(utilityEntry.getValue(), maxUtility);
            alpha = Math.max(alpha, maxUtility);
            if(alpha >= beta) {
                break;
            }
        }
        return maxUtility;
    }
    private int heuristic(CheckersBoard b) {
        final int SIZE = 8;
        int result = 0;
        char[][] board = b.getBoard();
        for (int i = 0; i < SIZE ; i++) {
            for (int j = 0; j < SIZE ; j++) {
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
