package es.iessaladillo.pedrojoya.pr246.detail;


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
import es.iessaladillo.pedrojoya.pr246.R;

@SuppressWarnings("WeakerAccess")
public class DetailFragment extends Fragment {

    private String name;

    public DetailFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getArguments());
        es.iessaladillo.pedrojoya.pr246.detail.DetailFragmentArgs detailFragmentArgs = es.iessaladillo.pedrojoya.pr246.detail.DetailFragmentArgs
            .fromBundle(getArguments());
        name = detailFragmentArgs.getName();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(requireView());
    }

    private void setupViews(View view) {
        TextView lblName = ViewCompat.requireViewById(view, R.id.lblName);

        lblName.setText(name);
    }

}
