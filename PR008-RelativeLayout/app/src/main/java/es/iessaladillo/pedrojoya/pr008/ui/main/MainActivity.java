package es.iessaladillo.pedrojoya.pr008.ui.main;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr008.R;
import es.iessaladillo.pedrojoya.pr008.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnLogin = ActivityCompat.requireViewById(this, R.id.btnLogin);
        btnLogin.setOnClickListener(v -> login());
        Button btnCancel = ActivityCompat.requireViewById(this, R.id.btnCancel);
        btnCancel.setOnClickListener(v -> resetViews());
        TextView lblUsername = ActivityCompat.requireViewById(this, R.id.lblUsername);
        TextView lblPassword = ActivityCompat.requireViewById(this, R.id.lblPassword);
        txtUsername = ActivityCompat.requireViewById(this, R.id.txtUsername);
        txtPassword = ActivityCompat.requireViewById(this, R.id.txtPassword);
        setChangeColorOnFocusListener(lblUsername, txtUsername);
        setChangeColorOnFocusListener(lblPassword, txtPassword);
        setChangeVisibilityTextWatcher(lblUsername, txtUsername);
        setChangeVisibilityTextWatcher(lblPassword, txtPassword);
        // Initial check.
        setColorOnFocus(lblUsername, true);
        checkIsValidForm();
        setTextViewVisibilityOnEditText(txtPassword, lblPassword);
        setTextViewVisibilityOnEditText(txtUsername, lblUsername);
    }

    private void login() {
        ToastUtils.toast(this,
                getString(R.string.main_activity_connected, txtUsername.getText().toString()));
    }

    private void resetViews() {
        txtUsername.setText("");
        txtPassword.setText("");
    }

    private void setChangeVisibilityTextWatcher(TextView lbl, EditText txt) {
        txt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setTextViewVisibilityOnEditText(txt, lbl);
                checkIsValidForm();
            }

        });
    }

    private void setChangeColorOnFocusListener(TextView lbl, EditText txt) {
        txt.setOnFocusChangeListener((v, hasFocus) -> setColorOnFocus(lbl, hasFocus));
    }

    private void checkIsValidForm() {
        btnLogin.setEnabled(isFormValid());
    }

    private boolean isFormValid() {
        return !TextUtils.isEmpty(txtUsername.getText().toString()) && !TextUtils.isEmpty(
                txtPassword.getText().toString());
    }

    private void setTextViewVisibilityOnEditText(EditText txt, TextView lbl) {
        lbl.setVisibility(
                TextUtils.isEmpty(txt.getText().toString()) ? View.INVISIBLE : View.VISIBLE);
    }

    private void setColorOnFocus(TextView lbl, boolean hasFocus) {
        lbl.setTextColor(
                hasFocus ? ContextCompat.getColor(this, R.color.accent) : ContextCompat.getColor(
                        this, R.color.primary));
        lbl.setTypeface(hasFocus ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
    }

}
