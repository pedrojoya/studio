package es.iessaladillo.pedrojoya.pr149.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr149.R;
import es.iessaladillo.pedrojoya.pr149.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr149.utils.TextViewUtils;
import es.iessaladillo.pedrojoya.pr149.utils.ToastUtils;
import es.iessaladillo.pedrojoya.pr149.utils.ValidationUtils;


public class MainActivity extends AppCompatActivity {

    private TextInputLayout tilName;
    private TextInputEditText txtName;
    private TextInputLayout tilPhone;
    private TextInputEditText txtPhone;
    private TextInputLayout tilEmail;
    private TextInputEditText txtEmail;
    private TextInputLayout tilPassword;
    private TextInputEditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        tilName = ActivityCompat.requireViewById(this, R.id.tilName);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        tilPhone = ActivityCompat.requireViewById(this, R.id.tilPhone);
        txtPhone = ActivityCompat.requireViewById(this, R.id.txtPhone);
        tilEmail = ActivityCompat.requireViewById(this, R.id.tilEmail);
        txtEmail = ActivityCompat.requireViewById(this, R.id.txtEmail);
        tilPassword = ActivityCompat.requireViewById(this, R.id.tilPassword);
        txtPassword = ActivityCompat.requireViewById(this, R.id.txtPassword);
        Button btnSignUp = ActivityCompat.requireViewById(this, R.id.btnSignUp);
        Button btnReset = ActivityCompat.requireViewById(this, R.id.btnReset);

        TextViewUtils.addAfterTextChangedListener(txtName, s -> checkIsValidName());
        TextViewUtils.addAfterTextChangedListener(txtPhone, s -> checkIsValidPhone());
        TextViewUtils.addAfterTextChangedListener(txtEmail, s -> checkIsValidEmail());
        TextViewUtils.addAfterTextChangedListener(txtPassword, s -> checkIsValidPassword());
        TextViewUtils.setOnImeActionDoneListener(txtPassword, (v, event) -> signUp());
        btnSignUp.setOnClickListener(v -> signUp());
        btnReset.setOnClickListener(v -> reset());
    }

    private void signUp() {
        KeyboardUtils.hideSoftKeyboard(this);
        checkIsValidForm();
        if (isValidForm()) {
            ToastUtils.toast(this, getString(R.string.main_signing_up));
        }
    }

    private void checkIsValidForm() {
        checkIsValidName();
        checkIsValidPhone();
        checkIsValidEmail();
        checkIsValidPassword();
    }

    private boolean isValidForm() {
        return isValidName() && isValidPhone() && isValidEmail() && isValidPassword();
    }

    private void reset() {
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }

    private boolean isValidName() {
        return txtName.getText() != null && !TextUtils.isEmpty(txtName.getText().toString());
    }

    private boolean isValidPhone() {
        return txtPhone.getText() != null && ValidationUtils.isValidSpanishPhoneNumber(
                txtPhone.getText().toString());
    }

    private boolean isValidEmail() {
        return txtEmail.getText() != null && ValidationUtils.isValidEmail(
                txtEmail.getText().toString());
    }

    private boolean isValidPassword() {
        return txtPassword.getText() != null && !TextUtils.isEmpty(
                txtPassword.getText().toString());
    }

    private void checkIsValidName() {
        tilName.setError(!isValidName() ? getString(R.string.main_required_field) : null);
    }

    private void checkIsValidPhone() {
        tilPhone.setError(!isValidPhone() ? getString(R.string.main_invalid_phone) : null);
    }

    private void checkIsValidEmail() {
        tilEmail.setError(!isValidEmail() ? getString(R.string.main_invalid_email) : null);
    }

    private void checkIsValidPassword() {
        tilPassword.setError(!isValidPassword() ? getString(R.string.main_required_field) : null);
    }

}
