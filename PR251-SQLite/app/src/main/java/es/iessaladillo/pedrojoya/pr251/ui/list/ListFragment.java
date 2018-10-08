package es.iessaladillo.pedrojoya.pr251.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import es.iessaladillo.pedrojoya.pr251.base.Event;
import es.iessaladillo.pedrojoya.pr251.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr251.data.local.DbHelper;
import es.iessaladillo.pedrojoya.pr251.data.local.StudentDao;
import es.iessaladillo.pedrojoya.pr251.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr251.ui.main.MainActivityViewModel;
import es.iessaladillo.pedrojoya.pr251.ui.student.StudentFragment;
import es.iessaladillo.pedrojoya.pr251.utils.FragmentUtils;
import es.iessaladillo.pedrojoya.pr251.utils.SnackbarUtils;

@SuppressWarnings("WeakerAccess")
public class ListFragment extends Fragment {

    private ListFragmentAdapter listAdapter;
    private ListFragmentViewModel viewModel;
    private FloatingActionButton fab;

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
        MainActivityViewModel activityViewModel = ViewModelProviders.of(requireActivity()).get(
                MainActivityViewModel.class);
        viewModel = ViewModelProviders.of(this, new ListFragmentViewModelFactory(new RepositoryImpl(
                StudentDao.getInstance(
                        DbHelper.getInstance(requireContext().getApplicationContext()))),
                activityViewModel)).get(ListFragmentViewModel.class);
        initViews(getView());
        activityViewModel.getInfoMessage().observe(getViewLifecycleOwner(), this::showMessage);
        viewModel.getStudents(false).observe(getViewLifecycleOwner(),
                students -> listAdapter.submitList(students));
    }

    private void showMessage(Event<Integer> messageEvent) {
        Integer messageResId = messageEvent.getContentIfNotHandled();
        if (messageResId != null) {
            SnackbarUtils.snackbar(fab, getString(messageResId));
        }
    }

    private void initViews(View view) {
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
        fab = ActivityCompat.requireViewById(requireActivity(), R.id.fab);
        fab.setImageResource(R.drawable.ic_add_white_24dp);
        fab.setOnClickListener(v -> addStudent());
    }

    private void setupRecyclerView(View view) {
        TextView lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);
        RecyclerView lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);

        lblEmptyView.setOnClickListener(v -> addStudent());
        lstStudents.setHasFixedSize(true);
        listAdapter = new ListFragmentAdapter();
        listAdapter.setOnItemClickListener(
                (v, position) -> editStudent(listAdapter.getItem(position)));
        listAdapter.setEmptyView(lblEmptyView);
        lstStudents.setAdapter(listAdapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
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
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                            int direction) {
                        deleteStudent(listAdapter.getItem(viewHolder.getAdapterPosition()));
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

    private void addStudent() {
        FragmentUtils.replaceFragmentAddToBackstack(requireFragmentManager(), R.id.flContent,
                StudentFragment.newInstance(), StudentFragment.class.getSimpleName(),
                StudentFragment.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    }

    private void editStudent(Student student) {
        FragmentUtils.replaceFragmentAddToBackstack(requireFragmentManager(), R.id.flContent,
                StudentFragment.newInstance(student.getId()), StudentFragment.class.getSimpleName(),
                StudentFragment.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    }

    private void deleteStudent(Student student) {
        viewModel.deleteStudent(student);
    }

}
