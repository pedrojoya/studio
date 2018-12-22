package es.iessaladillo.pedrojoya.pr123.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr123.R;
import es.iessaladillo.pedrojoya.pr123.ui.main.MainActivityViewModel;
import es.iessaladillo.pedrojoya.pr123.ui.main.MainActivityViewModelFactory;

public class InfoFragment extends Fragment {

    private TextView lblLikes;
    private MainActivityViewModel viewModel;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity(), new MainActivityViewModelFactory(R
                .id.mnuOriginal)).get(MainActivityViewModel.class);
        setupViews(requireView());
        observeLikes();
    }

    private void setupViews(View view) {
        lblLikes = ViewCompat.requireViewById(view, R.id.lblLikes);
        ImageView imgLike = ViewCompat.requireViewById(view, R.id.imgLike);

        imgLike.setOnClickListener(v -> viewModel.incrementLikes());
    }

    private void observeLikes() {
        viewModel.getLikes().observe(getViewLifecycleOwner(), this::showLikes);
    }

    private void showLikes(int likes) {
        lblLikes.setText(getResources().getQuantityString(R.plurals.info_likes,
                likes, likes));
    }

}
