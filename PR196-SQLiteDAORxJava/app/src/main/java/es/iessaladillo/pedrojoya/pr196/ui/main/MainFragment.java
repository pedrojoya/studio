package es.iessaladillo.pedrojoya.pr196.ui.main;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr196.R;
import es.iessaladillo.pedrojoya.pr196.data.Repository;
import es.iessaladillo.pedrojoya.pr196.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr196.data.model.Student;
import es.iessaladillo.pedrojoya.pr196.ui.student.StudentActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment {

    private static final int RC_ADD_STUDENT = 1;
    private static final int RC_EDIT_STUDENT = 2;

    private FloatingActionButton fab;
    private TextView lblEmptyView;
    private RecyclerView lstStudents;

    private MainFragmentAdapter adapter;
    private Repository repository;
    private CompositeDisposable compositeDisposable;
    private MainActivityViewModel viewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        repository = RepositoryImpl.getInstance(getActivity());
        compositeDisposable = new CompositeDisposable();
        viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
        initViews(getView());
        if (savedInstanceState == null) {
            loadStudents();
        }
    }

    private void initViews(View view) {
        fab = getActivity().findViewById(R.id.fab);
        lblEmptyView = view.findViewById(R.id.lblEmptyView);
        lstStudents = view.findViewById(R.id.lstStudents);

        setupFab();
        setupRecyclerView();
    }

    private void setupFab() {
        fab.setOnClickListener(v -> addStudent());
    }

    private void setupRecyclerView() {
        lstStudents.setHasFixedSize(true);
        adapter = new MainFragmentAdapter(viewModel.getStudents());
        adapter.setOnItemClickListener((view, student, position) -> editStudent(student));
        adapter.setEmptyView(lblEmptyView);
        lstStudents.setAdapter(adapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        lstStudents.addItemDecoration(
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        deleteStudent(viewHolder.getAdapterPosition());
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

    private void addStudent() {
        StudentActivity.startForResult(this, RC_ADD_STUDENT);
    }

    private void editStudent(Student student) {
        StudentActivity.startForResult(this, student.getId(), RC_EDIT_STUDENT);
    }

    private void deleteStudent(int position) {
        compositeDisposable.add(repository.deleteStudent(adapter.getItemAtPosition(position))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleted -> {
                    if (deleted == 1) {
                        showSuccessDeletingStudent();
                    } else {
                        showErrorDeletingStudent();
                    }
                    loadStudents();
                }));
    }

    private void showSuccessDeletingStudent() {
        Toast.makeText(getActivity(), R.string.main_fragment_student_deleted, Toast.LENGTH_SHORT)
                .show();
    }

    private void showErrorDeletingStudent() {
        Toast.makeText(getActivity(), R.string.main_fragment_error_deleting_student,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        adapter.onDestroy();
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == RC_ADD_STUDENT || requestCode == RC_EDIT_STUDENT)
                && resultCode == Activity.RESULT_OK) {
            loadStudents();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void loadStudents() {
        compositeDisposable.add(repository.getStudents()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(students -> {
                    viewModel.setStudents(students);
                    adapter.setData(students);
                }));
    }

}
