package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.CheckersPlayer;



public class RivasBot implements CheckersPlayer {
    private static final boolean PLAYER = true; //true is black
    private static final int LEVEL =5;

    @Override
    public CheckersMove play(CheckersBoard board)  {
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
