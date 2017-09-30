package es.iessaladillo.pedrojoya.pr157.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import es.iessaladillo.pedrojoya.pr157.R;
import es.iessaladillo.pedrojoya.pr157.data.Database;
import es.iessaladillo.pedrojoya.pr157.data.Repository;
import es.iessaladillo.pedrojoya.pr157.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr157.data.model.Word;
import es.iessaladillo.pedrojoya.pr157.ui.detail.DetailActivity;

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
        grdWords = findViewById(R.id.grdWords);

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void showWordDetail(View view, Word word) {
        DetailActivity.start(this, word, view.findViewById(R.id.imgPhoto));
    }

}