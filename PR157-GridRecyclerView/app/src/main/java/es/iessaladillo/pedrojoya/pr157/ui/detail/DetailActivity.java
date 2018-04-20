package es.iessaladillo.pedrojoya.pr157.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import es.iessaladillo.pedrojoya.pr157.R;
import es.iessaladillo.pedrojoya.pr157.data.model.Word;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_WORD = "EXTRA_WORD";

    private Word mWord = null;
    private ImageView imgDetailPhoto;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupTransition();
        obtainIntent();
        initViews();
    }

    private void obtainIntent() {
        if (getIntent() != null && getIntent().hasExtra(EXTRA_WORD)) {
            mWord = getIntent().getParcelableExtra(EXTRA_WORD);
        } else {
            finish();
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        AppBarLayout appbar = ActivityCompat.requireViewById(this, R.id.appbarLayout);
        appbar.setExpanded(true);
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

    private void initViews() {
        imgDetailPhoto = ActivityCompat.requireViewById(this, R.id.imgDetailPhoto);
        webView = ActivityCompat.requireViewById(this, R.id.webView);

        setupToolbar();
        showWordDetail();
    }

    private void showWordDetail() {
        imgDetailPhoto.setImageResource(mWord.getPhotoResId());
        setTitle(mWord.getEnglish());
        webView.loadUrl("http://www.thefreedictionary.com/" + mWord.getEnglish());
    }

    public static void start(Activity activity, @NonNull Word word, @NonNull View sharedView) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_WORD, word);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                sharedView, ViewCompat.getTransitionName(sharedView));
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
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
