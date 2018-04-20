package es.iessaladillo.pedrojoya.pr156.ui.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

import es.iessaladillo.pedrojoya.pr156.R;
import es.iessaladillo.pedrojoya.pr156.data.model.Student;

public class StudentActivity extends AppCompatActivity {

    public static final String EXTRA_STUDENT = "EXTRA_STUDENT";

    private EditText txtName;
    private EditText txtAge;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_student);
        initViews();
        obtainData(getIntent());
    }

    private void initViews() {
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        txtAge = ActivityCompat.requireViewById(this, R.id.txtAge);
        ActivityCompat.requireViewById(this, R.id.btnSend).setOnClickListener(v -> finish());
    }

    private void obtainData(Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_STUDENT)) {
            student = intent.getParcelableExtra(EXTRA_STUDENT);
        }
        showStudent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        buildResult();
        super.finish();
    }

    private void buildResult() {
        viewsToStudent();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_STUDENT, student);
        this.setResult(RESULT_OK, intent);
    }

    private void showStudent() {
        txtName.setText(student.getName());
        txtAge.setText(String.valueOf(student.getAge()));
    }

    private void viewsToStudent() {
        student.setName(txtName.getText().toString());
        int age;
        try {
            age = Integer.parseInt(txtAge.getText().toString());
        } catch (NumberFormatException e) {
            age = Student.DEFAULT_AGE;
        }
        student.setAge(age);
    }

    public static void startForResult(Activity activity, int requestCode, Student student) {
        Intent intent = new Intent(activity, StudentActivity.class);
        intent.putExtra(EXTRA_STUDENT, student);
        activity.startActivityForResult(intent, requestCode);
    }

}
