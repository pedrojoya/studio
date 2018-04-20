package es.iessaladillo.pedrojoya.pr027.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

@SuppressWarnings("unused")
public class YesNoDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "ARG_TITLE";
    private static final String ARG_MESSAGE = "ARG_MESSAGE";
    private static final String ARG_ICON_RES_ID = "ARG_ICON_RES_ID";
    private static final String ARG_YES_RES_ID = "ARG_YES_RES_ID";
    private static final String ARG_NO_RES_ID = "ARG_NO_RES_ID";

    public interface Callback {
        void onPositiveButtonClick(DialogFragment dialog);

        void onNegativeButtonClick(DialogFragment dialog);
    }

    private String title = "title";
    private String message = "message";
    @DrawableRes
    private int iconResId = android.R.drawable.ic_delete;
    @StringRes
    private int yesResId = android.R.string.yes;
    @StringRes
    private int noResId = android.R.string.no;

    private Callback listener = null;

    public static YesNoDialogFragment newInstance(String title, String message,
            @DrawableRes int iconResId) {
        YesNoDialogFragment frg = new YesNoDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_TITLE, title);
        arguments.putString(ARG_MESSAGE, message);
        arguments.putInt(ARG_ICON_RES_ID, iconResId);
        frg.setArguments(arguments);
        return frg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainArguments(getArguments());
    }

    private void obtainArguments(Bundle arguments) {
        if (arguments != null) {
            title = arguments.getString(ARG_TITLE, "title");
            message = arguments.getString(ARG_MESSAGE, "message");
            iconResId = arguments.getInt(ARG_ICON_RES_ID, android.R.drawable.ic_dialog_alert);
            yesResId = arguments.getInt(ARG_YES_RES_ID, android.R.string.yes);
            noResId = arguments.getInt(ARG_NO_RES_ID, android.R.string.no);
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(requireActivity());
        b.setTitle(title);
        b.setMessage(message);
        b.setIcon(iconResId);
        b.setPositiveButton(yesResId,
                (dialog, which) -> listener.onPositiveButtonClick(YesNoDialogFragment.this));
        b.setNegativeButton(noResId,
                (dialog, which) -> listener.onNegativeButtonClick(YesNoDialogFragment.this));
        return b.create();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            listener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implementar OnItemClickListener");
        }
    }

}
