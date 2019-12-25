package com.haanhgs.tictactoemvc;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Move> moves;

    public Game(){
        moves = new ArrayList<>();
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
}
