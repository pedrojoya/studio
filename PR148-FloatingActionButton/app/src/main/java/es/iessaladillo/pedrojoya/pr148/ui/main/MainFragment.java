package es.iessaladillo.pedrojoya.pr148.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import es.iessaladillo.pedrojoya.pr148.R;
import es.iessaladillo.pedrojoya.pr148.utils.FragmentUtils;

public class MainFragment extends Fragment {

    public MainFragment() { }

    @SuppressWarnings("WeakerAccess")
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(requireView());
    }

    private void setupViews(View view) {
        setupToolbar(view);
        ViewCompat.requireViewById(view, R.id.btnShowDetail).setOnClickListener(v ->
            navigateToDetail());
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = ViewCompat.requireViewById(view, R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    private void navigateToDetail() {
        FragmentUtils.replaceFragmentAddToBackstack(requireFragmentManager(), R.id.flContent,
            DetailFragment.newInstance(), DetailFragment.class.getSimpleName(), DetailFragment
                .class.getSimpleName(), FragmentTransaction.TRANSIT_NONE);
    }

}
