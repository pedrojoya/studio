package es.iessaladillo.pedrojoya.pr048.base;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment {

    private static final String ARG_YEAR = "ARG_YEAR";
    private static final String ARG_MONTH = "ARG_MONTH";
    private static final String ARG_DAY = "ARG_DAY";

    private DatePickerDialog.OnDateSetListener listener;
    private int year;
    private int month;
    private int day;

    @SuppressWarnings("unused")
    public static DatePickerDialogFragment newInstance(int year, int month, int day) {
        DatePickerDialogFragment frg = new DatePickerDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_YEAR, year);
        arguments.putInt(ARG_MONTH, month);
        arguments.putInt(ARG_DAY, day);
        frg.setArguments(arguments);
        return frg;
    }

    public static DatePickerDialogFragment newInstance() {
        return new DatePickerDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Default: today.
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        obtainArguments();
    }

    private void obtainArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            year = arguments.getInt(ARG_YEAR);
            month = arguments.getInt(ARG_MONTH);
            day = arguments.getInt(ARG_DAY);
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(requireActivity(), listener, year, month, day);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            if (getTargetFragment() != null) {
                listener = (OnDateSetListener) getTargetFragment();
            } else {
                listener = (OnDateSetListener) activity;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException("Listener must implement DatePickerDialogFragment.OnDateSetListener");
        }
    }

}