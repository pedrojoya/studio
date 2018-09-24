package es.iessaladillo.pedrojoya.pr222.ui.detail;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr222.R;

public class DetailFragment extends Fragment {

    // Communication interface.
    public interface Callback {
        void onDetailShown(String item, long key);
    }

    // El fragmento debe conocer cuál es su key de selección, para si
    // se hace back el fragmento de detalle cargado pueda informar a la actividad
    // sobre a qué elemento corresponde, de manera que ésta pueda seleccionarlo
    // en la lista del fragmento principal.

    public static final String EXTRA_ITEM = "EXTRA_ITEM";
    public static final String EXTRA_KEY = "EXTRA_KEY";

    private TextView lblItem;

    private String item;
    private long key;
    private Callback listener;

    public long getKey() {
        return key;
    }

    public static DetailFragment newInstance(String item, long key) {
        DetailFragment fragment = new DetailFragment();
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_ITEM, item);
        arguments.putLong(EXTRA_KEY, key);
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
            item = getArguments().getString(EXTRA_ITEM);
            key = getArguments().getLong(EXTRA_KEY);
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
            listener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement fragment callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
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
        lblItem.setText(item);
        // Notify activity (needed in case of landscape configuration).
        if (listener != null) {
            listener.onDetailShown(item, key);
        }
    }



}