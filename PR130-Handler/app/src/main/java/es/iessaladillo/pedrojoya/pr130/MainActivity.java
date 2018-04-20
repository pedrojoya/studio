package es.iessaladillo.pedrojoya.pr130;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private static final int onPreExecute = 0;
    private static final int onProgressUpdate = 1;
    private static final int onPostExecute = 2;
    private static final int onCancelled = 3;
    private static final int NUMBER_OF_TASKS = 10;

    private ProgressBar prbBar;
    private TextView lblMessage;
    private ProgressBar prbCircular;
    private Button btnStart;
    private Button btnCancel;

    private MainActivityHandler mainActivityHandler;
    private Thread secundaryThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Handler associated with current thread. It will work with the message queue of
        // the current activity.
        mainActivityHandler = new MainActivityHandler(this);
        initViews();
    }

    private void initViews() {
        prbBar = ActivityCompat.requireViewById(this, R.id.prbBar);
        lblMessage = ActivityCompat.requireViewById(this, R.id.lblMessage);
        prbCircular = ActivityCompat.requireViewById(this, R.id.prbCircular);
        btnStart = ActivityCompat.requireViewById(this, R.id.btnStart);
        btnCancel = ActivityCompat.requireViewById(this, R.id.btnCancel);

        btnStart.setOnClickListener(view -> start());
        btnCancel.setOnClickListener(view -> cancel());
        resetViews();
    }

    private void start() {
        btnStart.setEnabled(false);
        btnCancel.setEnabled(true);
        secundaryThread = (new Thread(new Task(mainActivityHandler)));
        secundaryThread.start();
    }

    private void cancel() {
        if (secundaryThread != null) {
            secundaryThread.interrupt();
        }
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
        lblMessage.setText(
                getResources().getQuantityString(R.plurals.main_activity_tasks_done, tasks, tasks));
    }

    private void resetViews() {
        lblMessage.setText("");
        prbBar.setVisibility(View.INVISIBLE);
        prbBar.setProgress(0);
        prbCircular.setVisibility(View.INVISIBLE);
        prbCircular.setProgress(0);
        btnStart.setEnabled(true);
        btnCancel.setEnabled(false);
    }

    private void showCancelled() {
        Toast.makeText(this, R.string.main_activity_task_cancelled, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        // Try and comment this line. The thread will go on executing but the instance of the
        // activity associated with the handler has no window associated where to show results.
        if (secundaryThread != null) secundaryThread.interrupt();
        super.onDestroy();
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
            for (int i = 0; i < NUMBER_OF_TASKS && !Thread.currentThread().isInterrupted(); i++) {
                if (Thread.currentThread().isInterrupted()) {
                    sendCancelledMessage();
                    return;
                }
                try {
                    work();
                } catch (InterruptedException e) {
                    sendCancelledMessage();
                    return;
                }
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
            Log.d("Mia", "Tarea " + i);
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

        private void sendCancelledMessage() {
            Message msg = new Message();
            msg.what = onCancelled;
            mainActivityHandler.sendMessage(msg);
        }

        // Working for 1 second simulation.
        private void work() throws InterruptedException {
            Thread.sleep(1000);
        }

    }

    static class MainActivityHandler extends Handler {

        private final WeakReference<MainActivity> mActivityWeakReference;

        MainActivityHandler(MainActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message message) {
            MainActivity activity = mActivityWeakReference.get();
            if (activity != null) {
                switch (message.what) {
                    case onPreExecute:
                        activity.showProgress();
                        break;
                    case onProgressUpdate:
                        int progress = message.arg1;
                        Log.d("Mia", "Mensaje tarea " + progress + " recibida");
                        activity.updateProgress(progress);
                        break;
                    case onPostExecute:
                        int tasks = message.arg1;
                        activity.showDoneTasks(tasks);
                        activity.resetViews();
                        break;
                    case onCancelled:
                        activity.showCancelled();
                        activity.resetViews();
                        break;
                }
            }
        }

    }

}
