package es.iessaladillo.pedrojoya.pr129.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.iessaladillo.pedrojoya.pr129.R;

public class MainActivity extends AppCompatActivity {

    private TextView lblTime;
    private Button btnStart;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
        viewModel.getClockLiveData().observe(this, this::updateTime);
    }

    private void initViews() {
        lblTime = ActivityCompat.requireViewById(this, R.id.lblTime);
        btnStart = ActivityCompat.requireViewById(this, R.id.btnStart);

        lblTime.setText(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
        btnStart.setText(
                viewModel.isClockRunning() ? R.string.activity_main_btnStop : R.string.activity_main_btnStart);
        btnStart.setOnClickListener(v -> {
            if (btnStart.getText().toString().equals(getString(R.string.activity_main_btnStart))) {
                start();
            } else {
                stop();
            }
        });
    }

    private void start() {
        viewModel.start();
        btnStart.setText(R.string.activity_main_btnStop);
    }

    private void stop() {
        viewModel.stop();
        btnStart.setText(R.string.activity_main_btnStart);
    }

    private void updateTime(String time) {
        lblTime.setText(time);
    }

}
