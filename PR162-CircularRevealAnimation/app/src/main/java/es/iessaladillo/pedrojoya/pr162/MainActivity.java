package es.iessaladillo.pedrojoya.pr162;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final long ANIM_DURATION = 1000;
    private static final String STATE_PANEL_VISIBLE = "STATE_PANEL_VISIBLE";

    private LinearLayout llPanel;
    private boolean isPanelVisible;
    @SuppressWarnings("FieldCanBeLocal")
    private ImageView imgGallery;
    @SuppressWarnings("FieldCanBeLocal")
    private ImageView imgVideo;
    @SuppressWarnings("FieldCanBeLocal")
    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreInstanceState(savedInstanceState);
        initViews();
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isPanelVisible = savedInstanceState.getBoolean(STATE_PANEL_VISIBLE);
        }
    }

    private void initViews() {
        llPanel = ActivityCompat.requireViewById(this, R.id.llPanel);
        imgGallery = ActivityCompat.requireViewById(this, R.id.imgGallery);
        imgVideo = ActivityCompat.requireViewById(this, R.id.imgVideo);
        imgPhoto = ActivityCompat.requireViewById(this, R.id.imgPhoto);

        setupToolbar();
        llPanel.setVisibility(isPanelVisible ? View.VISIBLE : View.INVISIBLE);
        imgGallery.setOnClickListener(view -> showGallery());
        imgVideo.setOnClickListener(view -> showVideo());
        imgPhoto.setOnClickListener(view -> showPhoto());
    }

    private void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void showGallery() {
        Toast.makeText(getApplicationContext(), R.string.activity_main_imgGallery,
                Toast.LENGTH_SHORT).show();
        reveal(llPanel, ANIM_DURATION, true);
    }

    private void showVideo() {
        Toast.makeText(getApplicationContext(), R.string.activity_main_imgVideo, Toast.LENGTH_SHORT)
                .show();
        reveal(llPanel, ANIM_DURATION, true);
    }

    private void showPhoto() {
        Toast.makeText(getApplicationContext(), R.string.activity_main_imgPhoto, Toast.LENGTH_SHORT)
                .show();
        reveal(llPanel, ANIM_DURATION, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuAttach:
                togglePanel();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void togglePanel() {
        reveal(llPanel, ANIM_DURATION, llPanel.getVisibility() == View.VISIBLE);
    }

    @SuppressWarnings("SameParameterValue")
    private void reveal(final View view, long duration, boolean reverse) {
        int originX = view.getRight();
        int originY = view.getTop();
        int radio = Math.max(view.getWidth(), view.getHeight());
        Animator anim;
        if (!reverse) {
            anim = ViewAnimationUtils.createCircularReveal(view, originX, originY, 0, radio);
            // Visible before animation.
            view.setVisibility(View.VISIBLE);
        } else {
            // End radius will be 0 at the end of the animation.
            anim = ViewAnimationUtils.createCircularReveal(view, originX, originY, radio, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    // Invisible when animation finishes.
                    view.setVisibility(View.INVISIBLE);
                }
            });
        }
        anim.setDuration(duration);
        anim.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_PANEL_VISIBLE, llPanel.getVisibility() == View.VISIBLE);
        super.onSaveInstanceState(outState);
    }

}
