package es.iessaladillo.pedrojoya.pr048.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import es.iessaladillo.pedrojoya.pr048.R;

public class DirectSelectionDialogFragment extends DialogFragment {

    private Callback mListener = null;

    @SuppressWarnings("UnusedParameters")
    public interface Callback {
        void onItemClick(DialogFragment dialog, int which);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.direct_selection_dialog_shift);
        b.setItems(R.array.shifts, (dialog, which) -> mListener.onItemClick(DirectSelectionDialogFragment.this,
                which));
        b.setIcon(R.drawable.ic_access_time_black_24dp);
        return b.create();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DirectSelectionDialogFragment.Callback");
        }
    }

}