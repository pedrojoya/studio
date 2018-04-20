package es.iessaldillo.pedrojoya.pr159.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import es.iessaldillo.pedrojoya.pr159.R;
import es.iessaldillo.pedrojoya.pr159.data.Database;
import es.iessaldillo.pedrojoya.pr159.data.Repository;
import es.iessaldillo.pedrojoya.pr159.data.RepositoryImpl;
import es.iessaldillo.pedrojoya.pr159.data.model.Word;
import es.iessaldillo.pedrojoya.pr159.ui.detail.DetailActivity;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private RecyclerView grdWords;

    private MainActivityViewModel mViewModel;
    @SuppressWarnings("FieldCanBeLocal")
    private Repository mRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRepository = RepositoryImpl.getInstance(Database.getInstance());
        mViewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(mRepository)).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        grdWords = ActivityCompat.requireViewById(this, R.id.grdWords);

        setupToolbar();
        grdWords.setHasFixedSize(true);
        grdWords.setLayoutManager(new GridLayoutManager(this,
                getResources().getInteger(R.integer.main_activity_grid_columns)));
        grdWords.setItemAnimator(new DefaultItemAnimator());
        MainActivityAdapter adapter = new MainActivityAdapter(mViewModel.getData());
        adapter.setOnItemClickListener((view, word, position) -> showWordDetail(view, word));
        grdWords.setAdapter(adapter);
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        // The transitions library makes the toolbar loose its color sometimes.
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        setSupportActionBar(toolbar);
    }

    private void showWordDetail(View view, Word word) {
        DetailActivity.start(this, word, ViewCompat.requireViewById(view, R.id.imgPhoto));
    }

}