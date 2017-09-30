package es.iessaldillo.pedrojoya.pr159.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.kogitune.activity_transition.ExitActivityTransition;

import es.iessaldillo.pedrojoya.pr159.R;
import es.iessaldillo.pedrojoya.pr159.data.model.Word;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_WORD = "EXTRA_WORD";

    private ImageView imgDetailPhoto;
    private WebView webView;
    private ImageView imgAppBarPhoto;
    private AppBarLayout appbarLayout;

    private Word mWord = null;
    private ExitActivityTransition mExitTransition;
    private boolean mOrientacionChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupTransition();
        obtainIntent();
        initVistas();
        animate(savedInstanceState);
    }

    private void obtainIntent() {
        if (getIntent() != null && getIntent().hasExtra(EXTRA_WORD)) {
            mWord = getIntent().getParcelableExtra(EXTRA_WORD);
        } else {
            finish();
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        // AppBarLayout initially hidden until transition finished.
        appbarLayout = findViewById(R.id.appbarLayout);
        appbarLayout.setVisibility(View.INVISIBLE);
        appbarLayout.setExpanded(true);
    }


    private void setupTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            transition.excludeTarget(android.R.id.navigationBarBackground, true);
            transition.excludeTarget(R.id.appbarLayout, true);
            transition.excludeTarget(R.id.toolbar, true);
            transition.excludeTarget(R.id.collapsingToolbar, true);
            getWindow().setEnterTransition(transition);
        }
    }

    private void initVistas() {
        imgDetailPhoto = findViewById(R.id.imgDetailPhoto);
        imgAppBarPhoto = findViewById(R.id.imgAppBarPhoto);
        webView = findViewById(R.id.webView);

        setupToolbar();
        showWordDetail();
    }

    private void showWordDetail() {
        imgDetailPhoto.setImageResource(mWord.getPhotoResId());
        // imgAppBarPhoto only available in portrait orientation.
        if (imgAppBarPhoto != null) {
            imgAppBarPhoto.setImageResource(mWord.getPhotoResId());
        }
        setTitle(mWord.getEnglish());
        webView.loadUrl("http://www.thefreedictionary.com/" + mWord.getEnglish());
    }

    private void animate(Bundle savedInstanceState) {
        mExitTransition = ActivityTransition.with(getIntent()).to(imgDetailPhoto).duration(
                getResources().getInteger(R.integer.duracion)).start(savedInstanceState);
        appbarLayout.postDelayed(() -> {
            appbarLayout.setVisibility(View.VISIBLE);
            if (getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_PORTRAIT) {
                imgDetailPhoto.setVisibility(View.INVISIBLE);
            }
        }, getResources().getInteger(R.integer.duracion));
        // Needed in order no to animate again after orientation change.
        mOrientacionChange = savedInstanceState != null;
    }

    public static void start(Activity activity, @NonNull Word word, @NonNull View sharedView) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_WORD, word);
        ActivityTransitionLauncher.with(activity).from(sharedView).launch(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!mOrientacionChange) {
            animateExit();
        } else {
            finish();
        }
    }

    private void animateExit() {
        imgDetailPhoto.setVisibility(View.VISIBLE);
        appbarLayout.setVisibility(View.INVISIBLE);
        mExitTransition.exit(this);
    }

    // It doesn't work with onNavigateUp.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
