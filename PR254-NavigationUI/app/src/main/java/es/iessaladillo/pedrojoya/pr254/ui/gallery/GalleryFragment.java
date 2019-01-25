package es.iessaladillo.pedrojoya.pr254.ui.gallery;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr254.R;

public class GalleryFragment extends Fragment {

    private GalleryFragmentViewModel viewModel;
    private TextView lblValue;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, new GalleryFragmentViewModelFactory()).get
            (GalleryFragmentViewModel.class);
        setupViews(requireView());
        observeValue();
    }

    private void observeValue() {
        viewModel.getValue().observe(getViewLifecycleOwner(), value ->
            lblValue.setText(String.valueOf(value)));
    }

    private void setupViews(View view) {
        lblValue = ViewCompat.requireViewById(view, R.id.lblValue);
        ViewCompat.requireViewById(view, R.id.btnIncrement).setOnClickListener(v -> viewModel.incrementValue());
    }

}
