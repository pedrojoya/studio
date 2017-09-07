package es.iessaladillo.pedrojoya.pr048.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import es.iessaladillo.pedrojoya.pr048.R;

public class SimpleSelectionDialogFragment extends DialogFragment {

    private Callback mListener = null;
    private int mSelectedShift = 0;

    @SuppressWarnings("UnusedParameters")
    public interface Callback {
        void onPositiveButtonClick(DialogFragment dialog, int which);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.simple_selection_dialog_shift);
        b.setSingleChoiceItems(R.array.shifts, mSelectedShift,
                (dialog, which) -> mSelectedShift = which);
        b.setIcon(R.drawable.ic_access_time_black_24dp);
        b.setPositiveButton(R.string.simple_selection_dialog_positiveButton, (d, arg1) -> {
            d.dismiss();
            mListener.onPositiveButtonClick(
                    SimpleSelectionDialogFragment.this, mSelectedShift);
        });
        return b.create();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SimpleSelectionDialogFragment.Callback");
        }
    }

}