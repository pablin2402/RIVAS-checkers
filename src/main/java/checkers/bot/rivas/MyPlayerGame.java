package checkers.bot.rivas;

import checkers.CheckersBoard;

public class MyPlayerGame {
    public static Build build() {return new Build();}
    private CheckersBoard board;
    private boolean myPlayer;
    private int level;
    private CheckersBoard.Player startingPlayer;

    private MyPlayerGame (){}
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
    public static class  Build {
        private final MyPlayerGame myPlayerGame;
        private Build() {
            myPlayerGame = new MyPlayerGame();
        }

        public MyPlayerGame.Build builder(CheckersBoard board, boolean myPlayer) {
            myPlayerGame.board = board;
            myPlayerGame.myPlayer = myPlayer;
            return this;
        }

        public MyPlayerGame.Build builderTo(int level, CheckersBoard.Player startingPlayer) {
            myPlayerGame.level = level;
            myPlayerGame.startingPlayer = startingPlayer;
            return this;
        }

        public MyPlayerGame build() {
            return myPlayerGame;
        }
    }

}
