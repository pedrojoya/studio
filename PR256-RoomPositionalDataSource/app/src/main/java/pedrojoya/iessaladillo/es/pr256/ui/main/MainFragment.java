package pedrojoya.iessaladillo.es.pr256.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pedrojoya.iessaladillo.es.pr256.R;
import pedrojoya.iessaladillo.es.pr256.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr256.data.local.AppDatabase;
import pedrojoya.iessaladillo.es.pr256.data.local.StudentDao;
import pedrojoya.iessaladillo.es.pr256.data.local.model.Student;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private MainFragmentViewModel viewModel;
    private MainFragmentAdapter listAdapter;
    private TextView lblEmpty;

    static Fragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this,
            new MainFragmentViewModelFactory(RepositoryImpl.getInstance(AppDatabase.getInstance(requireContext()).studentDao())))
            .get(MainFragmentViewModel.class);
        setupViews(requireView());
        observeStudents();
    }

    private void setupViews(View view) {
        setupToolbar(view);
        setupRecyclerView(view);
        setupFab(view);
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = ViewCompat.requireViewById(view, R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void setupFab(View view) {
        FloatingActionButton fabAccion = ViewCompat.requireViewById(view, R.id.fab);
        fabAccion.setOnClickListener(v -> viewModel.insertStudent(StudentDao.createFakeStudent()));
    }

    private void setupRecyclerView(View view) {
        RecyclerView lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);
        lblEmpty = ViewCompat.requireViewById(view, R.id.lblEmpty);

        listAdapter = new MainFragmentAdapter();
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(listAdapter);
        lstStudents.setLayoutManager(new LinearLayoutManager(requireContext()));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    Student student = listAdapter.getItem(viewHolder.getAdapterPosition());
                    if (student != null) {
                        viewModel.deleteStudent(student);
                    }
                }
            });
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

    private void observeStudents() {
        viewModel.getStudents().observe(this, studentPagedList -> {
            Log.d("Mia", "Nuevo pagedList");
            listAdapter.submitList(studentPagedList);
            lblEmpty.setVisibility(studentPagedList.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

}
