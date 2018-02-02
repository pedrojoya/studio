package es.iessaladillo.pedrojoya.pr210.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr210.R;

public class DetailFragment extends Fragment {

    // Communication interface.
    public interface Callback {
        void onDetailShown(String item);
    }

    private TextView lblItem;

    private String mItem;
    private Callback mListener;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        // TODO ENGANCHARSE AL VIEWMODEL DE LA ACTIVIDAD PARA OBSERVAR EL ITEM.
        initViews(getView());
        showItem();
    }

    private void initViews(View view) {
            lblItem = view.findViewById(R.id.lblItem);
    }

    private void showItem() {
        lblItem.setText(mItem);
        // Notify activity (needed in case of landscape configuration).
        if (mListener != null) {
            mListener.onDetailShown(mItem);
        }
    }

}