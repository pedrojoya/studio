package es.iessaladillo.pedrojoya.pr246_navigation.ui.another;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr246_navigation.R;

public class AnotherFragment extends Fragment {

    private TextView lblMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.another_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lblMessage = ViewCompat.requireViewById(view, R.id.lblMessage);
        int counter = AnotherFragmentArgs.fromBundle(getArguments()).getCounter();
        lblMessage.setText(String.valueOf(counter));
    }
}
