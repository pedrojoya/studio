package es.iessaladillo.pedrojoya.pr005.ui.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr005.R;
import es.iessaladillo.pedrojoya.pr005.utils.IntentsUtils;

public class StudentActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_AGE = "EXTRA_AGE";

    private static final int MAX_AGE = 150;

    private EditText txtName;
    private EditText txtAge;

    private String name;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        getIntentData();
        setupViews();
    }

    private void getIntentData() {
        name = IntentsUtils.requireStringExtra(getIntent(), EXTRA_NAME);
        age = IntentsUtils.requireIntExtra(getIntent(), EXTRA_AGE);
    }

    private void setupViews() {
        Button btnSend = ActivityCompat.requireViewById(this, R.id.btnSend);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        txtAge = ActivityCompat.requireViewById(this, R.id.txtAge);

        btnSend.setOnClickListener(v -> send());
        showStudent();
    }

    private boolean isValidForm() {
        boolean validName = isValidName();
        boolean validAge = isValidAge();
        return validName && validAge;
    }

    private boolean isValidName() {
        boolean valid = !txtName.getText().toString().trim().isEmpty();
        txtName.setError(valid ? null : getString(R.string.main_invalid_name));
        return valid;
    }

    private boolean isValidAge() {
        boolean valid;
        try {
            int age = Integer.parseInt(txtAge.getText().toString());
            valid = age >= 0 && age <= MAX_AGE;
        } catch (NumberFormatException e) {
            valid = false;
        }
        txtAge.setError(valid ? null : getString(R.string.main_invalid_age));
        return valid;
    }

    private void showStudent() {
        txtName.setText(name);
        txtAge.setText(String.valueOf(age));
    }

    private void send() {
        if (isValidForm()) {
            setActivityResult();
            finish();
        }
    }

    private void setActivityResult() {
        setResult(RESULT_OK,
            new Intent().putExtra(EXTRA_NAME, txtName.getText().toString()).putExtra(EXTRA_AGE,
                Integer.parseInt(txtAge.getText().toString())));
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Up == Back in order not to create a new instance of MainActivity when going up.
        onBackPressed();
        return true;
    }

    @SuppressWarnings("SameParameterValue")
    public static void startForResult(Activity activity, int requestCode, String name, int age) {
        Intent intent = new Intent(activity, StudentActivity.class);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_AGE, age);
        activity.startActivityForResult(intent, requestCode);
    }

}