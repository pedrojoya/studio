package es.iessaladillo.pedrojoya.pr245.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr245.BR;
import es.iessaladillo.pedrojoya.pr245.R;
import es.iessaladillo.pedrojoya.pr245.databinding.ActivityMainBinding;
import es.iessaladillo.pedrojoya.pr245.utils.KeyboardUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private MainActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vm = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(getApplicationContext().getResources()))
                .get(MainActivityViewModel.class);
        b.setVm(vm);
        vm.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (propertyId == BR.viewMessage) {
                    KeyboardUtils.hideKeyboard(b.fab);
                    Toast.makeText(MainActivity.this, vm.getViewMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (savedInstanceState == null) {
            b.lblName.setTypeface(null, Typeface.BOLD);
        }
    }

}
