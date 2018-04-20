package es.iessaladillo.pedrojoya.pr014.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import es.iessaladillo.pedrojoya.pr014.R;
import es.iessaladillo.pedrojoya.pr014.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.pr014.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.pr014.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr014.data.model.Student;

public class MainActivity extends AppCompatActivity {

    private ListView lstStudents;

    private MainActivityViewModel mViewModel;
    private MainActivityAdapter mAdapter;
    private MessageManager mMessageManager;
    private RepositoryImpl mRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRepository = RepositoryImpl.getInstance();
        mMessageManager = new ToastMessageManager();
        mViewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(mRepository)).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        lstStudents.setEmptyView(ActivityCompat.requireViewById(this, R.id.lblEmptyView));
        mAdapter = new MainActivityAdapter(this, mViewModel.getData(),
                (item, student, position) -> deleteStudent(position));
        lstStudents.setAdapter(mAdapter);
        lstStudents.setOnItemClickListener((parent, view, position, id) -> showStudent(
                (Student) lstStudents.getItemAtPosition(position)));
    }

    private void showStudent(Student student) {
        mMessageManager.showMessage(lstStudents,
                getString(R.string.main_activity_student_selected, student.getName()));
    }

    private void deleteStudent(int position) {
        Student student = (Student) mAdapter.getItem(position);
        mRepository.deleteStudent(position);
        mAdapter.notifyDataSetChanged();
        mMessageManager.showMessage(lstStudents,
                getString(R.string.main_activity_student_deleted, student.getName()));
    }

}
