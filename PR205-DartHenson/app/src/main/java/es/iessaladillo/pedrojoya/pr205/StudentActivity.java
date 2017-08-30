package es.iessaladillo.pedrojoya.pr205;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import es.iessaladillo.pedrojoya.pr205.data.model.Student;

public class StudentActivity extends AppCompatActivity {

    private static final String EXTRA_STUDENT = "EXTRA_STUDENT";

    private EditText txtName;
    private EditText txtAge;
    private Button btnSend;

    @SuppressWarnings("WeakerAccess")
    @InjectExtra
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_student);
        student = new Student("", Constants.DEFAULT_AGE);
        Dart.inject(this);
        initViews();
        showStudent();
    }

    private void initViews() {
        txtName = findViewById(R.id.txtName);
        btnSend = findViewById(R.id.btnSend);
        txtAge = findViewById(R.id.txtAge);

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnSend.setEnabled(isValidForm());
            }
        });
        txtAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnSend.setEnabled(isValidForm());
            }
        });
        btnSend.setOnClickListener(v -> {
            createResult();
            finish();
        });
    }

    private void showStudent() {
        txtName.setText(student.getName());
        txtAge.setText(String.valueOf(student.getAge()));
    }

    private boolean isValidForm() {
        return !TextUtils.isEmpty(txtName.getText().toString()) && !TextUtils.isEmpty(
                txtAge.getText().toString()) && Integer.parseInt(txtAge.getText().toString())
                <= Constants.MAX_AGE;
    }

    private void createResult() {
        Intent result = new Intent();
        int age;
        try {
            age = Integer.parseInt(txtAge.getText().toString());
        } catch (NumberFormatException e) {
            age = Constants.DEFAULT_AGE;
        }
        result.putExtra(EXTRA_STUDENT, new Student(txtName.getText().toString(), age));
        this.setResult(RESULT_OK, result);
    }

    public static Student getStudentFromExtra(Intent intent) {
        return intent.getParcelableExtra(EXTRA_STUDENT);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Up == Back in order not to create a new instance of MainActivity when going up.
        onBackPressed();
        return true;
    }

}
