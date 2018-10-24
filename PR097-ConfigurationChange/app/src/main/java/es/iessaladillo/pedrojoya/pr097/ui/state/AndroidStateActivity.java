package es.iessaladillo.pedrojoya.pr097.ui.state;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.evernote.android.state.State;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr097.R;
import es.iessaladillo.pedrojoya.pr097.data.model.ScoreBoard;

public class AndroidStateActivity extends AppCompatActivity {

    private TextView lblScore;

    // Can't be private or final because AndroidState needs to access it and to change its value
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @State
    ScoreBoard scoreBoard = new ScoreBoard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        /* Not needed it we set StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true)
           in application onCreate()
        StateSaver.restoreInstanceState(this, savedInstanceState);
        */
        setupViews();
        showScore();
    }

    private void setupViews() {
        lblScore = ActivityCompat.requireViewById(this, R.id.lblScore);

        ActivityCompat.requireViewById(this, R.id.btnIncrement).setOnClickListener(v -> increment());
    }

    private void increment() {
        scoreBoard.incrementScore();
        showScore();
    }


    /* Not needed it we set StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true)
       in application onCreate()

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        StateSaver.saveInstanceState(this, outState);
    }

    */

    private void showScore() {
        lblScore.setText(String.valueOf(scoreBoard.getScore()));
    }

    public static void start(@NonNull Context context) {
        context.startActivity(new Intent(context, AndroidStateActivity.class));
    }

}
