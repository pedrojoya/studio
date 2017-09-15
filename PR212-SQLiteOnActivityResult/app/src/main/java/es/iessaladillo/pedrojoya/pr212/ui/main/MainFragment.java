package es.iessaladillo.pedrojoya.pr212.ui.main;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import java.lang.ref.WeakReference;
import java.util.List;

import es.iessaladillo.pedrojoya.pr212.R;
import es.iessaladillo.pedrojoya.pr212.data.Repository;
import es.iessaladillo.pedrojoya.pr212.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr212.data.model.Student;
import es.iessaladillo.pedrojoya.pr212.ui.student.StudentActivity;

public class MainFragment extends Fragment {

    private static final int RC_ADD_STUDENT = 1;
    private static final int RC_EDIT_STUDENT = 2;


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
        if (savedInstanceState != null) {
            adapter.setData(viewModel.getStudents(false));
        } else {
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
        StudentActivity.startForResult(this, RC_ADD_STUDENT);
    }

    private void editStudent(Student student) {
        StudentActivity.startForResult(this, student.getId(), RC_EDIT_STUDENT);
    }

    private void deleteStudent(int position) {
        Student student = adapter.getItemAtPosition(position);
        (new DeleteStudentTask(this, repository)).execute(student);
    }

    private void loadStudents() {
        (new LoadStudentsTask(this, viewModel)).execute();
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
        repository.onDestroy();
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

    private static class LoadStudentsTask extends AsyncTask<Void, Void, List<Student>> {

        private final WeakReference<MainFragment> mainFragment;
        private final MainActivityViewModel viewModel;

        public LoadStudentsTask(MainFragment mainFragment, MainActivityViewModel viewModel) {
            this.mainFragment = new WeakReference<>(mainFragment);
            this.viewModel = viewModel;
        }

        @Override
        protected List<Student> doInBackground(Void... args) {
            return viewModel.getStudents(true);
        }

        @Override
        protected void onPostExecute(List<Student> students) {
            if (mainFragment.get() != null) {
                mainFragment.get().adapter.setData(students);
            }
        }

    }

    private static class DeleteStudentTask extends AsyncTask<Student, Void, Boolean> {

        private final WeakReference<MainFragment> mainFragmentWeakReference;
        private final Repository repository;

        private DeleteStudentTask(MainFragment mainFragment, Repository repository) {
            this.mainFragmentWeakReference = new WeakReference<>(mainFragment);
            this.repository = repository;
        }

        @Override
        protected Boolean doInBackground(Student... students) {
            return repository.deleteStudent(students[0].getId());
        }

        @Override
        protected void onPostExecute(Boolean success) {
            MainFragment mainFragment = mainFragmentWeakReference.get();
            if (mainFragment != null) {
                if (success) {
                    mainFragment.showSuccessDeletingStudent();
                    mainFragment.loadStudents();
                } else {
                    mainFragment.showErrorDeletingStudent();
                }
            }
        }

    }

}
