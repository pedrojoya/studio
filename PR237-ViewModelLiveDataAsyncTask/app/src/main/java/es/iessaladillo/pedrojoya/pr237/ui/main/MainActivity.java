package es.iessaladillo.pedrojoya.pr237.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr237.R;

public class MainActivity extends AppCompatActivity {


    private static final int MAX_STEPS = 10;

    private ProgressBar prbBar;
    private TextView lblMessage;
    private ProgressBar prbCircle;
    private Button btnStart;
    private Button btnCancel;

    private MainActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
        viewModel.getTask().observe(this, this::updateViews);
    }

    private void initViews() {
        prbBar = ActivityCompat.requireViewById(this, R.id.prbBar);
        lblMessage = ActivityCompat.requireViewById(this, R.id.lblMessage);
        prbCircle = ActivityCompat.requireViewById(this, R.id.prbCircle);
        btnStart = ActivityCompat.requireViewById(this, R.id.btnStart);
        btnCancel = ActivityCompat.requireViewById(this, R.id.btnCancel);

        btnStart.setOnClickListener(view -> viewModel.startWorking(MAX_STEPS));
        btnCancel.setOnClickListener(view -> cancel());
        updateViews(0);
    }

    private void cancel() {
        viewModel.cancelWorking();
    }

    @SuppressWarnings("SameParameterValue")
    private void updateViews(int step) {
        boolean working = step > 0 && step < MAX_STEPS && viewModel.isWorking();
        if (!working) {
            lblMessage.setText("");
        }
        prbBar.setProgress(step);
        lblMessage.setText(getString(R.string.activity_main_lblMessage, step, MAX_STEPS));
        btnStart.setEnabled(!working);
        btnCancel.setEnabled(working);
        prbBar.setVisibility(working ? View.VISIBLE : View.INVISIBLE);
        lblMessage.setVisibility(working ? View.VISIBLE : View.INVISIBLE);
        prbCircle.setVisibility(working ? View.VISIBLE : View.INVISIBLE);
    }

}
