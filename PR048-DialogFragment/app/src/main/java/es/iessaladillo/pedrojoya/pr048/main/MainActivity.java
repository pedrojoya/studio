package es.iessaladillo.pedrojoya.pr048.main;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr048.R;

import es.iessaladillo.pedrojoya.pr048.main.YesNoDialogFragment.Callback;
import es.iessaladillo.pedrojoya.pr048.data.model.Student;

public class MainActivity extends AppCompatActivity implements
        OnDateSetListener,
        OnTimeSetListener, Callback, DirectSelectionDialogFragment.Callback, SimpleSelectionDialogFragment.Callback, MultipleSelectionDialogFragment.Callback, AdapterDialogFragment.Callback, CustomLayoutDialogFragment.Callback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.btnDatePicker).setOnClickListener(v -> showDatePickerDialog());
        findViewById(R.id.btnTimePicker).setOnClickListener(v -> showTimePickerDialog());
        findViewById(R.id.btnYesNoAlert).setOnClickListener(v -> showYesNoDialog());
        findViewById(R.id.btnDirectSelectionAlert).setOnClickListener(v -> showDirectSelectionDialog());
        findViewById(R.id.btnSimpleSelectionAlert).setOnClickListener(v -> showSimpleSelectionDialog());
        findViewById(R.id.btnMultipleSelectionAlert).setOnClickListener(v -> showMultipleSelectionDialog());
        findViewById(R.id.btnCustomLayoutAlert).setOnClickListener(v -> showCustomLayoutDialog());
        findViewById(R.id.btnAdapterAlert).setOnClickListener(v -> showAdapterDialog());

    }

    private void showDatePickerDialog() {
        (new DatePickerDialogFragment()).show(getSupportFragmentManager(), "DatePickerDialogFragment");
    }

    private void showTimePickerDialog() {
        (new TimePickerDialogFragment()).show(getSupportFragmentManager(), "TimePickerDialogFragment");
    }

    private void showYesNoDialog() {
        (new YesNoDialogFragment()).show(this.getSupportFragmentManager(), "YesNoDialogFragment");
    }

    private void showDirectSelectionDialog() {
        (new DirectSelectionDialogFragment()).show(this.getSupportFragmentManager(),
                "DirectSelectionDialogFragment");
    }

    private void showSimpleSelectionDialog() {
        (new SimpleSelectionDialogFragment()).show(this.getSupportFragmentManager(),
                "SimpleSelectionDialogFragment");
    }

    private void showMultipleSelectionDialog() {
        (new MultipleSelectionDialogFragment()).show(this.getSupportFragmentManager(),
                "MultipleSelectionDialogFragment");
    }

    private void showCustomLayoutDialog() {
        (new CustomLayoutDialogFragment()).show(this.getSupportFragmentManager(),
                "CustomLayoutDialogFragment");
    }

    private void showAdapterDialog() {
        (new AdapterDialogFragment()).show(this.getSupportFragmentManager(),
                "AdapterDialogFragment");
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        Toast.makeText(this, getString(R.string.main_activity_selected,
                String.format("%02d", dayOfMonth) + "/"
                + String.format("%02d", (monthOfYear + 1)) + "/"
                + String.format("%04d", year)), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(this, getString(R.string.main_activity_selected,
                String.format("%02d", hourOfDay) + ":"
                + String.format("%02d", minute)), Toast.LENGTH_SHORT).show();
    }

    // Positive button on YesNoDialog.
    @Override
    public void onPositiveButtonClick(DialogFragment dialog) {
        Toast.makeText(this, getString(R.string.main_activity_user_deleted), Toast.LENGTH_SHORT).show();
    }

    // Negative button on YesNoDialog.
    @Override
    public void onNegativeButtonClick(DialogFragment dialog) {
        Toast.makeText(this, getString(R.string.main_activity_no_delete), Toast.LENGTH_SHORT).show();
    }

    // Selection on DirectSelectionDialog. Receives the index of the selected option.
    @Override
    public void onItemClick(DialogFragment dialog, int which) {
        String[] shifts = getResources().getStringArray(R.array.shifts);
        Toast.makeText(this,
                getString(R.string.main_activity_selected, shifts[which]),
                Toast.LENGTH_SHORT).show();
    }

    // Positive button on MultipleSelectionDialog. Receivas an boolean array indicating each item
    // selection.
    @Override
    public void onPositiveButtonClick(DialogFragment dialog,
            boolean[] optionIsChecked) {
        String[] shifts = getResources().getStringArray(R.array.shifts);
        String mensaje = buildShiftsMessage(optionIsChecked, shifts);
        if (mensaje.equals("")) {
            mensaje = getString(R.string.multiple_selection_dialog_no_shift_selected);
        }
        Toast.makeText(this, getString(R.string.multiple_selection_dialog_selected, mensaje), Toast
                .LENGTH_SHORT).show();
    }

    @NonNull
    private String buildShiftsMessage(boolean[] optionIsChecked, String[] shifts) {
        StringBuilder message = new StringBuilder();
        boolean first = true;
        for (int i = 0; i < optionIsChecked.length; i++) {
            if (optionIsChecked[i]) {
                if (first) {
                    first = false;
                } else {
                    message.append(", ");
                }
                message.append(shifts[i]);
            }
        }
        return message.toString();
    }

    // Positive button on SimpleSelectionDialog. Receives the index of the selected option.
    @Override
    public void onPositiveButtonClick(DialogFragment dialog, int which) {
        String[] shifts = getResources().getStringArray(R.array.shifts);
        Toast.makeText(this, getString(R.string.main_activity_selected, shifts[which]),
                Toast.LENGTH_SHORT).show();
    }

    // Click on item in adapter. Receives selected student.
    @Override
    public void onListItemClick(DialogFragment dialog, Student student) {
        Toast.makeText(this, getString(R.string.main_activity_selected, student.getName()),
                Toast.LENGTH_SHORT).show();
    }

    // Positive button on custom layout dialog.
    @Override
    public void onLoginClick(DialogFragment dialog) {
        Toast.makeText(this, R.string.main_activity_login, Toast.LENGTH_SHORT).show();
    }

    // Neutral button on custom layout dialog.
    @Override
    public void onCancelClick(DialogFragment dialog) {
        // Nothing done.
    }

}