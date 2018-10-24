package es.iessaladillo.pedrojoya.pr014.ui.main;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr014.R;
import es.iessaladillo.pedrojoya.pr014.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr014.data.local.Database;
import es.iessaladillo.pedrojoya.pr014.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr014.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
                MainActivityViewModel.class);
        setupViews();
    }

    private void setupViews() {
        ListView lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);

        lstStudents.setEmptyView(ActivityCompat.requireViewById(this, R.id.lblEmptyView));
        listAdapter = new MainActivityAdapter(viewModel.getStudents(false),
                (item, student, position) -> deleteStudent(listAdapter.getItem(position)));
        lstStudents.setAdapter(listAdapter);
        lstStudents.setOnItemClickListener(
                (parent, view, position, id) -> showStudent(listAdapter.getItem(position)));
    }

    private void showStudent(Student student) {
        ToastUtils.toast(this,
                getString(R.string.main_activity_student_selected, student.getName()));
    }

    private void deleteStudent(Student student) {
        viewModel.deleteStudent(student);
        listAdapter.submitList(viewModel.getStudents(true));
    }

}
