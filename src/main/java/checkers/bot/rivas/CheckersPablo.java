package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;

import java.util.*;

public class CheckersPablo extends CheckersBoard {
    List<CheckersMove> children ;
    public int heuristic() {
        int result = 0;
        System.out.println("caca"+ getBoard()[1][1]);
        for ( int i = 0; i < 8; i++ ) {
            for ( int j = 0; j < 8; j++ ) {
                if ( getBoard()[ i ][ j ] == 'b' ) {
                    result += 2;
                } else if ( getBoard()[ i ][ j ] == 'r' ) {
                    result -= 2;
                } else if (getBoard()[ i ][ j ] == Character.toUpperCase( 'b' ) ) {
                    result += 5;
                } else if (getBoard()[ i ][ j ] == Character.toUpperCase( 'r' ) ) {
                    result -= 5;
                }
            }
        }
        return result;
    }


}
