package es.iessaladillo.pedrojoya.pr048.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class MultipleSelectionDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "ARG_TITLE";
    private static final String ARG_ITEMS = "ARG_ITEMS";
    private static final String ARG_CONFIRM_TEXT = "ARG_CONFIRM_TEXT";
    private static final String ARG_DEFAULT_SELECTED_INDEXES = "ARG_DEFAULT_SELECTED_INDEXES";

    @SuppressWarnings({"UnusedParameters", "EmptyMethod"})
    public interface Listener {
        void onConfirmSelection(DialogInterface dialog, boolean[] itemsSelectedState);

        void onItemClick(DialogInterface dialog, int which, boolean isChecked);
    }

    private Listener listener;
    private String title;
    private CharSequence[] items;
    private String confirmText;
    private boolean[] defaultItemsSelectedState;

    public static MultipleSelectionDialogFragment newInstance(String title, CharSequence[] items,
            String confirmText, boolean[] defaultItemsSelectedState) {
        MultipleSelectionDialogFragment frg = new MultipleSelectionDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_TITLE, title);
        arguments.putCharSequenceArray(ARG_ITEMS, items);
        arguments.putString(ARG_CONFIRM_TEXT, confirmText);
        arguments.putBooleanArray(ARG_DEFAULT_SELECTED_INDEXES, defaultItemsSelectedState);
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
            confirmText = arguments.getString(ARG_CONFIRM_TEXT);
            defaultItemsSelectedState = arguments.getBooleanArray(ARG_DEFAULT_SELECTED_INDEXES);
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(requireActivity());
        b.setTitle(title);
        b.setMultiChoiceItems(items, defaultItemsSelectedState,
                (dialog, which, isChecked) -> {
                    defaultItemsSelectedState[which] = isChecked;
                    listener.onItemClick(dialog, which, isChecked);
                });
        b.setPositiveButton(confirmText, (dialog, which) -> {
            dialog.dismiss();
            listener.onConfirmSelection(dialog, defaultItemsSelectedState);
        });
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
                    "Listener must implement MultipleSelectionDialogFragment.Listener");
        }
    }

}