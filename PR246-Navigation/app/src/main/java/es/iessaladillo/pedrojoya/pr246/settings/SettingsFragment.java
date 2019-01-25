package es.iessaladillo.pedrojoya.pr246.settings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr246.R;

@SuppressWarnings("WeakerAccess")
public class SettingsFragment extends Fragment {


    public SettingsFragment() { }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

}
