package es.iessaladillo.pedrojoya.pr244.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr244.R;
import es.iessaladillo.pedrojoya.pr244.databinding.ActivityMainBinding;
import es.iessaladillo.pedrojoya.pr244.utils.KeyboardUtils;

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
        b.setLifecycleOwner(this);
        b.setVm(vm);
        vm.viewMessage.observe(this, message -> {
            KeyboardUtils.hideKeyboard(b.fab);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        });
/*
        vm.treatmentIndex.observe(this, index ->
                vm.treatment.setValue(getResources().getStringArray(R.array.activity_main_treatments)[index])
        );
        vm.name.observe(this, name -> {
            vm.lblNameText.setValue(TextUtils.isEmpty(name) ?
                    getString(R.string.activity_main_lblName_required) :
                    getString(R.string.activity_main_lblName));
            vm.valid.setValue(!TextUtils.isEmpty(name));
        });
*/
        if (savedInstanceState == null) {
            b.lblName.setTypeface(null, Typeface.BOLD);
        }
    }

}
