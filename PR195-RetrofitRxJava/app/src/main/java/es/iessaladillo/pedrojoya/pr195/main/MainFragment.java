package es.iessaladillo.pedrojoya.pr195.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import es.iessaladillo.pedrojoya.pr195.R;
import es.iessaladillo.pedrojoya.pr195.base.EventObserver;
import es.iessaladillo.pedrojoya.pr195.data.model.Student;
import es.iessaladillo.pedrojoya.pr195.di.Injector;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private SwipeRefreshLayout swlPanel;

    private MainFragmentAdapter listAdapter;
    private MainFragmentViewModel viewModel;
    private TextView lblEmptyView;

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
            Injector.provideMainFragmentViewModelFactory(requireContext())).get(
            MainFragmentViewModel.class);
        setupViews(requireView());
        observeStudents();

    }

    private void setupViews(View view) {
        setupRecyclerView(view);
        setupPanel(view);
    }

    private void setupRecyclerView(View view) {
        RecyclerView lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);

        listAdapter = new MainFragmentAdapter();
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(new GridLayoutManager(requireContext(),
            requireContext().getResources().getInteger(R.integer.main_lstStudents_columns)));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(listAdapter);
    }

    private void setupPanel(View view) {
        swlPanel = ViewCompat.requireViewById(view, R.id.swlPanel);

        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light, android.R.color.holo_orange_light,
            android.R.color.holo_red_light);
        swlPanel.setOnRefreshListener(() -> viewModel.refreshStudents());
    }

    private void observeStudents() {
        viewModel.getLoading().observe(getViewLifecycleOwner(),
            loading -> swlPanel.post(() -> swlPanel.setRefreshing(loading)));
        viewModel.getMessage().observe(getViewLifecycleOwner(),
            new EventObserver<>(this::showErrorLoadingStudents));
        viewModel.getStudents().observe(getViewLifecycleOwner(), this::showStudents);
    }

    private void showStudents(List<Student> students) {
        listAdapter.submitList(students);
        lblEmptyView.setVisibility(students.isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }

    private void showErrorLoadingStudents(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuRefresh) {
            viewModel.refreshStudents();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
