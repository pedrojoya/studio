package es.iessaladillo.pedrojoya.pr129.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr129.R;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private TextView lblTime;
    private Button btnStart;

    private MainFragmentViewModel viewModel;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

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
        observeRunning();
        observeClock();
    }

    private void setupViews(View view) {
        lblTime = ViewCompat.requireViewById(view, R.id.lblTime);
        btnStart = ViewCompat.requireViewById(view, R.id.btnStart);

        lblTime.setText(simpleDateFormat.format(new Date()));
        btnStart.setOnClickListener(v -> viewModel.startOrStop());
    }

    private void observeRunning() {
        viewModel.getRunning().observe(this, running ->
            btnStart.setText(running ? R.string.main_btnStop : R.string.main_btnStart));
    }

    private void observeClock() {
        viewModel.getClockLiveData().observe(getViewLifecycleOwner(), time -> lblTime.setText(time));
    }

}
