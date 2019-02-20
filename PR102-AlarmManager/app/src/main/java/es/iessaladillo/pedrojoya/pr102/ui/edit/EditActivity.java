package es.iessaladillo.pedrojoya.pr102.ui.edit;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr102.R;
import es.iessaladillo.pedrojoya.pr102.base.TimePickerDialogFragment;
import es.iessaladillo.pedrojoya.pr102.reminder.ReminderScheduler;

public class EditActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private String message;
    private Calendar when = Calendar.getInstance();
    private EditText txtMessage;
    private EditText txtWhen;
    private EditActivityViewModel viewModel;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm",
        Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getIntentData();
        viewModel = ViewModelProviders.of(this,
            new EditActivityViewModelFactory(ReminderScheduler.getInstance(getApplication()))).get(
            EditActivityViewModel.class);
        setupViews();
    }

    private void getIntentData() {
        Intent intent = Objects.requireNonNull(getIntent());
        message =
            Objects.requireNonNull(intent.getStringExtra(ReminderScheduler.EXTRA_MESSAGE));
        when.setTimeInMillis(intent.getLongExtra(ReminderScheduler.EXTRA_WHEN, 0));
    }

    private void setupViews() {
        txtMessage = ActivityCompat.requireViewById(this, R.id.txtMessage);
        txtWhen = ActivityCompat.requireViewById(this, R.id.txtWhen);
        Button btnRescheduleReminder = ActivityCompat.requireViewById(this, R.id.btnRescheduleReminder);
        Button btnCancelReminder = ActivityCompat.requireViewById(this, R.id.btnCancelReminder);

        txtMessage.setText(message);
        txtWhen.setText(simpleDateFormat.format(when.getTime()));
        txtWhen.setInputType(InputType.TYPE_NULL);
        txtWhen.setKeyListener(null);
        txtWhen.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showTimePickerDialog();
            }
        });
        txtWhen.setOnClickListener(v -> showTimePickerDialog());

        btnRescheduleReminder.setOnClickListener(v -> reschedule());
        btnCancelReminder.setOnClickListener(v -> cancel());
    }

    private void showTimePickerDialog() {
        if (when == null) {
            TimePickerDialogFragment.newInstance().show(getSupportFragmentManager(),
                TimePickerDialogFragment.class.getSimpleName());
        } else {
            TimePickerDialogFragment.newInstance(when.get(Calendar.HOUR_OF_DAY),
                when.get(Calendar.MINUTE), true).show(getSupportFragmentManager(),
                TimePickerDialogFragment.class.getSimpleName());
        }
    }

    private void reschedule() {
        String message = txtMessage.getText().toString();
        if (!TextUtils.isEmpty(message) && when != null) {
            viewModel.rescheduleReminder(message, when);
        }
    }

    private void cancel() {
        viewModel.cancelReminder();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (when == null) {
            when = Calendar.getInstance();
        }
        when.set(Calendar.HOUR_OF_DAY, hourOfDay);
        when.set(Calendar.MINUTE, minute);
        when.set(Calendar.SECOND, 0);
        txtWhen.setText(simpleDateFormat.format(when.getTime()));
    }

}
