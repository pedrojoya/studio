package es.iessaladillo.pedrojoya.pr005;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class StudentActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_AGE = "EXTRA_AGE";

    private EditText txtName;
    private EditText txtAge;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        initViews();
        getIntentData();
    }

    private void initViews() {
        btnSend = ActivityCompat.requireViewById(this, R.id.btnSend);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        txtAge = ActivityCompat.requireViewById(this, R.id.txtAge);

        txtAge.setText(String.valueOf(Constants.DEFAULT_AGE));
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

    private boolean isValidForm() {
        return !TextUtils.isEmpty(txtName.getText().toString()) && !TextUtils.isEmpty(
                txtAge.getText().toString()) && Integer.parseInt(txtAge.getText().toString())
                <= Constants.MAX_AGE;
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_NAME)) {
                txtName.setText(intent.getStringExtra(EXTRA_NAME));
            }
            if (intent.hasExtra(EXTRA_AGE)) {
                txtAge.setText(
                        String.valueOf(intent.getIntExtra(EXTRA_AGE, Constants.DEFAULT_AGE)));
            }
        }
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