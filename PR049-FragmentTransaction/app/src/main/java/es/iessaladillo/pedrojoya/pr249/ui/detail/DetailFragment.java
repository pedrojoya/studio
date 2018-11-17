package es.iessaladillo.pedrojoya.pr249.ui.detail;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr249.R;

public class DetailFragment extends Fragment {

    public static final String EXTRA_ITEM = "EXTRA_ITEM";

    private TextView lblItem;

    private String mItem;

    public static DetailFragment newInstance(String item) {
        DetailFragment fragment = new DetailFragment();
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_ITEM, item);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainArguments();
    }

    private void obtainArguments() {
        if (getArguments() != null) {
            mItem = getArguments().getString(EXTRA_ITEM);
        }
        if (mItem == null) {
            mItem = getString(R.string.main_activity_no_item);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
        showItem(mItem);
    }

    private void initViews(View view) {
        lblItem = ViewCompat.requireViewById(view, R.id.lblItem);
    }

    public void setItem(String item) {
        mItem = item;
        showItem(mItem);
    }

    private void showItem(String item) {
        lblItem.setText(item);
    }

}