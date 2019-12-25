package com.haanhgs.tictactoemvc;

import java.io.Serializable;

public class Move implements Serializable {

    private Player player;
    private int row;
    private int column;
    private GameState state;
    private static final long serialUID = 220479;

    public Move(Player player, int row, int column, GameState state) {
        this.player = player;
        this.row = row;
        this.column = column;
        this.state = state;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
