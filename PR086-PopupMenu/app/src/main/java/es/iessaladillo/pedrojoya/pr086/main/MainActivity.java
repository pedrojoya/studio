package es.iessaladillo.pedrojoya.pr086.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr086.R;
import es.iessaladillo.pedrojoya.pr086.data.model.Student;

public class MainActivity extends AppCompatActivity implements MainActivityAdapter.Callback {

    @SuppressWarnings("FieldCanBeLocal")
    private ListView lstStudents;

    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);

        lstStudents.setAdapter(new MainActivityAdapter(this, mViewModel.getStudents(), this));
        lstStudents.setOnItemClickListener(
                (adapterView, view, position, id) -> showStudent(position));
    }

    private void showStudent(int position) {
        Toast.makeText(MainActivity.this, getString(R.string.main_activity_click_on,
                ((Student) lstStudents.getItemAtPosition(position)).getName()),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCall(Student student) {
        Toast.makeText(this, getString(R.string.main_activity_call_sb, student.getName()),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendMessage(Student student) {
        Toast.makeText(this, getString(R.string.main_activity_send_message_to, student.getName()),
                Toast.LENGTH_SHORT).show();
    }

}
