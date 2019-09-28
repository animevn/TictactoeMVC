package com.haanhgs.tictactoemvc;

import static com.haanhgs.tictactoemvc.Player.O;
import static com.haanhgs.tictactoemvc.Player.X;

public class Board {

    private Cell[][]cells = new Cell[3][3];
    private Player winner;
    private GameState state;
    private Player currentTurn;

    private enum GameState{
        Inprogress, Finished
    }

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
       currentTurn = X;
       state = GameState.Inprogress;
    }

    private boolean isOutOfBounds(int idx){
        return idx < 0 || idx > 2;
    }

    private boolean isCellValueAlreadySet(int row, int col){
        return cells[row][col] != null;
    }

    private boolean isValid(int row, int col){
        if (state == GameState.Finished){
            return false;
        }else if (isOutOfBounds(row) || isOutOfBounds(col)){
            return false;
        }else return !isCellValueAlreadySet(row, col);
    }

    private boolean isWinningMoveByPlayer(Player player, int currentRow, int currentCol){
        return (
                cells[currentRow][0].getValue() == player
                && cells[currentRow][1].getValue() == player
                && cells[currentRow][2].getValue() == player
                ||
                cells[0][currentCol].getValue() == player
                && cells[1][currentCol].getValue() == player
                && cells[1][currentCol].getValue() == player
                ||
                cells[0][0].getValue() == player
                && cells[1][1].getValue() == player
                && cells[1][2].getValue() == player
                ||
                cells[0][2].getValue() == player
                && cells[1][1].getValue() == player
                && cells[2][0].getValue() == player
                );
    }

    private void flipSide(){
        currentTurn = currentTurn == X? O : X;
    }


    public Player mark(int row, int col){
        Player playerThatMoved = null;
        if (isValid(row, col)){
            cells[row][col].setValue(currentTurn);
            playerThatMoved = currentTurn;

            if (isWinningMoveByPlayer(currentTurn, row, col)){
                state = GameState.Finished;
                winner = currentTurn;
            }else {
                flipSide();
            }

        }
        return playerThatMoved;
    }

}


























