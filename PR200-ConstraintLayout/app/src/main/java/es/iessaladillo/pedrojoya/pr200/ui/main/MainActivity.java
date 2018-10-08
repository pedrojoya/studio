package es.iessaladillo.pedrojoya.pr200.ui.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr200.R;
import es.iessaladillo.pedrojoya.pr200.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnAccept;
    private TextView lblUsername;
    private TextView lblPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        btnAccept = ActivityCompat.requireViewById(this, R.id.btnAccept);
        Button btnCancel = ActivityCompat.requireViewById(this, R.id.btnCancel);
        lblUsername = ActivityCompat.requireViewById(this, R.id.lblUsername);
        lblPassword = ActivityCompat.requireViewById(this, R.id.lblPassword);
        txtUsername = ActivityCompat.requireViewById(this, R.id.txtUsername);
        txtPassword = ActivityCompat.requireViewById(this, R.id.txtPassword);

        btnAccept.setOnClickListener(v -> login());
        btnCancel.setOnClickListener(v -> resetViews());
        txtUsername.setOnFocusChangeListener(
                (v, hasFocus) -> setTypefaceOnFocus(lblUsername, hasFocus));
        txtPassword.setOnFocusChangeListener(
                (v, hasFocus) -> setTypefaceOnFocus(lblPassword, hasFocus));
        txtUsername.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIsValidForm();
                checkVisibility(txtUsername, lblUsername);
            }

        });
        txtPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            // Después de haber cambiado el texto.
            @Override
            public void afterTextChanged(Editable s) {
                checkIsValidForm();
                checkVisibility(txtPassword, lblPassword);
            }

        });
        setTypefaceOnFocus(lblUsername, true);
        checkIsValidForm();
        checkVisibility(txtPassword, lblPassword);
        checkVisibility(txtUsername, lblUsername);
    }

    private void login() {
        ToastUtils.toast(this,
                getString(R.string.main_signing_in, txtUsername.getText().toString()));
    }

    private void resetViews() {
        txtUsername.setText("");
        txtPassword.setText("");
    }

    private void checkIsValidForm() {
        btnAccept.setEnabled(
                !TextUtils.isEmpty(txtUsername.getText().toString()) && !TextUtils.isEmpty(
                        txtPassword.getText().toString()));
    }

    private void checkVisibility(EditText txt, TextView lbl) {
        if (TextUtils.isEmpty(txt.getText().toString())) {
            lbl.setVisibility(View.INVISIBLE);
        } else {
            lbl.setVisibility(View.VISIBLE);
        }
    }

    private void setTypefaceOnFocus(TextView lbl, boolean hasFocus) {
        if (hasFocus) {
            lbl.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lbl.setTypeface(Typeface.DEFAULT);
        }
    }

}