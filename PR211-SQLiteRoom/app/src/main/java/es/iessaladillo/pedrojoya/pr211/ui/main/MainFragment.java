package es.iessaladillo.pedrojoya.pr211.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
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

import java.lang.ref.WeakReference;

import es.iessaladillo.pedrojoya.pr211.R;
import es.iessaladillo.pedrojoya.pr211.data.Repository;
import es.iessaladillo.pedrojoya.pr211.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr211.data.model.Student;
import es.iessaladillo.pedrojoya.pr211.ui.student.StudentActivity;

public class MainFragment extends Fragment {

    private FloatingActionButton fab;
    private TextView lblEmptyView;
    private RecyclerView lstStudents;

    private MainFragmentListAdapter adapter;
    private Repository repository;
    private MainActivityViewModel viewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        repository = RepositoryImpl.getInstance(requireActivity());
        viewModel = ViewModelProviders.of(requireActivity(),
                new MainActivityViewModelFactory(repository)).get(MainActivityViewModel.class);
        initViews(getView());
    }

    private void initViews(View view) {
        fab = requireActivity().findViewById(R.id.fab);
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);
        lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);

        setupFab();
        setupRecyclerView();
        viewModel.getStudents().observe(requireActivity(), students -> {
            if (students != null) {
                // New data list for adapter (with automatic diffcallback calculations).
                adapter.submitList(students);
            }
        });
    }

    private void setupFab() {
        fab.setOnClickListener(v -> addStudent());
    }

    private void setupRecyclerView() {
        lstStudents.setHasFixedSize(true);
        adapter = new MainFragmentListAdapter();
        adapter.setOnItemClickListener((view, student, position) -> editStudent(student));
        adapter.setEmptyView(lblEmptyView);
        lstStudents.setAdapter(adapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        lstStudents.addItemDecoration(
                new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
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
        StudentActivity.start(requireActivity());
    }

    private void editStudent(Student student) {
        StudentActivity.start(requireActivity(), student.getId());
    }

    private void deleteStudent(int position) {
        Student student = adapter.getItemAtPosition(position);
        (new DeleteStudentTask(this, repository)).execute(student);
    }

    private void showSuccessDeletingStudent() {
        Toast.makeText(requireActivity(), R.string.main_fragment_student_deleted, Toast.LENGTH_SHORT)
                .show();
    }

    private void showErrorDeletingStudent() {
        Toast.makeText(requireActivity(), R.string.main_fragment_error_deleting_student,
                Toast.LENGTH_SHORT).show();
    }

    private static class DeleteStudentTask extends AsyncTask<Student, Void, Integer> {

        final WeakReference<MainFragment> mainFragmentWeakReference;
        final Repository repository;

        DeleteStudentTask(MainFragment mainFragment, Repository repository) {
            this.mainFragmentWeakReference = new WeakReference<>(mainFragment);
            this.repository = repository;
        }

        @Override
        protected Integer doInBackground(Student... students) {
            return repository.deleteStudent(students[0]);
        }

        @Override
        protected void onPostExecute(Integer deletions) {
            if (mainFragmentWeakReference.get() != null) {
                if (deletions == 1) {
                    mainFragmentWeakReference.get().showSuccessDeletingStudent();
                } else {
                    mainFragmentWeakReference.get().showErrorDeletingStudent();
                }
            }
        }

    }

}
