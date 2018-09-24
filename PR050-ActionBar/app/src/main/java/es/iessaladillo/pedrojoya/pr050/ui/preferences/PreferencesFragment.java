package es.iessaladillo.pedrojoya.pr050.ui.preferences;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr050.R;

public class PreferencesFragment extends Fragment {


    public PreferencesFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preferences, parent, false);
    }

    public static Fragment newInstance() {
        return new PreferencesFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setTitle(R.string.preferences_title);
        }
    }

}
