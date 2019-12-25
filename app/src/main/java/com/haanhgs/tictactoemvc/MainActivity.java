package com.haanhgs.tictactoemvc;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.haanhgs.tictactoemvc.GameState.Draw;

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

    private void resetBoard() {
        board.restart();
        handleTextView();
        for (int i = 0; i < clBoard.getChildCount(); i++) {
            ((Button) clBoard.getChildAt(i)).setText("");
        }
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

    private void makeGameMove(Button button){
        if (player != null){
            button.setText(player.toString());
            if (board.getWinner() != null){
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

    @OnClick({R.id.bn1, R.id.bn2, R.id.bn3, R.id.bn4, R.id.bn5, R.id.bn6, R.id.bn7, R.id.bn8,
            R.id.bn9, R.id.bnFirst, R.id.bnBack, R.id.bnForward, R.id.bnLast})
    public void onViewClicked(View view) {
        Button button = (Button)view;
        switch (view.getId()) {
            default:
                onButtonClick(button);
                break;
            case R.id.bnFirst:
                break;
            case R.id.bnBack:
                break;
            case R.id.bnForward:
                break;
            case R.id.bnLast:
                break;
        }
    }
}
