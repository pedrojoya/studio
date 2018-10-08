package es.iessaladillo.pedrojoya.pr147.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import es.iessaladillo.pedrojoya.pr147.R;
import es.iessaladillo.pedrojoya.pr147.utils.ToastUtils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        setupToolbar();
        setupViewPager();
    }

    private void setupToolbar() {
        setSupportActionBar(ActivityCompat.requireViewById(this, R.id.toolbar));
    }

    private void setupViewPager() {
        ViewPager viewPager = ActivityCompat.requireViewById(this, R.id.viewPager);
        TabLayout tabLayout = ActivityCompat.requireViewById(this, R.id.tabLayout);

        MainActivityAdapter adapter = new MainActivityAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);
        // Not saved properly autatically on rotation.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // ---
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setIcon(adapter.getPageIcon(i));
            }
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
            ToastUtils.toast(this, getString(R.string.main_settings));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
