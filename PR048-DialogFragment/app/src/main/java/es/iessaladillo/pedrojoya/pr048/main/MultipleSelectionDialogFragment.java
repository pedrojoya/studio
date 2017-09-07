package es.iessaladillo.pedrojoya.pr048.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import es.iessaladillo.pedrojoya.pr048.R;

public class MultipleSelectionDialogFragment extends DialogFragment {

    private Callback mListener = null;
    private boolean[] mOptionIsChecked;

    @SuppressWarnings("UnusedParameters")
    public interface Callback {
        void onPositiveButtonClick(DialogFragment dialog, boolean[] optionIsChecked);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.multiple_selection_dialog_shift);
        mOptionIsChecked = new boolean[]{true, false, false};
        b.setMultiChoiceItems(R.array.shifts, mOptionIsChecked, (dialog, which, isChecked) ->
                mOptionIsChecked[which] = isChecked);
        b.setIcon(R.drawable.ic_access_time_black_24dp);
        b.setPositiveButton(R.string.multiple_selection_dialog_positiveButton, (d, arg1) -> {
            d.dismiss();
            mListener.onPositiveButtonClick(MultipleSelectionDialogFragment.this,
                    mOptionIsChecked);
        });
        return b.create();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() + " must implement MultipleSelectionDialogFragment.Callback");
        }
    }

}