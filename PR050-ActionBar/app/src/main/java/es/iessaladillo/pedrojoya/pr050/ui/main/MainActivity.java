package es.iessaladillo.pedrojoya.pr050.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import es.iessaladillo.pedrojoya.pr050.R;
import es.iessaladillo.pedrojoya.pr050.ui.info.InfoFragment;
import es.iessaladillo.pedrojoya.pr050.ui.photo.PhotoFragment;
import es.iessaladillo.pedrojoya.pr050.ui.preferences.PreferencesFragment;
import es.iessaladillo.pedrojoya.pr050.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity implements PhotoFragment.Callback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Load initial fragment.
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
                    PhotoFragment.newInstance(), PhotoFragment.class.getSimpleName(),
                    PhotoFragment.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuPreferences:
                showPreferences();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onInfoClicked() {
        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
                InfoFragment.newInstance(), InfoFragment.class.getSimpleName(),
                InfoFragment.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    }

    private void showPreferences() {
        // Only if it's not the current fragment.
        if (getSupportFragmentManager().findFragmentByTag(PreferencesFragment.class.getSimpleName())
                == null) {
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
                    PreferencesFragment.newInstance(), PreferencesFragment.class.getSimpleName(),
                    PreferencesFragment.class.getSimpleName(),
                    FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
