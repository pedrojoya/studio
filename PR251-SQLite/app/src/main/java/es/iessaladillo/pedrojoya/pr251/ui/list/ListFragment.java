package es.iessaladillo.pedrojoya.pr251.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr251.R;
import es.iessaladillo.pedrojoya.pr251.base.EventObserver;
import es.iessaladillo.pedrojoya.pr251.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr251.data.local.DbHelper;
import es.iessaladillo.pedrojoya.pr251.data.local.StudentDao;
import es.iessaladillo.pedrojoya.pr251.ui.student.StudentFragment;

@SuppressWarnings("WeakerAccess")
public class ListFragment extends Fragment {

    private ListFragmentAdapter listAdapter;
    private ListFragmentViewModel viewModel;
    private TextView lblEmptyView;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this,
            new ListFragmentViewModelFactory(requireActivity().getApplication(), new RepositoryImpl(
                StudentDao.getInstance(
                    DbHelper.getInstance(requireContext().getApplicationContext()))))).get(
            ListFragmentViewModel.class);
        setupViews(requireView());
        observeStudents();
        observeSuccessMessage();
        observeErrorMessage();
    }

    private void observeSuccessMessage() {
        viewModel.getSuccessMessage().observe(getViewLifecycleOwner(),
            new EventObserver<>(this::showMessage));
    }

    private void observeErrorMessage() {
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(),
            new EventObserver<>(this::showMessage));
    }

    private void showMessage(String message) {
        Snackbar.make(lblEmptyView, message, Snackbar.LENGTH_SHORT).show();
    }

    private void setupViews(View view) {
        setupToolbar();
        setupFab();
        setupRecyclerView(view);
    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(R.string.app_name);
        }
    }

    private void setupFab() {
        FloatingActionButton fab = ActivityCompat.requireViewById(requireActivity(), R.id.fab);
        fab.setImageResource(R.drawable.ic_add_white_24dp);
        fab.setOnClickListener(v -> navigateToAddStudent());
    }

    private void setupRecyclerView(View view) {
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);
        RecyclerView lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);

        lblEmptyView.setOnClickListener(v -> navigateToAddStudent());
        lstStudents.setHasFixedSize(true);
        listAdapter = new ListFragmentAdapter();
        lstStudents.setAdapter(listAdapter);
        lstStudents.setLayoutManager(new LinearLayoutManager(requireActivity()));
        lstStudents.addItemDecoration(
            new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    viewModel.deleteStudent(listAdapter.getItem(viewHolder.getAdapterPosition()));
                }
            });
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

    private void observeStudents() {
        viewModel.getStudents().observe(getViewLifecycleOwner(), students -> {
            listAdapter.submitList(students);
            lblEmptyView.setVisibility(students.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void navigateToAddStudent() {
        requireFragmentManager().beginTransaction().replace(R.id.flContent,
            StudentFragment.newInstance(), StudentFragment.class.getSimpleName()).addToBackStack(
            StudentFragment.class.getSimpleName()).setTransition(
            FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

}
