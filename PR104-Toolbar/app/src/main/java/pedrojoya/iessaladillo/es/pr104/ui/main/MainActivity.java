package pedrojoya.iessaladillo.es.pr104.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import pedrojoya.iessaladillo.es.pr104.R;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        if (savedInstanceState == null) {
            navigateToStartFragment();
        }
    }

    private void navigateToStartFragment() {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.flContent, MainFragment.newInstance(), MainFragment.class.getSimpleName())
            .commit();
    }

    private void setupToolbar() {
        setSupportActionBar(ActivityCompat.requireViewById(this, R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSettings) {
            navigateToSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToSettings() {
        Toast.makeText(this, getString(R.string.main_settings), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
