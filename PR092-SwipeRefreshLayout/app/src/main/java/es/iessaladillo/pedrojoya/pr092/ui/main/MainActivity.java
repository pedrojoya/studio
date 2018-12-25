package es.iessaladillo.pedrojoya.pr092.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr092.R;
import es.iessaladillo.pedrojoya.pr092.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent, new MainFragment());
        }
    }

    private void setupToolbar() {
        setSupportActionBar(ActivityCompat.requireViewById(this, R.id.toolbar));
    }

}
