package es.iessaladillo.pedrojoya.pr211.ui.main;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
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

import es.iessaladillo.pedrojoya.pr211.R;
import es.iessaladillo.pedrojoya.pr211.data.Repository;
import es.iessaladillo.pedrojoya.pr211.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr211.data.model.Student;
import es.iessaladillo.pedrojoya.pr211.ui.student.StudentActivity;

public class MainFragment extends Fragment {

    private FloatingActionButton fab;
    private TextView lblEmptyView;
    private RecyclerView lstStudents;

    private MainFragmentAdapter adapter;
    private Repository repository;
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
        viewModel = ViewModelProviders.of(getActivity(),
                new MainActivityViewModelFactory(repository)).get(MainActivityViewModel.class);
        initViews(getView());
    }

    private void initViews(View view) {
        fab = getActivity().findViewById(R.id.fab);
        lblEmptyView = view.findViewById(R.id.lblEmptyView);
        lstStudents = view.findViewById(R.id.lstStudents);

        setupFab();
        setupRecyclerView();
        viewModel.getStudents().observe((LifecycleOwner) getActivity(), students -> {
            if (students != null) {
                adapter.setData(students);
            }
        });
    }

    private void setupFab() {
        fab.setOnClickListener(v -> addStudent());
    }

    private void setupRecyclerView() {
        lstStudents.setHasFixedSize(true);
        adapter = new MainFragmentAdapter();
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
        StudentActivity.start(getActivity());
    }

    private void editStudent(Student student) {
        StudentActivity.start(getActivity(), student.getId());
    }

    private void deleteStudent(int position) {
        Student student = adapter.getItemAtPosition(position);
        (new DeleteStudentTask()).execute(student);
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
        super.onDestroy();
    }

    private class DeleteStudentTask extends AsyncTask<Student, Void, Integer> {

        @Override
        protected Integer doInBackground(Student... students) {
            return repository.deleteStudent(students[0]);
        }

        @Override
        protected void onPostExecute(Integer deletions) {
            if (deletions == 1) {
                showSuccessDeletingStudent();
            } else {
                showErrorDeletingStudent();
            }
        }

    }

}
