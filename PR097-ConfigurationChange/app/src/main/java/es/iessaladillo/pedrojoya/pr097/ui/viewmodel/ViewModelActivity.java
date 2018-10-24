package es.iessaladillo.pedrojoya.pr097.ui.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr097.R;

public class ViewModelActivity extends AppCompatActivity {

    private TextView lblScore;

    private ViewModelActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        viewModel = ViewModelProviders.of(this)
                .get(ViewModelActivityViewModel.class);
        setupViews();
        showScore();
    }

    private void setupViews() {
        lblScore = ActivityCompat.requireViewById(this, R.id.lblScore);

        ActivityCompat.requireViewById(this, R.id.btnIncrement)
                .setOnClickListener(v -> increment());
    }

    private void increment() {
        viewModel.increment();
        showScore();
    }

    private void showScore() {
        lblScore.setText(String.valueOf(viewModel.getScoreBoard().getScore()));
    }

    public static void start(@NonNull Context context) {
        context.startActivity(new Intent(context, ViewModelActivity.class));
    }

}
