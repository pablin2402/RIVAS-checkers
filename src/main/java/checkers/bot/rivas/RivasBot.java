package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;
import checkers.CheckersPlayer;

import java.util.Optional;


public class RivasBot implements CheckersPlayer {
    private static final boolean PLAYER = true; //false is player 1
    private static final int LEVEL =7;

    @Override
    public CheckersMove play(CheckersBoard board)  {
        return getBuilderPosition(board);
    }
    private CheckersMove getBuilderPosition(CheckersBoard board)
    {
        CheckersPablo check = new CheckersPablo();
        Optional<CheckersMove> move = check.getBestMove(board, PLAYER,LEVEL);
        return CheckersMove.builder()
                .fromPosition(move.orElseThrow().getStartRow(), move.orElseThrow().getStartCol())
                .toPosition(move.orElseThrow().getEndRow(), move.orElseThrow().getEndCol())
                .build();
    }
}
