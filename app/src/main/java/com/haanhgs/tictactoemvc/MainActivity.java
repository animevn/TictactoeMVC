package com.haanhgs.tictactoemvc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void reset(){
        tvWinner.setText("");
        tvGroup.setVisibility(View.GONE);
        board.restart();

        for (int i = 0; i < clBoard.getChildCount(); i++){
            Button button = (Button)clBoard.getChildAt(i);
            button.setText("");
        }
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
        if (playerThatMoved != null){
            button.setText(playerThatMoved.toString());
            if (board.getWinner() != null){
                tvWinner.setText(playerThatMoved.toString());
                tvGroup.setVisibility(View.VISIBLE);
            }
        }
    }
}
