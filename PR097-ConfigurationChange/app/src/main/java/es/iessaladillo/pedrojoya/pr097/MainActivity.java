package es.iessaladillo.pedrojoya.pr097;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private Button btnSave;
    private Button btnRetain;
    private Button btnIcepick;
    private Button btnStarter;
    private Button btnViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnSave = ActivityCompat.requireViewById(this, R.id.btnSave);
        btnRetain = ActivityCompat.requireViewById(this, R.id.btnRetain);
        btnIcepick = ActivityCompat.requireViewById(this, R.id.btnIcepick);
        btnStarter = ActivityCompat.requireViewById(this, R.id.btnStarter);
        btnViewModel = ActivityCompat.requireViewById(this, R.id.btnViewModel);

        btnSave.setOnClickListener(v -> SaveActivity.start(MainActivity.this));
        btnRetain.setOnClickListener(v -> RetainActivity.start(MainActivity.this));
        btnIcepick.setOnClickListener(v -> IcepickActivity.start(MainActivity.this));
        btnStarter.setOnClickListener(v -> StarterActivity.start(MainActivity.this));
        btnViewModel.setOnClickListener(v -> ViewModelActivity.start(MainActivity.this));
    }

}
