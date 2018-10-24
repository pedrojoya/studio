package es.iessaladillo.pedrojoya.pr097.ui.main;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr097.R;
import es.iessaladillo.pedrojoya.pr097.ui.retain.RetainActivity;
import es.iessaladillo.pedrojoya.pr097.ui.save.SaveActivity;
import es.iessaladillo.pedrojoya.pr097.ui.state.AndroidStateActivity;
import es.iessaladillo.pedrojoya.pr097.ui.viewmodel.ViewModelActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        Button btnSave = ActivityCompat.requireViewById(this, R.id.btnSave);
        Button btnRetain = ActivityCompat.requireViewById(this, R.id.btnRetain);
        Button btnIcepick = ActivityCompat.requireViewById(this, R.id.btnState);
        Button btnViewModel = ActivityCompat.requireViewById(this, R.id.btnViewModel);

        btnSave.setOnClickListener(v -> SaveActivity.start(MainActivity.this));
        btnRetain.setOnClickListener(v -> RetainActivity.start(MainActivity.this));
        btnIcepick.setOnClickListener(v -> AndroidStateActivity.start(MainActivity.this));
        btnViewModel.setOnClickListener(v -> ViewModelActivity.start(MainActivity.this));
    }

}
