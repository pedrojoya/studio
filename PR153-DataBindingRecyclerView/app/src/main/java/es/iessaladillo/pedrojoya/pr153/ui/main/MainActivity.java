package es.iessaladillo.pedrojoya.pr153.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.mooveit.library.Fakeit;

import java.util.List;
import java.util.Random;

import es.iessaladillo.pedrojoya.pr153.BR;
import es.iessaladillo.pedrojoya.pr153.R;
import es.iessaladillo.pedrojoya.pr153.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr153.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MainActivityAdapter adapter;
    private MainActivityViewModel viewModel;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        random = new Random();
        initViews();
    }

    private void initViews() {
        setupToolbar();
        setupFab();
        setupRecyclerView();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupFab() {
        binding.fabAccion.setOnClickListener(
                view -> viewModel.insertStudent(createFakeStudent()));
    }

    private void setupRecyclerView() {
        binding.lstAlumnos.setHasFixedSize(true);
        adapter = new MainActivityAdapter(BR.item);
        adapter.setEmptyView(binding.lblNoHayAlumnos);
        adapter.setOnItemClickListener(
                (view, item, position) -> Snackbar.make(binding.lstAlumnos,
                        getString(R.string.ha_pulsado_sobre, ((Student) item).getName()),
                        Snackbar.LENGTH_SHORT).show());
        adapter.setOnItemLongClickListener((view, item, position) -> viewModel.deleteStudent((
                (Student) item)));
        binding.lstAlumnos.setAdapter(adapter);
        binding.lstAlumnos.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        viewModel.getStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable List<Student> students) {
                adapter.setList(students);
            }
        });
    }

    private Student createFakeStudent() {
        return new Student(Fakeit.name().name(), Fakeit.address().streetAddress(),
                "http://lorempixel.com/100/100/abstract/" + (random.nextInt(10) + 1) + "/");
    }

}
