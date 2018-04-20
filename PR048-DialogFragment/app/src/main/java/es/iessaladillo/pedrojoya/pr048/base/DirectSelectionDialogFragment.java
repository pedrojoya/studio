package es.iessaladillo.pedrojoya.pr048.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class DirectSelectionDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "ARG_TITLE";
    private static final String ARG_ITEMS = "ARG_ITEMS";

    @SuppressWarnings("UnusedParameters")
    public interface Listener {
        void onItemSelected(DialogFragment dialog, int which);
    }

    private Listener listener = null;
    private String title;
    private CharSequence[] items;

    public static DirectSelectionDialogFragment newInstance(String title, CharSequence[] items) {
        DirectSelectionDialogFragment frg = new DirectSelectionDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_TITLE, title);
        arguments.putCharSequenceArray(ARG_ITEMS, items);
        frg.setArguments(arguments);
        return frg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainArguments();
    }


    private void obtainArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            title = arguments.getString(ARG_TITLE);
            items = arguments.getCharSequenceArray(ARG_ITEMS);
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(requireActivity());
        b.setTitle(title);
        b.setItems(items,
                (dialog, which) -> listener.onItemSelected(DirectSelectionDialogFragment.this,
                        which));
        return b.create();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            if (getTargetFragment() != null) {
                listener = (Listener) getTargetFragment();
            } else {
                listener = (Listener) activity;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    "Listener must implement DirectSelectionDialogFragment.Listener");
        }
    }

}