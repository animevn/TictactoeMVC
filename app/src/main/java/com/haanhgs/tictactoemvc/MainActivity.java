package com.haanhgs.tictactoemvc;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.haanhgs.tictactoemvc.GameState.Draw;
import static com.haanhgs.tictactoemvc.GameState.HasResult;
import static com.haanhgs.tictactoemvc.GameState.InProgress;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvResult)
    TextView tvResult;
    @BindView(R.id.bnFirst)
    ImageButton bnFirst;
    @BindView(R.id.bnBack)
    ImageButton bnBack;
    @BindView(R.id.bnForward)
    ImageButton bnForward;
    @BindView(R.id.bnLast)
    ImageButton bnLast;
    @BindView(R.id.clBoard)
    ConstraintLayout clBoard;
    @BindView(R.id.bn1)
    Button bn1;
    @BindView(R.id.bn2)
    Button bn2;
    @BindView(R.id.bn3)
    Button bn3;
    @BindView(R.id.bn4)
    Button bn4;
    @BindView(R.id.bn5)
    Button bn5;
    @BindView(R.id.bn6)
    Button bn6;
    @BindView(R.id.bn7)
    Button bn7;
    @BindView(R.id.bn8)
    Button bn8;
    @BindView(R.id.bn9)
    Button bn9;

    private Board board;
    private Player player;

    private void forcePortraitMode() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void initBoard() {
        board = new Board();
    }

    private void handleTextView() {
        String string = board.getCurrentPlayer().toString() + " to play";
        tvResult.setText(string);
    }

    private void clearButtons(){
        for (int i = 0; i < clBoard.getChildCount(); i++) {
            ((Button) clBoard.getChildAt(i)).setText("");
        }
    }

    private void resetBoard() {
        board.restart();
        handleTextView();
        clearButtons();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        forcePortraitMode();
        initBoard();
        resetBoard();
    }

    private void moveToCurrentMove(int currentMove){
        for (int i = 0; i < currentMove; i++){
            Move move = board.getGame().getMoves().get(i);
            fillButtonText(move);
            board.reFillACell(move.getPlayer(), move.getRow(), move.getColumn());
            board.flipSide();
            board.setState(move.getState());
            handleMoveState(move);
        }
        board.setCurrentMove(currentMove);
    }

    private void loadSaveGame(){
        Game game = Repo.load(this);
        int currentMove = game.getCurrentMove();
        board.setGame(game);
        moveToCurrentMove(currentMove);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSaveGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Repo.save(this, board.getGame());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mniReset){
            resetBoard();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeGameMove(Button button){
        if (player != null){
            button.setText(player.toString());
            if (board.getState() == HasResult){
                String winner = board.getWinner().toString() + " wins!!!";
                tvResult.setText(winner);
            }
            if (board.getState() == Draw){
                tvResult.setText(String.format("%s", "Draw!!!"));
            }
        }
    }

    private void onButtonClick(Button button) {
        String tag = button.getTag().toString();
        int row = Integer.valueOf(tag.substring(0, 1));
        int column = Integer.valueOf(tag.substring(1, 2));
        player = board.makeMove(row, column);
        handleTextView();
        makeGameMove(button);
    }

    private void clearButtonText(Move move){
        String tag = "" + move.getRow() + move.getColumn();
        Button button = clBoard.findViewWithTag(tag);
        button.setText("");
    }

    private void fillButtonText(Move move){
        String tag = "" + move.getRow() + move.getColumn();
        Button button = clBoard.findViewWithTag(tag);
        button.setText(move.getPlayer().toString());
    }

    private void handleButtonBack(){
        int currentMove = board.getCurrentMove();
        if (currentMove > 0){
            Move move = board.getGame().getMoves().get(currentMove - 1);
            clearButtonText(move);
            board.clearACell(move.getRow(), move.getColumn());
            board.setCurrentMove(currentMove - 1);
            board.setState(move.getState());
            board.flipSide();
            handleTextView();
        }
    }

    private void handleMoveState(Move move){
        if (move.getState() == HasResult){
            String winner = move.getPlayer().toString() + " wins!!!";
            tvResult.setText(winner);
        }
        if (move.getState() == Draw){
            tvResult.setText(String.format("%s", "Draw!!!"));
        } else if (move.getState() == InProgress){
            handleTextView();
        }
    }

    private void handleButtonForward(){
        int currentMove = board.getCurrentMove();
        if (currentMove < board.getGame().getMoves().size()){
            Move move = board.getGame().getMoves().get(currentMove);
            fillButtonText(move);
            board.reFillACell(move.getPlayer(), move.getRow(), move.getColumn());
            board.setCurrentMove(currentMove + 1);
            board.setState(move.getState());
            board.flipSide();
            handleMoveState(move);
        }
    }

    private void handleButtonFirst(){
        int currentMove = board.getCurrentMove();
        if (currentMove > 0){
            for (int i = currentMove; i > 0; i--){
                Move move = board.getGame().getMoves().get(i - 1);
                clearButtonText(move);
                board.clearACell(move.getRow(), move.getColumn());
                board.flipSide();
                board.setState(move.getState());
            }
            board.setCurrentMove(0);
            handleTextView();
        }
    }

    private void handleButtonLast(){
        int currentMove = board.getCurrentMove();
        if (currentMove < board.getGame().getMoves().size()){
            for (int i = currentMove; i < board.getGame().getMoves().size(); i++){
                Move move = board.getGame().getMoves().get(i);
                fillButtonText(move);
                board.reFillACell(move.getPlayer(), move.getRow(), move.getColumn());
                board.flipSide();
                board.setState(move.getState());
                handleMoveState(move);
            }
            board.setCurrentMove(board.getGame().getMoves().size());
        }
    }

    @OnClick({R.id.bn1, R.id.bn2, R.id.bn3, R.id.bn4, R.id.bn5, R.id.bn6, R.id.bn7, R.id.bn8,
            R.id.bn9, R.id.bnFirst, R.id.bnBack, R.id.bnForward, R.id.bnLast})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            default:
                Button button = (Button)view;
                onButtonClick(button);
                break;
            case R.id.bnFirst:
                handleButtonFirst();
                break;
            case R.id.bnBack:
                handleButtonBack();
                break;
            case R.id.bnForward:
                handleButtonForward();
                break;
            case R.id.bnLast:
                handleButtonLast();
                break;
        }
    }
}
