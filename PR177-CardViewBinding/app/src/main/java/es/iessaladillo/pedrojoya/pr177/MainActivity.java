package es.iessaladillo.pedrojoya.pr177;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr177.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private static final float DEFAULT_CUENTA = 0.00f;
    private static final int DEFAULT_PORCENTAJE = 2;
    private static final int DEFAULT_COMENSALES = 1;
    private static final String STATE_VIEWMODEL = "viewModel";

    private ActivityMainVM mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            mViewModel = new ActivityMainVM(DEFAULT_CUENTA, DEFAULT_PORCENTAJE, DEFAULT_COMENSALES);
        } else {
            mViewModel = savedInstanceState.getParcelable(STATE_VIEWMODEL);
        }
        mBinding.setViewModel(mViewModel);
        mBinding.txtCuenta.setSelectAllOnFocus(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable(STATE_VIEWMODEL, mViewModel);
        super.onSaveInstanceState(outState, outPersistentState);
    }

}