package es.iessaladillo.pedrojoya.pr002.ui.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr002.R;
import es.iessaladillo.pedrojoya.pr002.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr002.utils.ToastUtils;

public class MainActivity extends AppCompatActivity implements OnClickListener,
        OnCheckedChangeListener {

    private EditText txtName;
    private CheckBox chkPolite;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnGreet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        chkPolite = ActivityCompat.requireViewById(this, R.id.chkPolite);
        btnGreet = ActivityCompat.requireViewById(this, R.id.btnGreet);

        chkPolite.setOnCheckedChangeListener(this);
        btnGreet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnGreet) {
            greet();

        }
    }

    private void greet() {
        StringBuilder sb = new StringBuilder(getString(R.string.main_good_morning));
        if (chkPolite.isChecked()) {
            sb.append(getString(R.string.main_nice_to_meet_you));
        }
        sb.append(" ").append(txtName.getText());
        KeyboardUtils.hideKeyboard(this);
        ToastUtils.toast(this, sb.toString());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        chkPolite.setText(isChecked ? getString(R.string.main_polite_mode) : getString(
                R.string.main_impolite_mode));
    }

}
