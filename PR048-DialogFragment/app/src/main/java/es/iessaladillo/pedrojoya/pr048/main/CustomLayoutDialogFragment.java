package es.iessaladillo.pedrojoya.pr048.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import es.iessaladillo.pedrojoya.pr048.R;

public class CustomLayoutDialogFragment extends DialogFragment {

    private Callback mListener = null;

    @SuppressWarnings({"EmptyMethod", "UnusedParameters"})
    public interface Callback {
        void onLoginClick(DialogFragment dialog);

        void onCancelClick(DialogFragment dialog);
    }

    @SuppressLint("InflateParams")
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.custom_layout_dialog_title);
        b.setView(LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_login, null));
        b.setPositiveButton(R.string.custom_layout_dialog_positiveButton,
                (dialog, which) -> mListener.onLoginClick(CustomLayoutDialogFragment.this));
        b.setNegativeButton(R.string.custom_layout_dialog_negativeButton,
                (dialog, which) -> mListener.onCancelClick(CustomLayoutDialogFragment.this));
        return b.create();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CustomLayoutDialogFragment.Callback");
        }
    }

}
