package es.iessaladillo.pedrojoya.pr012.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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

        lstStudents.setEmptyView(ActivityCompat.requireViewById(this, R.id.lblEmpty));
        MainActivityAdapter adapter = new MainActivityAdapter(viewModel.getStudents());
        adapter.setCallListener((view, student, position) -> callStudent(student));
        adapter.setShowMarksListener((view, student, position) -> showStudentMarks(student));
        lstStudents.setAdapter(adapter);
    }

    private void callStudent(Student student) {
        ToastUtils.toast(this, getString(R.string.main_activity_show_sb_marks, student.getName()));
    }

    private void showStudentMarks(Student student) {
        ToastUtils.toast(this, getString(R.string.main_activity_call_sb, student.getName()));
    }

}
