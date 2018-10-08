package es.iessaladillo.pedrojoya.pr148.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr148.R;
import es.iessaladillo.pedrojoya.pr148.utils.FragmentUtils;


public class MainActivity extends AppCompatActivity {

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = ActivityCompat.requireViewById(this,
                R.id.collapsingToolbar);

        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitle(getTitle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        // Load initial fragment
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                    MainFragment.newInstance(), MainFragment.class.getSimpleName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSettings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
