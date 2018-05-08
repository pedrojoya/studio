package es.iessaladillo.pedrojoya.pr244.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr244.R;
import es.iessaladillo.pedrojoya.pr244.databinding.ActivityMainBinding;
import es.iessaladillo.pedrojoya.pr244.utils.KeyboardUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    @SuppressWarnings("FieldCanBeLocal")
    private MainActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vm = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(getApplicationContext().getResources())).get(
                MainActivityViewModel.class);
        b.setLifecycleOwner(this);
        b.setVm(vm);
        vm.viewMessage.observe(this, message -> {
            if (message != null) {
                String m = message.getContentIfNotHandled();
                if (m != null) {
                    KeyboardUtils.hideKeyboard(b.fab);
                    Toast.makeText(MainActivity.this, m, Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (savedInstanceState == null) {
            b.lblName.setTypeface(null, Typeface.BOLD);
        }
    }

}
