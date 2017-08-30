package es.iessaladillo.pedrojoya.pr012.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import es.iessaladillo.pedrojoya.pr012.R;
import es.iessaladillo.pedrojoya.pr012.data.Database;
import es.iessaladillo.pedrojoya.pr012.data.Repository;
import es.iessaladillo.pedrojoya.pr012.data.RepositoryImpl;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private ListView lstStudents;

    private MainActivityViewModel mViewModel;
    private Repository mRepository;
    private MainActivityAdapter mAdapter;

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
        lstStudents = findViewById(R.id.lstStudents);

        lstStudents.setEmptyView(findViewById(R.id.lblEmpty));
        mAdapter = new MainActivityAdapter(this, mViewModel.getData());
        lstStudents.setAdapter(mAdapter);
    }

}
