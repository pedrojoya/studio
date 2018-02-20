package es.iessaladillo.pedrojoya.pr030;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class PickOrCaptureDialogFragment extends DialogFragment {

    private Listener mListener = null;

    @SuppressWarnings("UnusedParameters")
    public interface Listener {
        void onItemClick(DialogFragment dialog, int which);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle(R.string.elige_opcion);
        b.setItems(R.array.pick_or_capture_opciones, new DialogInterface.OnClickListener() {
            // Cuando se selecciona el elemento.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onItemClick(PickOrCaptureDialogFragment.this,
                        which);
            }
        });
        b.setIcon(R.mipmap.ic_launcher);
        return b.create();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (Listener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " debe implementar PickOrCaptureDialogFragment.Listener");
        }
    }

}
