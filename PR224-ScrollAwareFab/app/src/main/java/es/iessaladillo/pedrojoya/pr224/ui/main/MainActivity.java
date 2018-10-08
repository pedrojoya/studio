package es.iessaladillo.pedrojoya.pr224.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr224.R;
import es.iessaladillo.pedrojoya.pr224.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_FAB_SCALE_X = "STATE_FAB_SCALE_X";
    private static final String STATE_FAB_SCALE_Y = "STATE_FAB_SCALE_Y";
    private static final String STATE_FAB_ROTATION = "STATE_FAB_ROTATION";

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupFab(savedInstanceState);
        // Load initial fragment
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                    MainFragment.newInstance(), MainFragment.class.getSimpleName());
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);

        setSupportActionBar(toolbar);
    }

    private void setupFab(Bundle savedInstanceState) {
        fab = ActivityCompat.requireViewById(this, R.id.fab);

        if (savedInstanceState != null) {
            fab.setScaleX(savedInstanceState.getFloat(STATE_FAB_SCALE_X, 1));
            fab.setScaleY(savedInstanceState.getFloat(STATE_FAB_SCALE_Y, 1));
            fab.setRotation(savedInstanceState.getFloat(STATE_FAB_ROTATION, 0));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(STATE_FAB_SCALE_X, fab.getScaleX());
        outState.putFloat(STATE_FAB_SCALE_Y, fab.getScaleY());
        outState.putFloat(STATE_FAB_ROTATION, fab.getRotation());
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
