package es.iessaladillo.pedrojoya.pr049.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr049.R;

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

    @SuppressWarnings("ConstantConditions")
    private void obtainArguments() {
        mItem = getArguments().getString(EXTRA_ITEM);
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
            lblItem = view.findViewById(R.id.lblItem);
    }

    public void setItem(String item) {
        mItem = item;
        showItem(mItem);
    }

    private void showItem(String item) {
        lblItem.setText(item);
    }

}