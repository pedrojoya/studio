package es.iessaladillo.pedrojoya.pr048.ui.main;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import es.iessaladillo.pedrojoya.pr048.R;
import es.iessaladillo.pedrojoya.pr048.ToastUtils;
import es.iessaladillo.pedrojoya.pr048.base.DatePickerDialogFragment;
import es.iessaladillo.pedrojoya.pr048.base.DirectSelectionDialogFragment;
import es.iessaladillo.pedrojoya.pr048.base.MultipleSelectionDialogFragment;
import es.iessaladillo.pedrojoya.pr048.base.SimpleSelectionDialogFragment;
import es.iessaladillo.pedrojoya.pr048.base.TimePickerDialogFragment;
import es.iessaladillo.pedrojoya.pr048.base.YesNoDialogFragment;
import es.iessaladillo.pedrojoya.pr048.data.model.Student;

public class MainActivity extends AppCompatActivity implements OnDateSetListener,
        OnTimeSetListener, YesNoDialogFragment.Listener, DirectSelectionDialogFragment.Listener,
        SimpleSelectionDialogFragment.Listener, MultipleSelectionDialogFragment.Listener,
        StudentsDialogFragment.Callback, CustomLayoutDialogFragment.Listener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        ActivityCompat.requireViewById(this, R.id.btnDatePicker).setOnClickListener(v -> showDatePickerDialog());
        ActivityCompat.requireViewById(this, R.id.btnTimePicker).setOnClickListener(v -> showTimePickerDialog());
        ActivityCompat.requireViewById(this, R.id.btnYesNoAlert).setOnClickListener(v -> showConfirmDeletionDialog());
        ActivityCompat.requireViewById(this, R.id.btnDirectSelectionAlert).setOnClickListener(
                v -> showDirectSelectionDialog());
        ActivityCompat.requireViewById(this, R.id.btnSimpleSelectionAlert).setOnClickListener(
                v -> showSimpleSelectionDialog());
        ActivityCompat.requireViewById(this, R.id.btnMultipleSelectionAlert).setOnClickListener(
                v -> showMultipleSelectionDialog());
        ActivityCompat.requireViewById(this, R.id.btnCustomLayoutAlert).setOnClickListener(v -> showCustomLayoutDialog());
        ActivityCompat.requireViewById(this, R.id.btnAdapterAlert).setOnClickListener(v -> showAdapterDialog());

    }

    private void showDatePickerDialog() {
        DatePickerDialogFragment.newInstance().show(getSupportFragmentManager(),
                "DatePickerDialogFragment");
    }

    private void showTimePickerDialog() {
        TimePickerDialogFragment.newInstance().show(getSupportFragmentManager(),
                "TimePickerDialogFragment");
    }

    private void showConfirmDeletionDialog() {
        YesNoDialogFragment.newInstance(getString(R.string.confirm_dialog_title),
                getString(R.string.confirm_dialog_message), getString(R.string.confirm_dialog_yes),
                getString(R.string.confirm_dialog_no)).show(this.getSupportFragmentManager(),
                "ConfirmDeletionDialogFragment");
    }

    private void showDirectSelectionDialog() {
        DirectSelectionDialogFragment.newInstance(getString(R.string.direct_selection_dialog_shift),
                getResources().getStringArray(R.array.shifts)).show(
                this.getSupportFragmentManager(), "DirectSelectionDialogFragment");
    }

    private void showSimpleSelectionDialog() {
        SimpleSelectionDialogFragment.newInstance(getString(R.string.simple_selection_dialog_shift),
                getResources().getStringArray(R.array.shifts),
                getString(R.string.simple_selection_dialog_positiveButton), 0).show(
                this.getSupportFragmentManager(), "SimpleSelectionDialogFragment");
    }

    private void showMultipleSelectionDialog() {
        MultipleSelectionDialogFragment.newInstance(
                getString(R.string.multiple_selection_dialog_shift),
                getResources().getStringArray(R.array.shifts),
                getString(R.string.multiple_selection_dialog_positiveButton),
                new boolean[]{true, false, false}).show(this.getSupportFragmentManager(),
                "MultipleSelectionDialogFragment");
    }

    private void showCustomLayoutDialog() {
        (new CustomLayoutDialogFragment()).show(this.getSupportFragmentManager(),
                "CustomLayoutDialogFragment");
    }

    private void showAdapterDialog() {
        (new StudentsDialogFragment()).show(this.getSupportFragmentManager(),
                "StudentsDialogFragment");
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        ToastUtils.toast(this, getString(R.string.main_activity_selected,
                String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (monthOfYear + 1))
                        + "/" + String.format("%04d", year)));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ToastUtils.toast(this, getString(R.string.main_activity_selected,
                String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute)));
    }

    // Positive button on YesNoDialog.
    @Override
    public void onPositiveButtonClick(DialogInterface dialog) {
        ToastUtils.toast(this, getString(R.string.main_activity_user_deleted));
    }

    // Negative button on YesNoDialog.
    @Override
    public void onNegativeButtonClick(DialogInterface dialog) {
        ToastUtils.toast(this, getString(R.string.main_activity_no_delete));
    }

    // Selection on DirectSelectionDialog. Receives the index of the selected option.
    @Override
    public void onItemSelected(DialogFragment dialog, int which) {
        String[] shifts = getResources().getStringArray(R.array.shifts);
        ToastUtils.toast(this, getString(R.string.main_activity_selected, shifts[which]));
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
    public void onConfirmSelection(DialogInterface dialog, int which) {
        String[] shifts = getResources().getStringArray(R.array.shifts);
        ToastUtils.toast(this, getString(R.string.main_activity_selected, shifts[which]));
    }

    // Item selected on SimpleSelectionDialog. Receives the index of the selected
    // option.
    @Override
    public void onItemSelected(DialogInterface dialog, int which) {
        // Nothing done.
    }

    // Positive button on MultipleSelectionDialog. Receives the selection state of each option.
    @Override
    public void onConfirmSelection(DialogInterface dialog, boolean[] itemsSelectedState) {
        String[] shifts = getResources().getStringArray(R.array.shifts);
        String mensaje = buildShiftsMessage(itemsSelectedState, shifts);
        if (mensaje.equals("")) {
            mensaje = getString(R.string.multiple_selection_dialog_no_shift_selected);
        }
        ToastUtils.toast(this, getString(R.string.multiple_selection_dialog_selected, mensaje));
    }

    // Item clicked on MultipleSelectionDialog. Receives the index of the option clicked and the
    // state it is.
    @Override
    public void onItemClick(DialogInterface dialog, int which, boolean isChecked) {
        // Nothing done.
    }

    // Click on item in adapter. Receives selected student.
    @Override
    public void onListItemClick(DialogFragment dialog, Student student) {
        ToastUtils.toast(this, getString(R.string.main_activity_selected, student.getName()));
    }

    // Positive button on custom layout dialog.
    @Override
    public void onLoginClick(DialogFragment dialog) {
        ToastUtils.toast(this, getString(R.string.main_activity_login));
    }

    // Neutral button on custom layout dialog.
    @Override
    public void onCancelClick(DialogFragment dialog) {
        // Nothing done.
    }

}