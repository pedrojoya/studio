package es.iessaladillo.pedrojoya.pr182.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.marlonmafra.android.widget.EditTextPassword;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import es.iessaladillo.pedrojoya.pr182.R;
import es.iessaladillo.pedrojoya.pr182.contacts.ContactsActivity;
import es.iessaladillo.pedrojoya.pr182.utils.DotProgressBarManager;
import es.iessaladillo.pedrojoya.pr182.utils.SnackbarManager;
import es.iessaladillo.pedrojoya.pr182.utils.UIMessageManager;
import es.iessaladillo.pedrojoya.pr182.utils.UIProgressManager;

public class LoginFragment extends Fragment implements LoginView {

    @BindView(R.id.lblAppName)
    TextView lblAppName;
    @BindView(R.id.txtEmail)
    TextInputEditText txtEmail;
    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.txtPassword)
    EditTextPassword txtPassword;
    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.lblSignUp)
    TextView lblSignUp;
    @BindView(R.id.pbLoading)
    DotProgressBar pbLoading;

    private Unbinder mUnbinder;
    private LoginPresenterImpl mPresenter;
    private UIMessageManager mUIMessageManager;
    private UIProgressManager mUIProgressManager;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lblAppName.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Pacifico.ttf"));
        mPresenter = new LoginPresenterImpl();
        mUIMessageManager = new SnackbarManager();
        mUIProgressManager = new DotProgressBarManager(pbLoading);
        txtEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });
        checkFormValid();
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        mPresenter.onViewDestroyed();
        super.onDestroyView();
    }

    @OnClick(R.id.btnSignIn)
    public void btnSignInOnClick(View view) {
        mPresenter.wantToSignIn(txtEmail.getText().toString(), txtPassword.getText().toString());
    }

    @OnClick(R.id.lblSignUp)
    public void lblSignUpOnClick(View view) {
        mPresenter.wantToSignUp(txtEmail.getText().toString(), txtPassword.getText().toString());
    }

    @OnTextChanged(value = R.id.txtEmail, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void txtEmailAfterTextChanged(Editable s) {
        checkFormValid();
    }

    @OnTextChanged(value = R.id.txtPassword, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void txtPasswordAfterTextChanged(Editable s) {
        checkFormValid();
    }

    @OnEditorAction(R.id.txtPassword)
    public boolean txtPasswordOnEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            hideKeyboard(textView);
            btnSignInOnClick(btnSignIn);
            return true;
        }
        return false;
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm =
                (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void checkFormValid() {
        btnSignIn.setEnabled(isFormValid());
        lblSignUp.setEnabled(isFormValid());
    }

    private boolean isFormValid() {
        return !TextUtils.isEmpty(txtEmail.getText().toString()) &&
                !TextUtils.isEmpty(txtPassword.getText().toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onViewAttached(this);
        mPresenter.wantToCheckAuthenticated();
    }

    @Override
    public void onStop() {
        mPresenter.onViewDetached();
        super.onStop();
    }

    @Override
    public void showUserHasSignedIn() {
        mUIMessageManager.showMessage(btnSignIn, getString(R.string.login_usersignedin_message));
    }

    @Override
    public void showUserHasSignedUp() {
        mUIMessageManager.showMessage(btnSignIn, getString(R.string.login_usersignedup_message));
    }

    @Override
    public void showErrorSigningIn(String errorMessage) {
        mUIMessageManager.showMessage(lblSignUp, errorMessage);
    }

    @Override
    public void showErrorSigningUp(String errorMessage) {
        mUIMessageManager.showMessage(lblSignUp, errorMessage);
    }

    @Override
    public void navigateToContactsActivity() {
        startActivity(new Intent(getActivity(), ContactsActivity.class));
    }

    @Override
    public void onLoadingStarted() {
        mUIProgressManager.showIndeterminateProgress();
    }

    @Override
    public void onLoadingFinished() {
        mUIProgressManager.hideProgress();
    }

}
