package es.iessaladillo.pedrojoya.pr156.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr156.R;
import es.iessaladillo.pedrojoya.pr156.data.model.Student;
import es.iessaladillo.pedrojoya.pr156.ui.student.StudentActivity;
import es.iessaladillo.pedrojoya.pr156.utils.IntentsUtils;

public class MainActivity extends AppCompatActivity {

    private static final int RC_STUDENT = 1;

    // This object should be preserved and restored on configuration change.
    private Student student = new Student("Baldomero", 45);

    private TextView lblData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        showStudent();
    }

    private void setupViews() {
        Button btnRequest = ActivityCompat.requireViewById(this, R.id.btnRequest);
        lblData = ActivityCompat.requireViewById(this, R.id.lblData);

        btnRequest.setOnClickListener(v -> requestData());
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
        student = (Student) IntentsUtils.requireParcelableExtra(intent, StudentActivity.EXTRA_STUDENT);
        showStudent();
    }

    private void showStudent() {
        lblData.setText(getString(R.string.main_activity_student_data, student.getName(),
                student.getAge()));
    }

}
