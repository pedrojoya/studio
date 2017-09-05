package es.iessaldillo.pedrojoya.pr160.main;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import es.iessaldillo.pedrojoya.pr160.R;
import es.iessaldillo.pedrojoya.pr160.base.OnFabClickListener;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private FloatingActionButton fab;
    private ImageView imgHeader;

    private MainActivityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        tabLayout = findViewById(R.id.tabLayout);
        fab = findViewById(R.id.fab);
        imgHeader = findViewById(R.id.imgHeader);

        setupToolbar();
        setupViewPager();
        setupFab();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Title must be shown in toolbar, because in collapsingToolbar whould show over the
            // tabLayout.
            getSupportActionBar().setTitle(getTitle());
        }
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitleEnabled(false);
    }

    private void setupViewPager() {
        mAdapter = new MainActivityAdapter(getSupportFragmentManager(), this);
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(mAdapter);
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
        imgHeader.setImageResource(mAdapter.getPageHeader(position));
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
        OnFabClickListener frg = (OnFabClickListener) mAdapter.getFragment(tabLayout.getSelectedTabPosition());
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
