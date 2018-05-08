package es.iessaladillo.pedrojoya.pr153.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import es.iessaladillo.pedrojoya.pr153.BR;
import es.iessaladillo.pedrojoya.pr153.R;
import es.iessaladillo.pedrojoya.pr153.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr153.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr153.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MainActivityListAdapter adapter;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(RepositoryImpl.getInstance(getApplicationContext()), getApplicationContext().getResources()))
                .get(MainActivityViewModel.class);
        binding.setVm(viewModel);
        initViews();
    }

    private void initViews() {
        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupRecyclerView() {
        binding.lstStudents.setHasFixedSize(true);
        // OJO. NO se puede hacer con Data Binding porque entonces no se recupera correctamente
        // el estado de la lista, ya que el LayoutManager sería asignado por el Data Binding más
        // tarde que la restauración del estado.
        binding.lstStudents.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        adapter = new MainActivityListAdapter(BR.item);
        binding.lstStudents.setAdapter(adapter);
        viewModel.getStudents().observe(this, students -> adapter.submitList(students));
    }

}
