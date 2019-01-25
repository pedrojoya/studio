package es.iessaladillo.pedrojoya.pr246.ui.another;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr246.R;

public class SettingsFragment extends Fragment {

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
