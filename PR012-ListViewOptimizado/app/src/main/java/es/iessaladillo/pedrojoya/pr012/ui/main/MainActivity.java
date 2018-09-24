package es.iessaladillo.pedrojoya.pr012.ui.main;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;

import es.iessaladillo.pedrojoya.pr012.R;
import es.iessaladillo.pedrojoya.pr012.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr012.data.local.Database;
import es.iessaladillo.pedrojoya.pr012.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr012.utils.ToastUtils;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private ListView lstStudents;

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);

        listAdapter = new MainActivityAdapter(viewModel.getStudents(false));
        listAdapter.setCallListener((view, position) -> callStudent(listAdapter.getItem(position)));
        listAdapter.setShowMarksListener((view, position) -> showStudentMarks(listAdapter.getItem(position)));
        lstStudents.setEmptyView(ActivityCompat.requireViewById(this, R.id.lblEmpty));
        lstStudents.setAdapter(listAdapter);
        lstStudents.setOnItemClickListener((parent, view, position, id) -> showStudent
                (listAdapter.getItem(position)));
        lstStudents.setOnItemLongClickListener((parent, view, position, id) -> {
            deleteStudent(listAdapter.getItem(position));
            return true;
        });
    }

    private void showStudent(Student student) {
        ToastUtils.toast(this, student.getName());
    }

    private void deleteStudent(Student student) {
        viewModel.deleteStudent(student);
        listAdapter.submitList(viewModel.getStudents(true));
    }

    private void callStudent(Student student) {
        ToastUtils.toast(this, getString(R.string.main_activity_call_sb, student.getName()));
    }

    private void showStudentMarks(Student student) {
        ToastUtils.toast(this, getString(R.string.main_activity_show_sb_marks, student.getName()));
    }

}
