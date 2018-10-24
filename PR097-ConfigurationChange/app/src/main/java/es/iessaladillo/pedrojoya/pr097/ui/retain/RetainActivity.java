package es.iessaladillo.pedrojoya.pr097.ui.retain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr097.R;
import es.iessaladillo.pedrojoya.pr097.data.model.ScoreBoard;

@SuppressWarnings("FieldCanBeLocal")
public class RetainActivity extends AppCompatActivity {

    private TextView lblScore;

    private State state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        restoreLastCustomNonConfigurationIntance();
        setupViews();
        showScore();
    }

    private void restoreLastCustomNonConfigurationIntance() {
        state = (State) getLastCustomNonConfigurationInstance();
        if (state == null) {
            state = new State();
        }
    }

    private void setupViews() {
        lblScore = ActivityCompat.requireViewById(this, R.id.lblScore);

        ActivityCompat.requireViewById(this, R.id.btnIncrement).setOnClickListener(v -> increment());
    }

    private void increment() {
        state.getScoreBoard().incrementScore();
        showScore();
    }

    @Override
    public State onRetainCustomNonConfigurationInstance() {
        return state;
    }

    private void showScore() {
        lblScore.setText(String.valueOf(state.getScoreBoard().getScore()));
    }

    public static void start(@NonNull Context context) {
        context.startActivity(new Intent(context, RetainActivity.class));
    }

    private static class State {

        private final ScoreBoard scoreBoard = new ScoreBoard();

        @NonNull
        ScoreBoard getScoreBoard() {
            return scoreBoard;
        }

    }

}
