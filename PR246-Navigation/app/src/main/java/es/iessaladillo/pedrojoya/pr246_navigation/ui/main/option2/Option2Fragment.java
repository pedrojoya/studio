package es.iessaladillo.pedrojoya.pr246_navigation.ui.main.option2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr246_navigation.R;

public class Option2Fragment extends Fragment {


    private TextView lblMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.option2_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        lblMessage = ViewCompat.requireViewById(view, R.id.lblMessage);
        int counter = Option2FragmentArgs.fromBundle(getArguments()).getCounter();
        lblMessage.setText(String.valueOf(counter));
    }

}
