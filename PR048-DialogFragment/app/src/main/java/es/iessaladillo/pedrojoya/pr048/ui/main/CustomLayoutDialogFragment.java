package es.iessaladillo.pedrojoya.pr048.ui.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import es.iessaladillo.pedrojoya.pr048.R;

@SuppressWarnings("WeakerAccess")
public class CustomLayoutDialogFragment extends DialogFragment {

    private Listener listener;

    @SuppressWarnings({"EmptyMethod", "UnusedParameters"})
    public interface Listener {
        void onLoginClick(DialogFragment dialog);

        void onCancelClick(DialogFragment dialog);
    }

    @SuppressLint("InflateParams")
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(requireActivity());
        b.setTitle(R.string.custom_layout_dialog_title);
        b.setView(LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_login, null));
        b.setPositiveButton(R.string.custom_layout_dialog_positiveButton,
                (dialog, which) -> listener.onLoginClick(CustomLayoutDialogFragment.this));
        b.setNegativeButton(R.string.custom_layout_dialog_negativeButton,
                (dialog, which) -> listener.onCancelClick(CustomLayoutDialogFragment.this));
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
                    "Listener must implement CustomLayoutDialogFragment.Listener");
        }
    }

}
