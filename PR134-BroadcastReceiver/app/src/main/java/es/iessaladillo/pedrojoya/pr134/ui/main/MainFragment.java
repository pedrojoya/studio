package es.iessaladillo.pedrojoya.pr134.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr134.R;
import es.iessaladillo.pedrojoya.pr134.base.BatteryStatus;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private TextView lblLevel;
    private ProgressBar pbLevel;

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
        MainFragmentViewModel viewModel = ViewModelProviders.of(this).get(
            MainFragmentViewModel.class);
        setupViews(requireView());
        observeBatteryStatus(viewModel);
    }

    private void observeBatteryStatus(MainFragmentViewModel viewModel) {
        viewModel.getBatteryStatusLiveData().observe(getViewLifecycleOwner(), this::showBatteryStatus);
    }

    private void setupViews(View view) {
        lblLevel = ViewCompat.requireViewById(view, R.id.lblLevel);
        pbLevel = ViewCompat.requireViewById(view, R.id.pbLevel);
    }

    private void showBatteryStatus(BatteryStatus batteryStatus) {
        String healthMessage;
        if (batteryStatus.hasColdHealth()) healthMessage = getString(
                R.string.main_health_cold);
        else if (batteryStatus.hasDeadHealth()) healthMessage = getString(
                R.string.main_health_dead);
        else if (batteryStatus.hasGoodHealth()) healthMessage = getString(
                R.string.main_health_good);
        else if (batteryStatus.hasOverheatHealth()) healthMessage = getString(
                R.string.main_health_overheat);
        else if (batteryStatus.hasOverVoltageHealth()) healthMessage = getString(
                R.string.main_health_over_voltage);
        else if (batteryStatus.hasUnspecifiedFailureHealth()) healthMessage = getString(
                R.string.main_health_unspecified_failure);
        else healthMessage = getString(R.string.main_health_unknown);

        StringBuilder sb = new StringBuilder();
        if (batteryStatus.isLoading()) {
            sb.append(getString(R.string.main_loading)).append(" ");
            if (batteryStatus.isPluggedToUsb()) {
                sb.append(getString(R.string.main_plugged_usb)).append(" ");
            } else if (batteryStatus.isPluggedToAc()) {
                sb.append(getString(R.string.main_plugged_ac)).append(" ");
            }
        } else {
            sb.append(getString(R.string.main_lblLevel)).append(" ");
        }
        sb.append("(").append(batteryStatus.getLevel()).append("%)").append(" ").append(
                healthMessage);

        lblLevel.setText(sb.toString());
        pbLevel.setProgress(batteryStatus.getLevel());
    }

}
