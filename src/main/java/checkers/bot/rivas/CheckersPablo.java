package checkers.bot.rivas;

import checkers.CheckersBoard;
import checkers.CheckersMove;

import java.util.*;

public class CheckersPablo extends CheckersBoard {
    List<CheckersMove> children ;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CheckersPablo that = (CheckersPablo) o;
        return Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), children);
    }


}
