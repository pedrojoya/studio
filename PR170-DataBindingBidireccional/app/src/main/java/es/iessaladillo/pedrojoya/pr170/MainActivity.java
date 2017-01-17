package es.iessaladillo.pedrojoya.pr170;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr170.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_SALUDO = "saludo";

    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            mViewModel = new MainActivityViewModel();
        } else {
            mViewModel = savedInstanceState.getParcelable(STATE_SALUDO);
        }
        mBinding.setViewModel(mViewModel);
        mBinding.setPresenter(new MainActivityPresenter());
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_SALUDO, mViewModel);
        super.onSaveInstanceState(outState);
    }

}
