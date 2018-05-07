package es.iessaladillo.pedrojoya.pr245.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr245.R;
import es.iessaladillo.pedrojoya.pr245.databinding.ActivityMainBinding;
import es.iessaladillo.pedrojoya.pr245.utils.KeyboardUtils;

public class MainActivity extends AppCompatActivity {

    private static final String PHOTO_BASE_URL =
            "https://dummyimage.com/356x200/deb4de/ffffff&text=";

    private ActivityMainBinding b;
    private final Random random = new Random();
    private MainActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vm = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(
                        getResources().getStringArray(R.array.activity_main_treatments)))
                .get(MainActivityViewModel.class);
        b.setVm(vm);
        b.setPresenter(this);
        if (savedInstanceState == null) {
            changePhoto();
            b.lblName.setTypeface(null, Typeface.BOLD);
        }
    }

    public void greet() {
        //ActivityMainBinding b = DataBindingUtil.findBinding(v);
        //ActivityMainModel vm = b.getViewModel();
        String message = getString(R.string.main_activity_good_morning);
        if (vm.isPolite()) {
            message = message + getString(R.string.main_activity_nice_to_meet_you);
            if (!TextUtils.isEmpty(vm.getTreatment())) {
                message += " " + vm.getTreatment();
            }
        }
        message += " " + vm.getName();
        KeyboardUtils.hideKeyboard(b.fab);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("SameReturnValue")
    public boolean insult() {
        KeyboardUtils.hideKeyboard(b.fab);
        Toast.makeText(this, getString(R.string.main_activity_insult, vm.getName()),
                Toast.LENGTH_SHORT).show();
        return true;
    }

    @SuppressWarnings("WeakerAccess")
    public void changePhoto() {
        vm.setPhotoUrl(PHOTO_BASE_URL + random.nextInt(100));
    }

    public void txtNameOnChange() {
        b.lblName.setText(TextUtils.isEmpty(b.txtName.getText().toString()) ?
                          getString(R.string.activity_main_lblName_required) :
                          getString(R.string.activity_main_lblName));
    }

    @SuppressWarnings("unused")
    public void txtNameOnFocusChange(View v, boolean hasFocus) {
        b.lblName.setTypeface(null, hasFocus ? Typeface.BOLD : Typeface.NORMAL);
    }

    @SuppressWarnings("unused")
    public void boldOnFocusOnFocusChange(View v, boolean hasFocus) {
        b.lblName.setTypeface(null, hasFocus ? Typeface.BOLD : Typeface.NORMAL);
    }


}
