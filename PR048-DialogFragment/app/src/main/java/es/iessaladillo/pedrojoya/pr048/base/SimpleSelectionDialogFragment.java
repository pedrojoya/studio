package es.iessaladillo.pedrojoya.pr048.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

public class SimpleSelectionDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "ARG_TITLE";
    private static final String ARG_ITEMS = "ARG_ITEMS";
    private static final String ARG_CONFIRM_TEXT = "ARG_CONFIRM_TEXT";
    private static final String ARG_DEFAULT_SELECTED_INDEX = "ARG_DEFAULT_SELECTED_INDEX";

    @SuppressWarnings({"UnusedParameters", "EmptyMethod"})
    public interface Listener {
        void onConfirmSelection(DialogInterface dialog, int which);

        void onItemSelected(DialogInterface dialog, int which);
    }

    private Listener listener;
    private String title;
    private CharSequence[] items;
    private String confirmText;
    private int defaultSelectedIndex;

    @SuppressWarnings("SameParameterValue")
    public static SimpleSelectionDialogFragment newInstance(String title, CharSequence[] items,
            String confirmText, int defaultSelectedIndex) {
        SimpleSelectionDialogFragment frg = new SimpleSelectionDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_TITLE, title);
        arguments.putCharSequenceArray(ARG_ITEMS, items);
        arguments.putString(ARG_CONFIRM_TEXT, confirmText);
        arguments.putInt(ARG_DEFAULT_SELECTED_INDEX, defaultSelectedIndex);
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
            defaultSelectedIndex = arguments.getInt(ARG_DEFAULT_SELECTED_INDEX);
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(requireActivity());
        b.setTitle(title);
        b.setSingleChoiceItems(items, defaultSelectedIndex, (dialog, which) -> {
            defaultSelectedIndex = which;
            listener.onItemSelected(dialog, which);
        });
        b.setPositiveButton(confirmText, (dialog, which) -> {
            dialog.dismiss();
            listener.onConfirmSelection(dialog, defaultSelectedIndex);
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
                    "Listener must implement SimpleSelectionDialogFragment.Listener");
        }
    }

}