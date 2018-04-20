package es.iessaladillo.pedrojoya.pr105.ui.detail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import es.iessaladillo.pedrojoya.pr105.R;


@SuppressWarnings("FieldCanBeLocal")
public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private NestedScrollView nestedScrollView;
    private FloatingActionButton fabAccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
    }

    private void initViews() {
        toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        collapsingToolbarLayout = ActivityCompat.requireViewById(this, R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle(getTitle());
        nestedScrollView = ActivityCompat.requireViewById(this, R.id.nestedScrollView);
        fabAccion = ActivityCompat.requireViewById(this, R.id.fab);
        fabAccion.setOnClickListener(view -> showMessage());
    }

    private void showMessage() {
        Snackbar.make(nestedScrollView, R.string.detail_activity_fab_clicked, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
