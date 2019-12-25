package com.haanhgs.tictactoemvc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {

    private List<Move> moves;
    private int currentMove;
    private static final long serialUID = 792204;

    public Game(){
        moves = new ArrayList<>();
        currentMove = 0;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public int getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(int currentMove) {
        this.currentMove = currentMove;
    }
}
