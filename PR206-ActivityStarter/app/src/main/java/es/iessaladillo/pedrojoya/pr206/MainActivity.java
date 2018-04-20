package es.iessaladillo.pedrojoya.pr206;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr206.data.model.Student;

public class MainActivity extends AppCompatActivity {

    private static final int RC_STUDENT = 1;
    private static final String STATE_STUDENT = "STATE_STUDENT";

    private TextView lblData;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnRequest;

    @SuppressWarnings("WeakerAccess")
    Student mStudent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreSavedInstanceState(savedInstanceState);
        initViews();
        showStudent();
    }

    private void restoreSavedInstanceState(Bundle savedInstanceState) {
        mStudent = new Student("", Constants.DEFAULT_AGE);
        if (savedInstanceState != null) {
            mStudent = savedInstanceState.getParcelable(STATE_STUDENT);
        }

    }

    private void initViews() {
        lblData = ActivityCompat.requireViewById(this, R.id.lblData);
        btnRequest = ActivityCompat.requireViewById(this, R.id.btnRequest);

        btnRequest.setOnClickListener(v -> requestData());
    }

    private void requestData() {
        StudentActivityStarter.startForResult(this, mStudent, RC_STUDENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_STUDENT) {
            mStudent = StudentActivity.getAlumnoFromExtra(data);
            showStudent();
        }
    }

    private void showStudent() {
        lblData.setText(getString(R.string.main_activity_student_data, mStudent.getName(),
                mStudent.getAge()));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_STUDENT, mStudent);
    }

}
