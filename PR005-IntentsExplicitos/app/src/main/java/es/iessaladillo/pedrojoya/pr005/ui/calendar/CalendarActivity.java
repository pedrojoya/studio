package es.iessaladillo.pedrojoya.pr005.ui.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr005.R;
import es.iessaladillo.pedrojoya.pr005.utils.IntentsUtils;

public class CalendarActivity extends AppCompatActivity {

    public static final String EXTRA_DAY = "EXTRA_DAY";
    public static final String EXTRA_MONTH = "EXTRA_MONTH";
    public static final String EXTRA_YEAR = "EXTRA_YEAR";

    private CalendarView calendarView;

    private int day;
    private int month;
    private int year;

    public static void startForResult(@NonNull Activity activity, int requestCode, int day,
        int month, int year) {
        Intent intent = new Intent(activity, CalendarActivity.class).putExtra(EXTRA_DAY, day)
            .putExtra(EXTRA_MONTH, month)
            .putExtra(EXTRA_YEAR, year);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);
        getIntentData(getIntent());
        setupViews();
        showData();
    }

    private void getIntentData(Intent intent) {
        day = IntentsUtils.requireIntExtra(intent, EXTRA_DAY);
        month = IntentsUtils.requireIntExtra(intent, EXTRA_MONTH);
        year = IntentsUtils.requireIntExtra(intent, EXTRA_YEAR);
    }

    private void setupViews() {
        Button btnSend = ActivityCompat.requireViewById(this, R.id.btnSend);
        calendarView = ActivityCompat.requireViewById(this, R.id.calendarView);

        btnSend.setOnClickListener(v -> send());
        calendarView.setOnDateChangeListener(
            (view, year, month, dayOfMonth) -> saveDate(year, month, dayOfMonth));
    }

    private void saveDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private void showData() {
        calendarView.setDate(LocalDate.of(year, month, day).atStartOfDay().atZone(
            ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    private void send() {
        setActivityResult();
        finish();
    }

    private void setActivityResult() {
        Intent result = new Intent()
            .putExtra(EXTRA_DAY, day)
            .putExtra(EXTRA_MONTH, month + 1)
            .putExtra(EXTRA_YEAR, year);
        setResult(RESULT_OK, result);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Up == Back in order not to create a new instance of MainActivity when going up.
        onBackPressed();
        return true;
    }

}
