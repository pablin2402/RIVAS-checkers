package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.CheckersPlayer;


import java.util.List;

public class RivasBot implements CheckersPlayer {
    private static final boolean PLAYER = true; //true is black
    private static final int LEVEL =5;

    @Override
    public CheckersMove play(CheckersBoard board)  {

        List<CheckersMove> possibleCaptures = board.possibleCaptures();
        if (possibleCaptures.isEmpty()) {
            return getBuild(board);
        }
        return getBuild(board);

    }

    private CheckersMove getBuild(CheckersBoard board)
    {
        CheckersPablo check = new CheckersPablo();
        CheckersMove move = check.getBestMove(board, PLAYER,LEVEL);
        return CheckersMove.builder()
                .fromPosition(move.getStartRow(), move.getStartCol())
                .toPosition(move.getEndRow(), move.getEndCol())
                .build();
    }
}
