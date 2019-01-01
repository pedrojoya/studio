package es.iessaladillo.pedrojoya.pr105.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr105.R;


public class DetailActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    public static void start(Context context) {
        context.startActivity(new Intent(context, DetailActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupViews();
    }

    private void setupViews() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = ActivityCompat.requireViewById(this,
            R.id.collapsingToolbar);
        fab = ActivityCompat.requireViewById(this, R.id.fab);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        collapsingToolbarLayout.setTitle(getTitle());
        fab.setOnClickListener(view -> showMessage());
    }

    private void showMessage() {
        Snackbar.make(fab, R.string.detail_activity_fab_clicked, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onSupportNavigateUp();
        onBackPressed();
        return true;
    }

}
