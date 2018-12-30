package es.iessaladillo.pedrojoya.pr050.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import es.iessaladillo.pedrojoya.pr050.R;
import es.iessaladillo.pedrojoya.pr050.ui.photo.PhotoFragment;
import es.iessaladillo.pedrojoya.pr050.ui.preferences.PreferencesActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            navigateToStartFragment();
        }
    }

    private void navigateToStartFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
            PhotoFragment.newInstance(), PhotoFragment.class.getSimpleName()).addToBackStack(
            PhotoFragment.class.getSimpleName()).setTransition(
            FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
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
                navigateToPreferences();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void navigateToPreferences() {
        PreferencesActivity.start(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
