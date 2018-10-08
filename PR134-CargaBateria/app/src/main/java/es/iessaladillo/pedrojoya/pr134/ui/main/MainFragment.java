package es.iessaladillo.pedrojoya.pr134.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import es.iessaladillo.pedrojoya.pr134.R;
import es.iessaladillo.pedrojoya.pr134.base.BatteryStatus;
import es.iessaladillo.pedrojoya.pr134.utils.ToastUtils;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private TextView lblLevel;
    private ProgressBar pbLevel;
    private Button btnNews;

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
        MainFragmentViewModel viewModel = ViewModelProviders.of(this,
                new MainFragmentViewModelFactory(requireContext().getApplicationContext())).get(
                MainFragmentViewModel.class);
        setupViews(view);
        viewModel.getBatteryStatusLiveData().observe(getViewLifecycleOwner(),
                this::showBatteryStatus);
        viewModel.getConnectionStatusLiveData().observe(getViewLifecycleOwner(),
                connectionStatus -> btnNews.setEnabled(connectionStatus.isConnected()));
    }

    private void setupViews(View view) {
        lblLevel = ViewCompat.requireViewById(view, R.id.lblLevel);
        pbLevel = ViewCompat.requireViewById(view, R.id.pbLevel);
        btnNews = ViewCompat.requireViewById(view, R.id.btnNews);

        btnNews.setOnClickListener(v -> showNews());
    }

    private void showNews() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.elpais.com")));
        } catch (ActivityNotFoundException e) {
            ToastUtils.toast(requireContext(), getString(R.string.main_no_activity_to_show_news));
        }
    }

    private void showBatteryStatus(BatteryStatus batteryStatus) {
        String healthMessage;
        if (batteryStatus.hasColdHealth()) healthMessage = getString(
                R.string.main_activity_health_cold);
        else if (batteryStatus.hasDeadHealth()) healthMessage = getString(
                R.string.main_activity_health_dead);
        else if (batteryStatus.hasGoodHealth()) healthMessage = getString(
                R.string.main_activity_health_good);
        else if (batteryStatus.hasOverheatHealth()) healthMessage = getString(
                R.string.main_activity_health_overheat);
        else if (batteryStatus.hasOverVoltageHealth()) healthMessage = getString(
                R.string.main_activity_health_over_voltage);
        else if (batteryStatus.hasUnspecifiedFailureHealth()) healthMessage = getString(
                R.string.main_activity_health_unspecified_failure);
        else healthMessage = getString(R.string.main_activity_health_unknown);

        StringBuilder sb = new StringBuilder();
        if (batteryStatus.isLoading()) {
            sb.append(getString(R.string.main_activity_loading)).append(" ");
            if (batteryStatus.isPluggedToUsb()) {
                sb.append(getString(R.string.main_activity_plugged_usb)).append(" ");
            } else if (batteryStatus.isPluggedToAc()) {
                sb.append(getString(R.string.main_activity_plugged_ac)).append(" ");
            }
        } else {
            sb.append(getString(R.string.activity_main_lblLevel)).append(" ");
        }
        sb.append("(").append(batteryStatus.getLevel()).append("%)").append(" ").append(
                healthMessage);

        lblLevel.setText(sb.toString());
        pbLevel.setProgress(batteryStatus.getLevel());
    }

}
