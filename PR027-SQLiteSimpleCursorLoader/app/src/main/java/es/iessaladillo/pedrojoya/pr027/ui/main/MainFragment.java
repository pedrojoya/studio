package es.iessaladillo.pedrojoya.pr027.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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

import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.data.Repository;
import es.iessaladillo.pedrojoya.pr027.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr027.data.local.DbContract;
import es.iessaladillo.pedrojoya.pr027.data.local.StudentDao;
import es.iessaladillo.pedrojoya.pr027.data.model.Student;
import es.iessaladillo.pedrojoya.pr027.ui.student.StudentActivity;
import es.iessaladillo.pedrojoya.pr027.utils.SimpleCursorLoader;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int STUDENTS_LOADER = 0;

    private FloatingActionButton fab;
    private TextView lblEmptyView;
    private RecyclerView lstStudents;

    private MainFragmentAdapter adapter;
    private Repository repository;

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
        initViews(getView());
        repository = RepositoryImpl.getInstance(getActivity());
        getActivity().getSupportLoaderManager().initLoader(STUDENTS_LOADER, null, this);
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
        repository.onDestroy();
        super.onDestroy();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MainFragmentLoader(getActivity(), repository);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            adapter.setData(StudentDao.mapStudentsFromCursor(data));
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.setData(null);
    }

    static class MainFragmentLoader extends SimpleCursorLoader {

        private final Repository repository;

        public MainFragmentLoader(Context context, Repository repository) {
            super(context);
            this.repository = repository;
        }

        @Override
        protected Cursor getCursor() {
            return repository.queryStudents();
        }

        @Override
        protected Uri getUri() {
            return Uri.parse(DbContract.Student.NOTIFICATION_URI);
        }
    }

    private class DeleteStudentTask extends AsyncTask<Student, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Student... students) {
            return repository.deleteStudent(students[0].getId());
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                showSuccessDeletingStudent();
            } else {
                showErrorDeletingStudent();
            }
        }

    }

}
