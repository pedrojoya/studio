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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        setupViews(view);
        viewModel.getClockLiveData().observe(getViewLifecycleOwner(), this::updateTime);
    }

    private void setupViews(View view) {
        lblTime = ViewCompat.requireViewById(view, R.id.lblTime);
        btnStart = ViewCompat.requireViewById(view, R.id.btnStart);

        lblTime.setText(simpleDateFormat.format(new Date()));
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
