package es.iessaladillo.pedrojoya.pr257.settings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr257.R;
import es.iessaladillo.pedrojoya.pr257.databinding.FragmentSettingsBinding;

@SuppressWarnings("WeakerAccess")
public class SettingsFragment extends Fragment {

    public SettingsFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        FragmentSettingsBinding b = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container,
            false);
        return b.getRoot();
    }

}
