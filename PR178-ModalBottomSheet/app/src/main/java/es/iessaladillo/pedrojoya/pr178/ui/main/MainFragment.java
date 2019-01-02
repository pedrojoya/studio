package es.iessaladillo.pedrojoya.pr178.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr178.R;
import es.iessaladillo.pedrojoya.pr178.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr178.data.local.Database;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private MainFragmentViewModel viewModel;
    private MainFragmentAdapter listAdapter;
    private TextView lblEmptyView;

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this,
                new MainFragmentViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
                MainFragmentViewModel.class);
        setupViews(requireView());
        viewModel.getStudents().observe(getViewLifecycleOwner(), students -> {
            listAdapter.submitList(students);
            lblEmptyView.setVisibility(students.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void setupViews(View view) {
        setupToolbar(view);
        setupRecyclerView(view);
        setupFab(view);
    }

    private void setupFab(View view) {
        FloatingActionButton fab = ViewCompat.requireViewById(view, R.id.fab);

        fab.setOnClickListener(v -> viewModel.addStudent(Database.newFakeStudent()));
    }

    private void setupToolbar(View view) {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(
                ViewCompat.requireViewById(view, R.id.toolbar));
    }

    private void setupRecyclerView(View view) {
        RecyclerView lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);

        listAdapter = new MainFragmentAdapter();
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(new LinearLayoutManager(requireContext()));
        lstStudents.addItemDecoration(
                new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(listAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                            int direction) {
                        viewModel.deleteStudent(
                                listAdapter.getItem(viewHolder.getAdapterPosition()));
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

}
