package es.iessaladillo.pedrojoya.pr174.ui.main;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr174.R;
import es.iessaladillo.pedrojoya.pr174.reminder.ReminderService;
import es.iessaladillo.pedrojoya.pr174.utils.SharedPrefHelper;

@SuppressWarnings({"WeakerAccess", "unused", "CanBeFinal"})
public class MainActivity extends AppCompatActivity {

    private static final int REMINDER_JOB_ID = 1;

    private EditText txtMessage;
    private EditText txtInterval;
    @SuppressWarnings("FieldCanBeLocal")
    private SwitchCompat swSchedule;

    private int jobId;
    private SharedPrefHelper prefHelper;
    private JobScheduler jobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jobScheduler = (JobScheduler) getSystemService(
                Context.JOB_SCHEDULER_SERVICE);
        prefHelper = new SharedPrefHelper(getResources(),
                PreferenceManager.getDefaultSharedPreferences(this));
        initViews();
    }

    private void initViews() {
        txtMessage = ActivityCompat.requireViewById(this, R.id.txtMessage);
        txtInterval = ActivityCompat.requireViewById(this, R.id.txtInterval);
        swSchedule = ActivityCompat.requireViewById(this, R.id.swSchedule);
        swSchedule.setOnCheckedChangeListener((view, isChecked) -> onScheduleStateChange(isChecked));

        txtMessage.setText(prefHelper.getString(R.string.pref_message,
                getString(R.string.activity_main_txtMessage)));
        txtInterval.setText(String.valueOf(
                prefHelper.getInt(R.string.pref_interval, ReminderService.DEFAULT_INTERVAL)));
        swSchedule.setChecked(prefHelper.getBoolean(R.string.pref_scheduled, false));
    }

    private void onScheduleStateChange(boolean activate) {
        if (activate) {
            if (jobScheduler.getAllPendingJobs().isEmpty()) {
                schedule(REMINDER_JOB_ID);
            }
        } else {
            cancel(REMINDER_JOB_ID);
        }
    }

    private void schedule(int reminderJobId) {
        String message = TextUtils.isEmpty(txtMessage.getText().toString()) ? getString(
                R.string.activity_main_txtMessage) : txtMessage.getText().toString();
        int interval;
        try {
            interval = Integer.parseInt(txtInterval.getText().toString());
        } catch (NumberFormatException e) {
            interval = ReminderService.DEFAULT_INTERVAL;
        }
        // Create and sechedule job.
        JobInfo reminderJob = ReminderService.newReminderJob(this, reminderJobId, message,
                interval);
        if (jobScheduler.schedule(reminderJob) > 0) {
            Toast.makeText(this, getString(R.string.main_activity_reminder_scheduled, reminderJobId),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.main_activity_error_scheduling, Toast.LENGTH_SHORT).show();
        }
        // Save info in sharedPreferences.
        prefHelper.applyString(R.string.pref_message, message);
        prefHelper.applyInt(R.string.pref_interval, interval);
        prefHelper.applyBoolean(R.string.pref_scheduled, true);
    }

    private void cancel(int reminderJobId) {
        jobScheduler.cancel(jobId);
        // Save infor in sharedPreferences.
        prefHelper.applyBoolean(R.string.pref_scheduled, false);
    }

}
