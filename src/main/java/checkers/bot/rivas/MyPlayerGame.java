package checkers.bot.rivas;

import checkers.CheckersBoard;

public class MyPlayerGame {
    private final CheckersBoard board;
    private final boolean myPlayer;
    private final int level;
    private final CheckersBoard.Player startingPlayer;

    public MyPlayerGame(CheckersBoard board, boolean myPlayer, int level, CheckersBoard.Player startingPlayer) {
        this.board = board;
        this.myPlayer = myPlayer;
        this.level = level;
        this.startingPlayer = startingPlayer;
    }
    public CheckersBoard getBoard() {
        return board;
    }
    public boolean isMyPlayer() {
        return myPlayer;
    }
    public int getLevel() {
        return level;
    }
    public CheckersBoard.Player getStartingPlayer() {
        return startingPlayer;
    }

}
