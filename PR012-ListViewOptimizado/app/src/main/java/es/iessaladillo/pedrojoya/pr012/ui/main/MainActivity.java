package es.iessaladillo.pedrojoya.pr012.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr012.R;
import es.iessaladillo.pedrojoya.pr012.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr012.data.local.Database;
import es.iessaladillo.pedrojoya.pr012.data.local.model.Student;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private ListView lstStudents;

    private MainActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(RepositoryImpl.getInstance(Database.getInstance()))).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);

        lstStudents.setEmptyView(ActivityCompat.requireViewById(this, R.id.lblEmpty));
        MainActivityAdapter adapter = new MainActivityAdapter(viewModel.getData());
        adapter.setCallListener((view, student, position) -> callStudent(student));
        adapter.setShowMarksListener((view, student, position) -> showStudentMarks(student));
        lstStudents.setAdapter(adapter);
    }

    private void callStudent(Student student) {
        Toast.makeText(this, getString(R.string.main_activity_show_sb_marks,
                student.getName()), Toast.LENGTH_SHORT).show();
    }

    private void showStudentMarks(Student student) {
        Toast.makeText(this, getString(R.string.main_activity_call_sb,
                student.getName()), Toast.LENGTH_SHORT).show();
    }

}
