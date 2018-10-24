package es.iessaladillo.pedrojoya.pr097.ui.save;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr097.R;
import es.iessaladillo.pedrojoya.pr097.data.model.ScoreBoard;

public class SaveActivity extends AppCompatActivity {

    private static final String STATE_SCOREBOARD = "STATE_SCOREBOARD";

    private TextView lblScore;

    private ScoreBoard scoreBoard = new ScoreBoard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        restoreSavedInstanceState(savedInstanceState);
        setupViews();
        showScore();
    }

    private void restoreSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            scoreBoard = savedInstanceState.getParcelable(STATE_SCOREBOARD);
        }
    }

    private void setupViews() {
        lblScore = ActivityCompat.requireViewById(this, R.id.lblScore);

        ActivityCompat.requireViewById(this, R.id.btnIncrement).setOnClickListener(v -> increment());
    }

    private void increment() {
        scoreBoard.incrementScore();
        showScore();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_SCOREBOARD, scoreBoard);
    }

    private void showScore() {
        lblScore.setText(String.valueOf(scoreBoard.getScore()));
    }

    public static void start(@NonNull Context context) {
        context.startActivity(new Intent(context, SaveActivity.class));
    }

}
