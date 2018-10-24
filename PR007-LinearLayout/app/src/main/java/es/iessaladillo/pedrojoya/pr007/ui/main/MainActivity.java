package es.iessaladillo.pedrojoya.pr007.ui.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr007.R;
import es.iessaladillo.pedrojoya.pr007.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr007.utils.TextViewUtils;
import es.iessaladillo.pedrojoya.pr007.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView lblUsername;
    private TextView lblPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        // Check initial state.
        checkInitialState();
    }

    private void setupViews() {
        btnLogin = ActivityCompat.requireViewById(this, R.id.btnLogin);
        btnLogin.setOnClickListener(v -> logIn());
        Button btnCancel = ActivityCompat.requireViewById(this, R.id.btnCancel);
        btnCancel.setOnClickListener(v -> resetViews());
        lblUsername = ActivityCompat.requireViewById(this, R.id.lblUsername);
        lblPassword = ActivityCompat.requireViewById(this, R.id.lblPassword);
        txtUsername = ActivityCompat.requireViewById(this, R.id.txtUsername);
        txtPassword = ActivityCompat.requireViewById(this, R.id.txtPassword);
        txtUsername.setOnFocusChangeListener((v, hasFocus) -> lblUsername.setTypeface(
                hasFocus ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT));
        txtPassword.setOnFocusChangeListener((v, hasFocus) -> lblPassword.setTypeface(
                hasFocus ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT));
        TextViewUtils.addAfterTextChangedListener(txtUsername, s -> {
            lblUsername.setVisibility(
                    TextUtils.isEmpty(txtUsername.getText()) ? View.INVISIBLE : View.VISIBLE);
            checkIsValidForm();
        });
        TextViewUtils.addAfterTextChangedListener(txtPassword, s -> {
            lblPassword.setVisibility(
                    TextUtils.isEmpty(txtPassword.getText()) ? View.INVISIBLE : View.VISIBLE);
            checkIsValidForm();
        });
        TextViewUtils.setOnImeActionDoneListener(txtPassword, (v, event) -> logIn());
    }

    private void checkInitialState() {
        checkIsValidForm();
        lblUsername.setVisibility(
                TextUtils.isEmpty(txtUsername.getText()) ? View.INVISIBLE : View.VISIBLE);
        lblPassword.setVisibility(
                TextUtils.isEmpty(txtPassword.getText()) ? View.INVISIBLE : View.VISIBLE);
    }

    private void logIn() {
        if (isValidForm()) {
            KeyboardUtils.hideSoftKeyboard(this);
            ToastUtils.toast(this,
                    getString(R.string.main_login, txtUsername.getText().toString()));
        }
    }

    private void resetViews() {
        txtUsername.setText("");
        txtPassword.setText("");
    }

    private void checkIsValidForm() {
        btnLogin.setEnabled(isValidForm());
    }

    private boolean isValidForm() {
        return !TextUtils.isEmpty(txtUsername.getText().toString()) && !TextUtils.isEmpty(
                txtPassword.getText().toString());
    }

}
