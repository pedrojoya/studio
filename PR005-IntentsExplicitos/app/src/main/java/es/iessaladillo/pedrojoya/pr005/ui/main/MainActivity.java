package es.iessaladillo.pedrojoya.pr005.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr005.R;
import es.iessaladillo.pedrojoya.pr005.ui.student.StudentActivity;
import es.iessaladillo.pedrojoya.pr005.utils.IntentsUtils;

public class MainActivity extends AppCompatActivity {

    private static final int RC_STUDENT = 1;

    // NOTE: These two fiels should be saved on configuration change (not explained yet)
    private String name = "Baldomero";
    private int age = 45;

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
        StudentActivity.startForResult(this, RC_STUDENT, name, age);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_STUDENT) {
            getReturnData(data);
        }
    }

    private void getReturnData(Intent intent) {
        name = IntentsUtils.requireStringExtra(intent, StudentActivity.EXTRA_NAME);
        age = IntentsUtils.requireIntExtra(intent, StudentActivity.EXTRA_AGE);
        showStudent();
    }

    private void showStudent() {
        lblData.setText(getString(R.string.main_student_data, name, age));
    }

}