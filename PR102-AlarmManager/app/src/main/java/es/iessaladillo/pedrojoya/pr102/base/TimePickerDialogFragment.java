package es.iessaladillo.pedrojoya.pr102.base;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerDialogFragment extends DialogFragment {

    private static final String ARG_HOURS = "ARG_HOURS";
    private static final String ARG_MINUTES = "ARG_MINUTES";
    private static final String ARG_IES_24_HOURS = "ARG_IES_24_HOURS";

    private OnTimeSetListener listener;
    private int hours;
    private int minutes;
    private boolean is24Hour;

    @SuppressWarnings("unused")
    public static DatePickerDialogFragment newInstance(int hours, int minutes, boolean is24Hour) {
        DatePickerDialogFragment frg = new DatePickerDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_HOURS, hours);
        arguments.putInt(ARG_MINUTES, minutes);
        arguments.putBoolean(ARG_IES_24_HOURS, is24Hour);
        frg.setArguments(arguments);
        return frg;
    }

    public static TimePickerDialogFragment newInstance() {
        return new TimePickerDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Default: current time in 24h.
        Calendar calendar = Calendar.getInstance();
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        is24Hour = true;
        obtainArguments();
    }

    private void obtainArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            hours = arguments.getInt(ARG_HOURS);
            minutes = arguments.getInt(ARG_MINUTES);
            is24Hour = arguments.getBoolean(ARG_IES_24_HOURS);
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(requireActivity(), listener, hours, minutes, is24Hour);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            if (getTargetFragment() != null) {
                listener = (OnTimeSetListener) getTargetFragment();
            } else {
                listener = (OnTimeSetListener) activity;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    "Listener must implement TimePickerDialog.OnTimeSetListener");
        }
    }

}