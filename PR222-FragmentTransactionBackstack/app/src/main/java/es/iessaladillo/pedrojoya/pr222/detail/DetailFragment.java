package es.iessaladillo.pedrojoya.pr222.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr222.R;

public class DetailFragment extends Fragment {

    // Communication interface.
    public interface Callback {
        void onDetailShown(int position);
    }

    public static final String EXTRA_ITEM = "EXTRA_ITEM";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";

    private TextView lblItem;

    private String mItem;
    private int mPosition;
    private Callback mListener;

    public static DetailFragment newInstance(String item, int position) {
        DetailFragment fragment = new DetailFragment();
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_ITEM, item);
        arguments.putInt(EXTRA_POSITION, position);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainArguments();
    }

    private void obtainArguments() {
        if (getArguments() !=  null) {
            mItem = getArguments().getString(EXTRA_ITEM);
            mPosition = getArguments().getInt(EXTRA_POSITION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement fragment callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
        showItem();
    }

    private void initViews(View view) {
            lblItem = ViewCompat.requireViewById(view, R.id.lblItem);
    }

    private void showItem() {
        lblItem.setText(mItem);
        // Notify activity (needed in case of landscape configuration).
        if (mListener != null) {
            mListener.onDetailShown(mPosition);
        }
    }

}