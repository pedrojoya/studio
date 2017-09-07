package es.iessaladillo.pedrojoya.pr165.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import es.iessaladillo.pedrojoya.pr165.R;
import es.iessaladillo.pedrojoya.pr165.data.local.Database;
import es.iessaladillo.pedrojoya.pr165.data.local.Repository;
import es.iessaladillo.pedrojoya.pr165.data.local.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr165.data.model.Product;
import es.iessaladillo.pedrojoya.pr165.ui.new_product.NewProductDialogFragment;

public class MainActivity extends AppCompatActivity implements NewProductDialogFragment.Callback {

    private RecyclerView lstProducts;

    private Repository mRepository;
    private MainActivityViewModel mViewModel;
    private MainActivityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRepository = RepositoryImpl.getInstance(Database.getInstance());
        mViewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory()).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        lstProducts = findViewById(R.id.lstProducts);

        setupToolbar();
        setupRecyclerView();
        setupFab();
    }

    private void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void setupRecyclerView() {
        mAdapter = new MainActivityAdapter(mViewModel.getProducts());
        mAdapter.setOnItemClickListener(
                (view, product, position) -> toogleBoughtState(product, position));
        mAdapter.setOnItemLongClickListener(
                (view, product, position) -> deleteItem(product));
        lstProducts.setHasFixedSize(true);
        lstProducts.setAdapter(mAdapter);
        lstProducts.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstProducts.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        lstProducts.setItemAnimator(new DefaultItemAnimator());
    }

    private void toogleBoughtState(Product product, int position) {
        mRepository.toggleBoughtState(product);
        mAdapter.toggleBought(position);
    }

    private void deleteItem(Product product) {
        mRepository.deleteProduct(product);
        mAdapter.removeItem(product);
        Snackbar.make(lstProducts,
                getString(R.string.main_activity_product_deleted, product.getName()),
                Snackbar.LENGTH_SHORT).show();
    }

    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(
                view -> (new NewProductDialogFragment()).show(getSupportFragmentManager(),
                        getString(R.string.main_activity_add_product)));
    }

    @Override
    public void onAddClick(Product product) {
        mRepository.addProduct(product);
        mAdapter.addItem(product);
        Snackbar.make(lstProducts,
                getString(R.string.main_activity_product_added, product.getName()),
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelClick() {
    }

}
