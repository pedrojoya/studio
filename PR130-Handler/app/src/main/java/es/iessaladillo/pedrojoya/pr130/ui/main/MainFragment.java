package es.iessaladillo.pedrojoya.pr130.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr130.R;
import es.iessaladillo.pedrojoya.pr130.utils.ToastUtils;

public class MainFragment extends Fragment {

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

    private MainFragmentHandler mainActivityHandler;
    private Thread secundaryThread;

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Handler associated with current thread. It will work with the message queue of
        // the current activity.
        mainActivityHandler = new MainFragmentHandler(this);
        setupViews(view);
    }

    private void setupViews(View view) {
        prbBar = ViewCompat.requireViewById(view, R.id.prbBar);
        lblMessage = ViewCompat.requireViewById(view, R.id.lblMessage);
        prbCircular = ViewCompat.requireViewById(view, R.id.prbCircular);
        btnStart = ViewCompat.requireViewById(view, R.id.btnStart);
        btnCancel = ViewCompat.requireViewById(view, R.id.btnCancel);

        btnStart.setOnClickListener(v -> start());
        btnCancel.setOnClickListener(v -> cancel());
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
        ToastUtils.toast(requireContext(), getString(R.string.main_activity_task_cancelled));
    }

    @Override
    public void onDestroy() {
        // Try and comment this line. The thread will go on executing but the instance of the
        // activity associated with the handler has no window associated where to show results.
        if (secundaryThread != null) secundaryThread.interrupt();
        super.onDestroy();
    }

    private static class Task implements Runnable {

        // Handler only has a weak reference to the activity so as not to produce memory leaks.
        private final MainFragmentHandler mainFragmentHandler;

        public Task(MainFragmentHandler mainFragmentHandler) {
            this.mainFragmentHandler = mainFragmentHandler;
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
            mainFragmentHandler.sendMessage(msg);
        }

        private void sendProgressMessage(int i) {
            Log.d("Mia", "Tarea " + i);
            Message msg = new Message();
            msg.what = onProgressUpdate;
            msg.arg1 = i + 1;
            mainFragmentHandler.sendMessage(msg);
        }

        private void sendFinalMessage() {
            Message msg = new Message();
            msg.what = onPostExecute;
            msg.arg1 = NUMBER_OF_TASKS;
            mainFragmentHandler.sendMessage(msg);
        }

        private void sendCancelledMessage() {
            Message msg = new Message();
            msg.what = onCancelled;
            mainFragmentHandler.sendMessage(msg);
        }

        // Working for 1 second simulation.
        private void work() throws InterruptedException {
            Thread.sleep(1000);
        }

    }

    static class MainFragmentHandler extends Handler {

        private final WeakReference<MainFragment> mainFragmentWeakReference;

        MainFragmentHandler(MainFragment mainFragment) {
            mainFragmentWeakReference = new WeakReference<>(mainFragment);
        }

        @Override
        public void handleMessage(Message message) {
            MainFragment mainFragment = mainFragmentWeakReference.get();
            if (mainFragment != null && mainFragment.isVisible()) {
                switch (message.what) {
                    case onPreExecute:
                        mainFragment.showProgress();
                        break;
                    case onProgressUpdate:
                        int progress = message.arg1;
                        mainFragment.updateProgress(progress);
                        break;
                    case onPostExecute:
                        int tasks = message.arg1;
                        mainFragment.showDoneTasks(tasks);
                        mainFragment.resetViews();
                        break;
                    case onCancelled:
                        mainFragment.showCancelled();
                        mainFragment.resetViews();
                        break;
                }
            }
        }

    }

}
