package checkers.bot.rivas;

import checkers.CheckersBoard;

public class MyPlayerGame {
    private CheckersBoard board;
    private boolean myPlayer;
    private int level;
    private CheckersBoard.Player startingPlayer;

    public MyPlayerGame(CheckersBoard board, boolean myPlayer, int level, CheckersBoard.Player startingPlayer) {
        this.board = board;
        this.myPlayer = myPlayer;
        this.level = level;
        this.startingPlayer = startingPlayer;
    }

    public MyPlayerGame(CheckersBoard board, boolean myPlayer, int level) {
        this.board = board;
        this.myPlayer = myPlayer;
        this.level = level;
    }

    public CheckersBoard getBoard() {
        return board;
    }

    public void setBoard(CheckersBoard board) {
        this.board = board;
    }

    public boolean isMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(boolean myPlayer) {
        this.myPlayer = myPlayer;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public CheckersBoard.Player getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(CheckersBoard.Player startingPlayer) {
        this.startingPlayer = startingPlayer;
    }
}
