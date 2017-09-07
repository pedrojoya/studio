package es.iessaladillo.pedrojoya.pr048.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import es.iessaladillo.pedrojoya.pr048.R;

public class YesNoDialogFragment extends DialogFragment {

    private Callback mListener = null;

    @SuppressWarnings("UnusedParameters")
    public interface Callback {
        void onPositiveButtonClick(DialogFragment dialog);

        void onNegativeButtonClick(DialogFragment dialog);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.yes_no_dialog_title);
        b.setMessage(R.string.yes_no_dialog_message);
        b.setIcon(R.drawable.ic_delete_black_24dp);
        // Al pulsar el botón positivo.
        b.setPositiveButton(android.R.string.yes, (dialog, which) -> mListener.onPositiveButtonClick(YesNoDialogFragment.this));
        b.setNegativeButton(android.R.string.no,
                (dialog, which) -> mListener.onNegativeButtonClick(YesNoDialogFragment.this));
        return b.create();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement YesNoDialogFragment.Callback");
        }
    }

}