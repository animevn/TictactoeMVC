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
    private Game game;
    private int currentMove;

    public void clearCells(){
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
        game = new Game();
        currentMove = 0;
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

    public void flipSide(){
        currentPlayer = currentPlayer == X ? O : X;
    }

    //when users move back and forth with buttons, if they choose a new move
    //then the game will continue from that move, and all moves that come following
    //that move will be deleted.
    private void deleteMoveAfterCurrentMove(){
        if (currentMove < game.getMoves().size()){
            game.setMoves(game.getMoves().subList(0, currentMove));
            state = InProgress;
        }
    }

    private void addMoveToCurrentGame(Player player, int row, int column){
        if (player != null){
            game.getMoves().add(new Move(player, row, column, state));
            currentMove ++;
            game.setCurrentMove(currentMove);
        }

    }

    public Player makeMove(int row, int column){
        Player player = null;
        if (isCellValid(row, column)){
            deleteMoveAfterCurrentMove();
            cells[row][column].setPlayer(currentPlayer);
            player = currentPlayer;
            if (winningMove(player, row, column)){
                state = HasResult;
                winner = player;
            }else if (state == InProgress && isBoardFull()){
                state = Draw;
            }
            addMoveToCurrentGame(currentPlayer, row, column);
            flipSide();
        }
        return player;
    }

    //need this when rewinding game
    public void clearACell(int row, int column){
        cells[row][column] = new Cell();
    }

    //need this when fast forwarding game
    public void reFillACell(Player player, int row, int column){
        if (isCellEmpty(row, column)) cells[row][column].setPlayer(player);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(int currentMove) {
        this.currentMove = currentMove;
        game.setCurrentMove(currentMove);
    }
}
