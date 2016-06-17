package es.iessaladillo.pedrojoya.pr184.utils.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    @LayoutRes
    protected abstract int getLayoutResId();
    @IdRes
    protected abstract int getFragmentContainerResId();
    protected abstract Fragment getFragment();
    protected abstract String getTag();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(getFragmentContainerResId(), getFragment(), getTag())
                    .commit();
        }
    }

}
