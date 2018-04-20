package es.iessaladillo.pedrojoya.pr168.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import es.iessaladillo.pedrojoya.pr168.R;

public class LoginFragment extends Fragment {

    private OnLoginFragmentListener mListener;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnSignUp;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            initVistas(getView());
        }
    }

    private void initVistas(View view) {
        txtEmail = (EditText) ViewCompat.requireViewById(view, R.id.txtEmail);
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkForm();
            }
        });
        txtPassword = (EditText) ViewCompat.requireViewById(view, R.id.txtPassword);
        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkForm();
            }
        });
        btnLogin = (Button) ViewCompat.requireViewById(view, R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onLogin(txtEmail.getText().toString(),
                            txtPassword.getText().toString());
                }
            }
        });
        btnSignUp = (Button) ViewCompat.requireViewById(view, R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSignup(txtEmail.getText().toString(),
                            txtPassword.getText().toString());
                }
            }
        });
        checkForm();
    }

    private void checkForm() {
        btnLogin.setEnabled(!TextUtils.isEmpty(txtEmail.getText().toString()) && !TextUtils.isEmpty(
                txtPassword.getText().toString()));
        btnSignUp.setEnabled(
                !TextUtils.isEmpty(txtEmail.getText().toString()) && !TextUtils.isEmpty(
                        txtPassword.getText().toString()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentListener) {
            mListener = (OnLoginFragmentListener) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement OnLoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnLoginFragmentListener {
        void onLogin(String email, String password);

        void onSignup(String email, String password);
    }
}
