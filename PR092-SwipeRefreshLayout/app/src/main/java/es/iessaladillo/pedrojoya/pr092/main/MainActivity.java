package es.iessaladillo.pedrojoya.pr092.main;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import es.iessaladillo.pedrojoya.pr092.R;
import es.iessaladillo.pedrojoya.pr092.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupToolbar();
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.container, new MainFragment());
        }
    }

    private void initViews() {
        toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

}
