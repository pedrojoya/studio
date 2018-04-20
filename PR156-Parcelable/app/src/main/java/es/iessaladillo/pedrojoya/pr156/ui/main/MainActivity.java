package es.iessaladillo.pedrojoya.pr156.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr156.R;
import es.iessaladillo.pedrojoya.pr156.data.model.Student;
import es.iessaladillo.pedrojoya.pr156.ui.student.StudentActivity;

public class MainActivity extends AppCompatActivity {

    private static final int RC_STUDENT = 1;

    private Student student;

    private TextView lblData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        student = new Student("", Student.DEFAULT_AGE);
        initViews();
    }

    private void initViews() {
        ActivityCompat.requireViewById(this, R.id.btnRequest).setOnClickListener(
                v -> requestData());
        lblData = ActivityCompat.requireViewById(this, R.id.lblData);
    }

    private void requestData() {
        StudentActivity.startForResult(this, RC_STUDENT, student);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_STUDENT) {
            obtainResponseData(data);
        }
    }

    private void obtainResponseData(Intent intent) {
        if (intent != null && intent.hasExtra(StudentActivity.EXTRA_STUDENT)) {
            student = intent.getParcelableExtra(StudentActivity.EXTRA_STUDENT);
        }
        showStudent();
    }

    private void showStudent() {
        lblData.setText(student.toString());
    }

}
