package es.iessaladillo.pedrojoya.pr187;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import es.iessaladillo.pedrojoya.pr187.events.PostExecuteEvent;
import es.iessaladillo.pedrojoya.pr187.events.PreExecuteEvent;
import es.iessaladillo.pedrojoya.pr187.events.ProgressEvent;


public class MainFragment extends Fragment {

    private static final String STATE_IS_TASK_STARTED = "STATE_IS_TASK_STARTED";

    private ProgressBar prbBar;
    private TextView lblMessage;
    private ProgressBar prbCircular;
    private Button btnStart;
    private Button btnCancel;

    private boolean isTaskStarted;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View v) {
        prbBar = v.findViewById(R.id.prbBar);
        lblMessage = v.findViewById(R.id.lblMessage);
        prbCircular = v.findViewById(R.id.prbCircular);
        btnStart = v.findViewById(R.id.btnStart);
        btnCancel = v.findViewById(R.id.btnCancel);

        btnStart.setOnClickListener(view -> start());
        btnCancel.setOnClickListener(view -> cancel());
        updateViews(isTaskStarted);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_IS_TASK_STARTED, isTaskStarted);
        super.onSaveInstanceState(outState);
    }

    private void start() {
        TaskService.start(getContext(), getResources().getInteger(R.integer.activity_main_prbBar));
    }

    private void cancel() {
        TaskService.cancel(getContext());
    }

    private void updateViews(boolean isStarted) {
        if (!isStarted) {
            prbBar.setProgress(0);
            lblMessage.setText("");
        }
        btnStart.setEnabled(!isStarted);
        btnCancel.setEnabled(isStarted);
        prbBar.setVisibility(isStarted ? View.VISIBLE : View.INVISIBLE);
        lblMessage.setVisibility(isStarted ? View.VISIBLE : View.INVISIBLE);
        prbCircular.setVisibility(isStarted ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPreExecuteEvent(PreExecuteEvent event) {
        isTaskStarted = true;
        updateViews(true);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgressEvent(ProgressEvent event) {
        lblMessage.setText(getString(R.string.activity_main_lblMessage, event.getStepNumber(),
                getResources().getInteger(R.integer.activity_main_prbBar)));
        prbBar.setProgress(event.getStepNumber());
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPostExecuteEvent(PostExecuteEvent event) {
        isTaskStarted = false;
        updateViews(false);
    }

}
