package es.iessaladillo.pedrojoya.pr149.ui.main;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import es.iessaladillo.pedrojoya.pr149.R;
import es.iessaladillo.pedrojoya.pr149.utils.ValidationUtils;


public class MainActivity extends AppCompatActivity {

    private TextInputLayout tilPhone;
    private TextInputEditText txtPhone;
    private TextInputLayout tilEmail;
    private TextInputEditText txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        tilPhone = ActivityCompat.requireViewById(this, R.id.tilPhone);
        txtPhone = ActivityCompat.requireViewById(this, R.id.txtPhone);
        tilEmail = ActivityCompat.requireViewById(this, R.id.tilEmail);
        txtEmail = ActivityCompat.requireViewById(this, R.id.txtEmail);

        txtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(txtPhone.getText().toString())) {
                    if (!ValidationUtils.isValidSpanishPhoneNumber(txtPhone.getText().toString())) {
                        tilPhone.setError(getString(
                                es.iessaladillo.pedrojoya.pr149.R.string
                                        .main_activity_invalid_phone));
                    } else {
                        tilPhone.setErrorEnabled(false);
                    }
                } else {
                    tilPhone.setErrorEnabled(false);
                }
            }
        });
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(txtEmail.getText().toString())) {
                    if (!ValidationUtils.isValidEmail(txtEmail.getText().toString())) {
                        tilEmail.setError(getString(es.iessaladillo.pedrojoya.pr149.R.string.main_activity_invalid_email));
                    } else {
                        tilEmail.setErrorEnabled(false);
                    }
                } else {
                    tilEmail.setErrorEnabled(false);
                }
            }
        });
    }

}
