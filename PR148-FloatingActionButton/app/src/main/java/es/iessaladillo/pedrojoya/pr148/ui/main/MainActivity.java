package es.iessaladillo.pedrojoya.pr148.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr148.R;
import es.iessaladillo.pedrojoya.pr148.utils.FragmentUtils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        if (savedInstanceState == null) {
            loadInitialFragment();
        }
    }

    private void setupViews() {
        setupToolbar();
        setupFab();
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = ActivityCompat.requireViewById(this,
            R.id.collapsingToolbarLayout);

        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitle(getTitle());
    }

    private void setupFab() {
        FloatingActionButton fab = ActivityCompat.requireViewById(this, R.id.fab);
        fab.setOnClickListener(v -> save());
    }

    private void loadInitialFragment() {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
            MainFragment.newInstance(), MainFragment.class.getSimpleName());
    }

    private void save() {
        Toast.makeText(this, getString(R.string.main_fab_clicked), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSettings) {
            showSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSettings() {
        Toast.makeText(this, getString(R.string.main_mnuSettings), Toast.LENGTH_SHORT).show();
    }

}
