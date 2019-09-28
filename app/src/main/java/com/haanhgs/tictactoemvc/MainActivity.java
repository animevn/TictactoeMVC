package com.haanhgs.tictactoemvc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import static com.haanhgs.tictactoemvc.GameState.Draw;

public class MainActivity extends AppCompatActivity {

    private Board board;
    private TextView tvWinner;
    private TextView tvGroup;
    private ConstraintLayout clBoard;

    private void initViews(){
        board = new Board();
        tvWinner = findViewById(R.id.tvWinner);
        tvGroup = findViewById(R.id.tvViewGroup);
        clBoard = findViewById(R.id.clBoard);
    }

    private void reset(){
        board.restart();
        String string = board.getCurrentTurn().toString() + " to move";
        tvWinner.setText(string);
        tvGroup.setVisibility(View.GONE);


        for (int i = 0; i < clBoard.getChildCount(); i++){
            Button button = (Button)clBoard.getChildAt(i);
            button.setText("");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        reset();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mniReset){
            reset();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCellClicked(View view){
        Button button = (Button) view;
        String tag = button.getTag().toString();
        int row = Integer.valueOf(tag.substring(0, 1));
        int col = Integer.valueOf(tag.substring(1, 2));
        Player playerThatMoved = board.mark(row, col);
        String string = board.getCurrentTurn().toString() + " to move";
        tvWinner.setText(string);
        if (playerThatMoved != null){
            button.setText(playerThatMoved.toString());
            if (board.getWinner() != null){
                tvWinner.setText(playerThatMoved.toString());
                tvGroup.setVisibility(View.VISIBLE);
            }
            if (board.getState() == Draw){
                tvWinner.setText(String.format("%s", "Game draw"));
            }
        }
    }
}
