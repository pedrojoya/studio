package es.iessaladillo.pedrojoya.pr050.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import es.iessaladillo.pedrojoya.pr050.R;
import es.iessaladillo.pedrojoya.pr050.preferences.PreferencesActivity;
import es.iessaladillo.pedrojoya.pr050.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity implements PhotoFragment.Callback,
        InfoFragment.Callback {

    private static final String TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT";
    private static final String TAG_INFO_FRAGMENT = "TAG_INFO_FRAGMENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT) == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                    PhotoFragment.newInstance(), TAG_MAIN_FRAGMENT);
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

    private void showPreferences() {
        PreferencesActivity.start(this);
    }

    @Override
    public void onInfoClicked() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_INFO_FRAGMENT) == null) {
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
                    InfoFragment.newInstance(), TAG_INFO_FRAGMENT, TAG_INFO_FRAGMENT,
                    FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        } else {
            onBackPressed();
        }
    }

    @Override
    public void onPhotoClicked() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT) == null) {
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
                    PhotoFragment.newInstance(), TAG_MAIN_FRAGMENT, TAG_MAIN_FRAGMENT,
                    FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        }
        else {
            onBackPressed();
        }
    }

}
