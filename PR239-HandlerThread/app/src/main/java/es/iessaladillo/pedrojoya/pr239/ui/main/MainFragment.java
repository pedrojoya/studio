package es.iessaladillo.pedrojoya.pr239.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr239.R;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private TextView lblStockPrice;

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
        setupViews(view);
        viewModel.getStockLiveData().observe(getViewLifecycleOwner(), this::showPrice);
    }

    private void showPrice(Integer price) {
        lblStockPrice.setText(String.valueOf(price));
    }

    private void setupViews(View view) {
        lblStockPrice = ViewCompat.requireViewById(view, R.id.lblStockPrice);
        Button btnStart = ViewCompat.requireViewById(view, R.id.btnStart);
        Button btnStop = ViewCompat.requireViewById(view, R.id.btnStop);

        btnStart.setOnClickListener(v -> viewModel.start());
        btnStop.setOnClickListener(v -> viewModel.stop());
    }

}
