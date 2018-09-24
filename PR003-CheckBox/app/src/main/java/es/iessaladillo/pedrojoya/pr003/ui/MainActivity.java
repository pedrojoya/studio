package es.iessaladillo.pedrojoya.pr003.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr003.R;

public class MainActivity extends AppCompatActivity {

    private CheckBox chkPolitely;
    private EditText txtName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        chkPolitely = ActivityCompat.requireViewById(this, R.id.chkPolitely);
        Button btnGreet = ActivityCompat.requireViewById(this, R.id.btnGreet);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);

        chkPolitely.setChecked(true);
        btnGreet.setOnClickListener(v -> {
            String message = getString(R.string.main_good_morning);
            if (chkPolitely.isChecked()) {
                message = message + " " + getString(R.string.main_mr_mrs);
            }
            message += " " + txtName.getText();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        });
        chkPolitely.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message = getString(R.string.main_politely_mode) + " ";
            if (isChecked) {
                message += getString(R.string.main_on);
            } else {
                message += getString(R.string.main_off);
            }
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        });
    }

}
