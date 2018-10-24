package es.iessaladillo.pedrojoya.pr005.ui.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr005.Constants;
import es.iessaladillo.pedrojoya.pr005.R;
import es.iessaladillo.pedrojoya.pr005.utils.TextViewUtils;

public class StudentActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_AGE = "EXTRA_AGE";

    private EditText txtName;
    private EditText txtAge;
    private Button btnSend;

    private String name = "";
    private int age = Constants.DEFAULT_AGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        getIntentData();
        setupViews();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_NAME)) {
                name = intent.getStringExtra(EXTRA_NAME);
            }
            if (intent.hasExtra(EXTRA_AGE)) {
                age = intent.getIntExtra(EXTRA_AGE, Constants.DEFAULT_AGE);
            }
        }
    }

    private void setupViews() {
        btnSend = ActivityCompat.requireViewById(this, R.id.btnSend);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        txtAge = ActivityCompat.requireViewById(this, R.id.txtAge);

        txtAge.setText(String.valueOf(Constants.DEFAULT_AGE));
        TextViewUtils.addAfterTextChangedListener(txtName, s -> checkIsValidForm());
        TextViewUtils.addAfterTextChangedListener(txtAge, s -> checkIsValidForm());
        btnSend.setOnClickListener(v -> {
            createResult();
            finish();
        });
        showStudent();
    }

    private void checkIsValidForm() {
        btnSend.setEnabled(isValidForm());
    }

    private boolean isValidForm() {
        return !TextUtils.isEmpty(txtName.getText().toString()) && !TextUtils.isEmpty(
                txtAge.getText().toString()) && Integer.parseInt(txtAge.getText().toString())
                <= Constants.MAX_AGE;
    }

    private void showStudent() {
        txtName.setText(name);
        txtAge.setText(String.valueOf(age));
    }

    private void createResult() {
        Intent result = new Intent();
        result.putExtra(EXTRA_NAME, txtName.getText().toString());
        int age;
        try {
            age = Integer.parseInt(txtAge.getText().toString());
        } catch (NumberFormatException e) {
            age = Constants.DEFAULT_AGE;
        }
        result.putExtra(EXTRA_AGE, age);
        this.setResult(RESULT_OK, result);
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