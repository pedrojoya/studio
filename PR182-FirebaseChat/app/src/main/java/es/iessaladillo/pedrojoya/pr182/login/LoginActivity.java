package es.iessaladillo.pedrojoya.pr182.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import es.iessaladillo.pedrojoya.pr182.R;
import es.iessaladillo.pedrojoya.pr182.utils.SingleFragmentActivity;

public class LoginActivity extends SingleFragmentActivity {

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected int getFragmentContainerResId() {
        return 0;
    }

    @Override
    protected Fragment getFragment() {
        return null;
    }

    @Override
    protected String getTag() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

}
