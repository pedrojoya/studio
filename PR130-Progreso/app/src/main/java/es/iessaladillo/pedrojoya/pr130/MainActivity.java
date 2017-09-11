package es.iessaladillo.pedrojoya.pr130;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private static final int onPreExecute = 0;
    private static final int onProgressUpdate = 1;
    private static final int onPostExecute = 2;
    private static final int NUMBER_OF_TASKS = 10;

    private ProgressBar prbBar;
    private TextView lblMessage;
    private ProgressBar prbCircular;
    private Button btnStart;

    private MainActivityHandler mMainActivityHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Handler associated with current thread. It will work with the message queue of
        // the current activity.
        mMainActivityHandler = new MainActivityHandler(this);
        initViews();
    }

    private void initViews() {
        prbBar = findViewById(R.id.prbBar);
        lblMessage = findViewById(R.id.lblMessage);
        prbCircular = findViewById(R.id.prbCircular);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(view -> start());
    }

    private void start() {
        btnStart.setEnabled(false);
        (new Thread(new Task(mMainActivityHandler))).start();
    }

    private void showProgress() {
        prbBar.setVisibility(View.VISIBLE);
        lblMessage.setText(getString(R.string.main_activity_task, 0, NUMBER_OF_TASKS));
        lblMessage.setVisibility(View.VISIBLE);
        prbCircular.setVisibility(View.VISIBLE);
    }

    private void updateProgress(int progress) {
        lblMessage.setText(getString(R.string.main_activity_task, progress, NUMBER_OF_TASKS));
        prbBar.setProgress(progress);
    }

    private void showDoneTasks(int tasks) {
        lblMessage.setText(getResources().getQuantityString(R.plurals.main_activity_tasks_done, tasks, tasks));
    }

    private void resetViews() {
        prbBar.setVisibility(View.INVISIBLE);
        prbBar.setProgress(0);
        prbCircular.setVisibility(View.INVISIBLE);
        prbCircular.setProgress(0);
        btnStart.setEnabled(true);
    }

    private static class Task implements Runnable {

        // Handler only has a weak reference to the activity so as not to produce memory leaks.
        private final MainActivityHandler mainActivityHandler;

        public Task(MainActivityHandler mainActivityHandler) {
            this.mainActivityHandler = mainActivityHandler;
        }

        @Override
        public void run() {
            sendInitialMessage();
            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                work();
                sendProgressMessage(i);
            }
            sendFinalMessage();
        }

        private void sendInitialMessage() {
            Message msg = new Message();
            msg.what = onPreExecute;
            mainActivityHandler.sendMessage(msg);
        }

        private void sendProgressMessage(int i) {
            Message msg = new Message();
            msg.what = onProgressUpdate;
            msg.arg1 = i + 1;
            mainActivityHandler.sendMessage(msg);
        }

        private void sendFinalMessage() {
            Message msg = new Message();
            msg.what = onPostExecute;
            msg.arg1 = NUMBER_OF_TASKS;
            mainActivityHandler.sendMessage(msg);
        }

        // Working for 1 second simulation.
        private void work() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    static class MainActivityHandler extends Handler {

        private final WeakReference<MainActivity>
                mActivityWeakReference;

        MainActivityHandler(MainActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message mensaje) {
            MainActivity activity = mActivityWeakReference.get();
            if (activity != null) {
                switch (mensaje.what) {
                    case onPreExecute:
                        activity.showProgress();
                        break;
                    case onProgressUpdate:
                        int progreso = mensaje.arg1;
                        activity.updateProgress(progreso);
                        break;
                    case onPostExecute:
                        int tareas = mensaje.arg1;
                        activity.showDoneTasks(tareas);
                        activity.resetViews();
                        break;
                }
            }
        }

    }

}
