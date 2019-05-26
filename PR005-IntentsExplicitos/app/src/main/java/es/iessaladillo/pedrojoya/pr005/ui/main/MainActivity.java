package es.iessaladillo.pedrojoya.pr005.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr005.R;
import es.iessaladillo.pedrojoya.pr005.ui.calendar.CalendarActivity;
import es.iessaladillo.pedrojoya.pr005.utils.IntentsUtils;

public class MainActivity extends AppCompatActivity {

    private static final int RC_CALENDAR = 1;

    private EditText txtName;
    private EditText txtSignUpDate;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate signUpDate = LocalDate.now();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        showSignUpDate();
    }

    private void setupViews() {
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        txtSignUpDate = ActivityCompat.requireViewById(this, R.id.txtSignUpDate);
        Button btnShow = ActivityCompat.requireViewById(this, R.id.btnShow);

        txtSignUpDate.setOnClickListener(v -> requestBirthDate());
        btnShow.setOnClickListener(v -> showData());
    }

    private void showData() {
        if (isValidForm()) {
            Toast.makeText(this,
                getString(R.string.main_message, txtName.getText(), txtSignUpDate.getText()),
                Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidForm() {
        return !TextUtils.isEmpty(txtName.getText().toString()) && !TextUtils.isEmpty(
            txtSignUpDate.getText().toString());
    }

    private void requestBirthDate() {
        CalendarActivity.startForResult(this, RC_CALENDAR, signUpDate.getDayOfMonth(),
            signUpDate.getMonthValue(), signUpDate.getYear());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && requestCode == RC_CALENDAR && intent != null) {
            getReturnData(intent);
        }
    }

    private void getReturnData(Intent intent) {
        int day = IntentsUtils.requireIntExtra(intent, CalendarActivity.EXTRA_DAY);
        int month = IntentsUtils.requireIntExtra(intent, CalendarActivity.EXTRA_MONTH);
        int year = IntentsUtils.requireIntExtra(intent, CalendarActivity.EXTRA_YEAR);
        signUpDate = LocalDate.of(year, month, day);
        showSignUpDate();
    }

    private void showSignUpDate() {
        txtSignUpDate.setText(dateTimeFormatter.format(signUpDate));
    }

}