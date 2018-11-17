package es.iessaladillo.pedrojoya.pr018.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import es.iessaladillo.pedrojoya.pr018.R;
import es.iessaladillo.pedrojoya.pr018.utils.FragmentUtils;
import es.iessaladillo.pedrojoya.pr018.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Load initial fragment.
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                MainFragment.newInstance(), MainFragment.class.getSimpleName());
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
            ToastUtils.toast(this, R.string.main_settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}