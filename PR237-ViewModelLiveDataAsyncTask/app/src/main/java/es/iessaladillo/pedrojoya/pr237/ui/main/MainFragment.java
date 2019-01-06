package es.iessaladillo.pedrojoya.pr237.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr237.R;
import es.iessaladillo.pedrojoya.pr237.base.Event;
import es.iessaladillo.pedrojoya.pr237.base.Resource;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        setupViews(requireView());
        observeWorkingTask();
    }

    private void setupViews(View view) {
        prbBar = ViewCompat.requireViewById(view, R.id.prbBar);
        lblMessage = ViewCompat.requireViewById(view, R.id.lblMessage);
        prbCircle = ViewCompat.requireViewById(view, R.id.prbCircle);
        btnStart = ViewCompat.requireViewById(view, R.id.btnStart);
        btnCancel = ViewCompat.requireViewById(view, R.id.btnCancel);

        btnStart.setOnClickListener(v -> viewModel.startWorking(MAX_STEPS));
        btnCancel.setOnClickListener(v -> viewModel.cancelWorking());
    }

    private void observeWorkingTask() {
        viewModel.getWorkingTask().observe(getViewLifecycleOwner(), resource -> {
            updateViews(resource);
            if (resource.hasError()) {
                showError(resource.getException());
            } else if (resource.hasSuccess()) {
                showSuccess(resource.getData());
            }
        });
    }

    private void updateViews(Resource<Event<String>> resource) {
        lblMessage.setText(resource.isLoading() ? getString(R.string.main_lblMessage,
            resource.getProgress(), MAX_STEPS) : "");
        btnStart.setEnabled(!resource.isLoading());
        btnCancel.setEnabled(resource.isLoading());
        prbBar.setVisibility(resource.isLoading() ? View.VISIBLE : View.INVISIBLE);
        if (resource.isLoading()) {
            prbBar.setProgress(resource.getProgress());
        }
        lblMessage.setVisibility(resource.isLoading() ? View.VISIBLE : View.INVISIBLE);
        prbCircle.setVisibility(resource.isLoading() ? View.VISIBLE : View.INVISIBLE);
    }

    private void showError(Event<Exception> exceptionEvent) {
        Exception exception = exceptionEvent.getContentIfNotHandled();
        if (exception != null) {
            Toast.makeText(requireContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showSuccess(Event<String> messageEvent) {
        String message = messageEvent.getContentIfNotHandled();
        if (message != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

}
