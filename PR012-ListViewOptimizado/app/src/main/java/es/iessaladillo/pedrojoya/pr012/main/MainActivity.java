package es.iessaladillo.pedrojoya.pr012.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import es.iessaladillo.pedrojoya.pr012.R;
import es.iessaladillo.pedrojoya.pr012.data.Database;
import es.iessaladillo.pedrojoya.pr012.data.Repository;
import es.iessaladillo.pedrojoya.pr012.data.RepositoryImpl;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private ListView lstStudents;

    private MainActivityViewModel viewModel;
    private Repository repository;
    private MainActivityAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = RepositoryImpl.getInstance(Database.getInstance());
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(repository)).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);

        lstStudents.setEmptyView(ActivityCompat.requireViewById(this, R.id.lblEmpty));
        adapter = new MainActivityAdapter(viewModel.getData());
        lstStudents.setAdapter(adapter);
    }

}
