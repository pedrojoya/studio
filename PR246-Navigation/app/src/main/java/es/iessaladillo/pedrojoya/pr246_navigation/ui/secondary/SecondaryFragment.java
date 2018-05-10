package es.iessaladillo.pedrojoya.pr246_navigation.ui.secondary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.iessaladillo.pedrojoya.pr246_navigation.R;

public class SecondaryFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.secundary_fragment, container, false);
    }

}
