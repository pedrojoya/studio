package es.iessaladillo.pedrojoya.pr170.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr170.R;
import es.iessaladillo.pedrojoya.pr170.databinding.ActivityMainBinding;
import es.iessaladillo.pedrojoya.pr170.utils.KeyboardUtils;

public class MainActivity extends AppCompatActivity {

    private static final String PHOTO_BASE_URL = "https://dummyimage.com/356x200/deb4de/ffffff&text=";

    private ActivityMainModel model;
    private ActivityMainBinding binding;
    private final Random random = new Random();
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        model = viewModel.getModel();
        binding.setModel(model);
        binding.setPresenter(this);
        if (savedInstanceState == null) {
            changePhoto();
            binding.lblName.setTypeface(null, Typeface.BOLD);
        }
    }

    public void greet() {
        //ActivityMainBinding binding = DataBindingUtil.findBinding(v);
        //ActivityMainModel viewModel = binding.getViewModel();
        String message = getString(R.string.main_activity_good_morning);
        if (model.isPolite()) {
            message = message + getString(R.string.main_activity_nice_to_meet_you);
            if (!TextUtils.isEmpty(model.getTreatment())) {
                message += " " + model.getTreatment();
            }
        }
        message += " " + model.getName();
        KeyboardUtils.hideKeyboard(binding.fab);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("SameReturnValue")
    public boolean insult() {
        KeyboardUtils.hideKeyboard(binding.fab);
        Toast.makeText(this, getString(R.string.main_activity_insult, model.getName()),
                Toast.LENGTH_SHORT).show();
        return true;
    }

    @SuppressWarnings("WeakerAccess")
    public void changePhoto() {
        model.setPhotoUrl(PHOTO_BASE_URL + random.nextInt(100));
    }

    public void txtNameOnChange() {
        binding.lblName.setText(TextUtils.isEmpty(binding.txtName.getText().toString()) ?
                                getString(R.string.activity_main_lblName_required) :
                                getString(R.string.activity_main_lblName));
    }

    @SuppressWarnings("unused")
    public void txtNameOnFocusChange(View v, boolean hasFocus) {
        binding.lblName.setTypeface(null, hasFocus ? Typeface.BOLD : Typeface.NORMAL);
    }

    @SuppressWarnings("unused")
    public void boldOnFocusOnFocusChange(View v, boolean hasFocus) {
        binding.lblName.setTypeface(null, hasFocus ? Typeface.BOLD : Typeface.NORMAL);
    }


}
