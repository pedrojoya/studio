package es.iessaladillo.pedrojoya.pr246.another;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr246.R;

@SuppressWarnings("WeakerAccess")
public class AnotherFragment extends Fragment {

    private AnotherFragmentViewModel viewModel;
    private TextView lblName;

    public AnotherFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_another, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(requireActivity().getIntent().getExtras());
        viewModel = ViewModelProviders.of(this,
            new AnotherFragmentViewModelFactory(requireActivity().getIntent().getExtras())).get(
            AnotherFragmentViewModel.class);
        setupViews(requireView());
        observeName();
    }

    private void setupViews(View view) {
        lblName = ViewCompat.requireViewById(view, R.id.lblName);
    }

    private void observeName() {
        viewModel.getName().observe(getViewLifecycleOwner(), name -> lblName.setText(name));
    }

}
