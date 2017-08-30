package es.iessaladillo.pedrojoya.pr005;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int RC_STUDENT = 1;

    // NOTE: These two fiels should be saved on configuration change (not explained yet)
    private String mName;
    private int mAge = Constants.DEFAULT_AGE;

    private TextView lblData;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnRequest = findViewById(R.id.btnRequest);
        lblData = findViewById(R.id.lblData);
        btnRequest.setOnClickListener(v -> requestData());
    }

    private void requestData() {
        StudentActivity.startForResult(this, RC_STUDENT, mName, mAge);
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
                mName = intent.getStringExtra(StudentActivity.EXTRA_NAME);
            }
            if (intent.hasExtra(StudentActivity.EXTRA_AGE)) {
                mAge = intent.getIntExtra(StudentActivity.EXTRA_AGE,
                        Constants.DEFAULT_AGE);
            }
        }
        lblData.setText(getString(R.string.main_activity_student_data, mName, mAge));
    }

}