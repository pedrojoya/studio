package es.iessaladillo.pedrojoya.pr005.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr005.Constants;
import es.iessaladillo.pedrojoya.pr005.R;
import es.iessaladillo.pedrojoya.pr005.ui.student.StudentActivity;

public class MainActivity extends AppCompatActivity {

    private static final int RC_STUDENT = 1;

    // NOTE: These two fiels should be saved on configuration change (not explained yet)
    private String name;
    private int age = Constants.DEFAULT_AGE;

    private TextView lblData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        ActivityCompat.requireViewById(this, R.id.btnRequest).setOnClickListener(v -> requestData());
        lblData = ActivityCompat.requireViewById(this, R.id.lblData);
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
        if (intent != null) {
            if (intent.hasExtra(StudentActivity.EXTRA_NAME)) {
                name = intent.getStringExtra(StudentActivity.EXTRA_NAME);
            }
            if (intent.hasExtra(StudentActivity.EXTRA_AGE)) {
                age = intent.getIntExtra(StudentActivity.EXTRA_AGE,
                        Constants.DEFAULT_AGE);
            }
        }
        lblData.setText(getString(R.string.main_activity_student_data, name, age));
    }

}