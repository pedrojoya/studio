package es.iessaladillo.pedrojoya.pr182.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import es.iessaladillo.pedrojoya.pr182.R;
import es.iessaladillo.pedrojoya.pr182.utils.SingleFragmentActivity;

public class LoginActivity extends SingleFragmentActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getFragmentContainerResId() {
        return R.id.flFragmentContainer;
    }

    @Override
    protected Fragment getFragment() {
        return new LoginFragment();
    }

    @Override
    protected String getTag() {
        return "LoginFragment";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
