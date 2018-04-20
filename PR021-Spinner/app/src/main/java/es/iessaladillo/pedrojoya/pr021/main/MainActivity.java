package es.iessaladillo.pedrojoya.pr021.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr021.R;
import es.iessaladillo.pedrojoya.pr021.data.Database;
import es.iessaladillo.pedrojoya.pr021.data.Repository;
import es.iessaladillo.pedrojoya.pr021.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr021.data.model.Country;

public class MainActivity extends AppCompatActivity {

    private Spinner spnCountry;
    private Button btnShow;

    @SuppressWarnings("FieldCanBeLocal")
    private Repository mRepository;
    private MainActivityViewModel mViewModel;

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
        btnShow = ActivityCompat.requireViewById(this, R.id.btnShow);
        spnCountry = ActivityCompat.requireViewById(this, R.id.spnCountry);

        btnShow.setOnClickListener(v -> {
            // First option is the default one.
            if (spnCountry.getSelectedItemPosition() != 0) {
                showCountry((Country) spnCountry.getSelectedItem());
            }
        });
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country(R.drawable.no_flag,
                getString(R.string.main_activity_choose_one_country)));
        countries.addAll(mViewModel.getData());
        spnCountry.setAdapter(new MainActivityAdapter(countries));
        spnCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkIsValidForm();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void checkIsValidForm() {
        btnShow.setEnabled(spnCountry.getSelectedItemPosition() != 0);
    }

    private void showCountry(Country country) {
        Toast.makeText(this, country.getName(), Toast.LENGTH_SHORT).show();
    }

}