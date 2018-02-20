package es.iessaladillo.pedrojoya.pr210.detail;

import android.support.v7.app.AppCompatActivity;

public abstract class DetailFragmentBaseActivity<T extends DetailFragmentBaseViewModel> extends
        AppCompatActivity {

    public abstract Class<T> getViewModelClass();

}
