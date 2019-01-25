package es.iessaladillo.pedrojoya.pr254.ui.secondary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr254.R;

public class SecondaryFragment extends Fragment {

    private String message;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getArguments());
        SecondaryFragmentArgs secondaryFragmentArgs = SecondaryFragmentArgs.fromBundle(getArguments());
        message = secondaryFragmentArgs.getMessage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_secondary, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(requireView());
    }

    private void setupViews(View view) {
        TextView lblMessage = ViewCompat.requireViewById(view, R.id.lblMessage);
        lblMessage.setText(message);
    }

}
