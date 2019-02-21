package es.iessaladillo.pedrojoya.pr156.ui.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr156.R;
import es.iessaladillo.pedrojoya.pr156.data.model.Student;
import es.iessaladillo.pedrojoya.pr156.utils.IntentsUtils;

public class StudentActivity extends AppCompatActivity {

    public static final String EXTRA_STUDENT = "EXTRA_STUDENT";

    private static final int MAX_AGE = 150;

    private EditText txtName;
    private EditText txtAge;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_student);
        obtainData();
        setupViews();
    }

    private void obtainData() {
        student = (Student) IntentsUtils.requireParcelableExtra(getIntent(), EXTRA_STUDENT);
    }

    private void setupViews() {
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        txtAge = ActivityCompat.requireViewById(this, R.id.txtAge);
        Button btnSend = ActivityCompat.requireViewById(this, R.id.btnSend);

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
        txtName.setText(student.getName());
        txtAge.setText(String.valueOf(student.getAge()));
    }

    private void send() {
        if (isValidForm()) {
            setActivityResult();
            finish();
        }
    }

    private void setActivityResult() {
        viewsToStudent();
        setResult(RESULT_OK,
            new Intent().putExtra(EXTRA_STUDENT, student));
    }

    private void viewsToStudent() {
        student.setName(txtName.getText().toString());
        student.setAge(Integer.parseInt(txtAge.getText().toString()));
    }

    public static void startForResult(Activity activity, int requestCode, Student student) {
        Intent intent = new Intent(activity, StudentActivity.class);
        intent.putExtra(EXTRA_STUDENT, student);
        activity.startActivityForResult(intent, requestCode);
    }

}
