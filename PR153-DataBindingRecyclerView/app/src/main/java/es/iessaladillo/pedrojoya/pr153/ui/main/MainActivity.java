package es.iessaladillo.pedrojoya.pr153.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import es.iessaladillo.pedrojoya.pr153.BR;
import es.iessaladillo.pedrojoya.pr153.R;
import es.iessaladillo.pedrojoya.pr153.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr153.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityViewModel viewModel = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(
                        RepositoryImpl.getInstance(getApplicationContext()),
                        getApplicationContext().getResources())).get(MainActivityViewModel.class);
        b.setVm(viewModel);
        b.setLifecycleOwner(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        b.lstStudents.setHasFixedSize(true);
        // OJO. NO se puede hacer con Data Binding porque entonces no se recupera correctamente
        // el estado de la lista, ya que el LayoutManager sería asignado por el Data Binding más
        // tarde que la restauración del estado.
        b.lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MainActivityListAdapter adapter = new MainActivityListAdapter(BR.item);
        b.lstStudents.setAdapter(adapter);
    }

}
