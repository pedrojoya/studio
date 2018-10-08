package es.iessaldillo.pedrojoya.pr160.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import es.iessaldillo.pedrojoya.pr160.R;
import es.iessaldillo.pedrojoya.pr160.base.OnFabClickListener;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private FloatingActionButton fab;
    private ImageView imgHeader;

    private MainActivityAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        tabLayout = ActivityCompat.requireViewById(this, R.id.tabLayout);
        fab = ActivityCompat.requireViewById(this, R.id.fab);
        imgHeader = ActivityCompat.requireViewById(this, R.id.imgHeader);

        setupToolbar();
        setupViewPager();
        setupFab();
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Title must be shown in toolbar, because in collapsingToolbar whould show over the
            // tabLayout.
            getSupportActionBar().setTitle(getTitle());
        }
        CollapsingToolbarLayout collapsingToolbarLayout = ActivityCompat.requireViewById(this, R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitleEnabled(false);
    }

    private void setupViewPager() {
        viewPagerAdapter = new MainActivityAdapter(getSupportFragmentManager(), getApplicationContext());
        ViewPager viewPager = ActivityCompat.requireViewById(this, R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateHeader(position);
                updateFabState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }

    private void updateHeader(int position) {
        imgHeader.setImageResource(viewPagerAdapter.getPageHeader(position));
        imgHeader.invalidate();
    }

    private void updateFabState(int position) {
        if (position == 1) {
            fab.hide();
        } else {
            fab.show();
        }
    }

    private void setupFab() {
        fab.setOnClickListener(this::callFabOnFragment);
    }

    private void callFabOnFragment(View view) {
        OnFabClickListener frg = (OnFabClickListener) viewPagerAdapter.getFragment(tabLayout.getSelectedTabPosition());
        if (frg != null) {
            frg.onFabClick(view);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuSettings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
