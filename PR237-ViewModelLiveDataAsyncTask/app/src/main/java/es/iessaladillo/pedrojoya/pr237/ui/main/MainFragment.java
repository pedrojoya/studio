package es.iessaladillo.pedrojoya.pr237.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr237.R;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private static final int MAX_STEPS = 10;

    private ProgressBar prbBar;
    private TextView lblMessage;
    private ProgressBar prbCircle;
    private Button btnStart;
    private Button btnCancel;

    private MainFragmentViewModel viewModel;

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        setuptViews(view);
        viewModel.getTask().observe(getViewLifecycleOwner(), this::updateViews);
    }

    private void setuptViews(View view) {
        prbBar = ViewCompat.requireViewById(view, R.id.prbBar);
        lblMessage = ViewCompat.requireViewById(view, R.id.lblMessage);
        prbCircle = ViewCompat.requireViewById(view, R.id.prbCircle);
        btnStart = ViewCompat.requireViewById(view, R.id.btnStart);
        btnCancel = ViewCompat.requireViewById(view, R.id.btnCancel);

        btnStart.setOnClickListener(v -> viewModel.startWorking(MAX_STEPS));
        btnCancel.setOnClickListener(v -> cancel());
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
