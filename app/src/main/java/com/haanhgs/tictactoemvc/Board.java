package com.haanhgs.tictactoemvc;

import static com.haanhgs.tictactoemvc.GameState.Draw;
import static com.haanhgs.tictactoemvc.GameState.HasResult;
import static com.haanhgs.tictactoemvc.GameState.InProgress;
import static com.haanhgs.tictactoemvc.Player.O;
import static com.haanhgs.tictactoemvc.Player.X;

public class Board {

    private final Cell[][] cells = new Cell[3][3];
    private Player winner;
    private Player currentPlayer;
    private GameState state;

    private void clearCells(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                cells[i][j] = new Cell();
            }
        }
    }

    public void restart(){
        clearCells();
        winner = null;
        currentPlayer = X;
        state = InProgress;
    }

    public Board(){
        restart();
    }

    private boolean winningMove(Player player, int row, int column){
        return
                cells[row][0].getPlayer() == player
                && cells[row][1].getPlayer() == player
                && cells[row][2].getPlayer() == player
                || cells[0][column].getPlayer() == player
                && cells[1][column].getPlayer() == player
                && cells[2][column].getPlayer() == player
                || cells[0][0].getPlayer() == player
                && cells[1][1].getPlayer() == player
                && cells[2][2].getPlayer() == player
                || cells[0][2].getPlayer() == player
                && cells[1][1].getPlayer() == player
                && cells[2][0].getPlayer() == player;
    }

    private boolean isCoordinateValid(int coord){
        return coord >= 0 && coord <= 2;
    }

    private boolean isCellEmpty(int row, int column){
        return cells[row][column].getPlayer() == null;
    }

    private boolean isCellValid(int row, int column){
        return
                state == InProgress
                        && isCoordinateValid(row)
                        && isCoordinateValid(column)
                        && isCellEmpty(row, column);
    }

    private boolean isBoardFull(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (cells[i][j].getPlayer() == null) return  false;
            }
        }
        return true;
    }

    private void flipSide(){
        currentPlayer = currentPlayer == X ? O : X;
    }

    public Player makeMove(int row, int column){
        Player player = null;
        if (isCellValid(row, column)){
            cells[row][column].setPlayer(currentPlayer);
            player = currentPlayer;
            if (winningMove(player, row, column)){
                state = HasResult;
                winner = player;
            }else if (state == InProgress && isBoardFull()){
                state = Draw;
            }
            flipSide();
        }
        return player;
    }

}
