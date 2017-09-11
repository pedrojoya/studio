package es.iessaladillo.pedrojoya.pr129;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView lblTime;
    private Button btnStart;

    private Thread secundaryThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        lblTime = this.findViewById(R.id.lblTime);
        btnStart = this.findViewById(R.id.btnStart);

        lblTime.setText(new SimpleDateFormat("HH:mm:ss",
                Locale.getDefault()).format(new Date()));
        btnStart.setOnClickListener(v -> {
            if (btnStart.getText().toString().equals(getString(R.string.activity_main_btnStart))) {
                start();
            } else {
                stop();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // We need to stop to avoid memory leak.
        stop();
    }

    private void start() {
        secundaryThread = new Thread(new Clock());
        secundaryThread.start();
        btnStart.setText(R.string.main_activity_stop);
    }

    private void updateTime(String cadena) {
        lblTime.setText(cadena);
    }

    private void stop() {
        secundaryThread.interrupt();
        btnStart.setText(R.string.activity_main_btnStart);
    }

    private class Clock implements Runnable {

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                final String time = simpleDateFormat.format(new Date());
                Runnable task = () -> updateTime(time);
                runOnUiThread(task);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Interrupted while sleeping.
                    return;
                }
            }
        }

    }

}
