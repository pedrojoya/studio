package es.iessaladillo.pedrojoya.pr016.ui.main;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import java.util.List;

import es.iessaladillo.pedrojoya.pr016.R;
import es.iessaladillo.pedrojoya.pr016.data.local.Database;
import es.iessaladillo.pedrojoya.pr016.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Level;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr016.utils.CollectionUtils;
import es.iessaladillo.pedrojoya.pr016.utils.ToastUtils;

public class MainActivity extends AppCompatActivity implements OnChildClickListener {

    private MainActivityAdapter listAdapter;
    private MainActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(new
                        RepositoryImpl(Database.getInstance()))).get(MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        ExpandableListView lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);

        // We won't use default indicators for group or child.
        lstStudents.setGroupIndicator(null);
        lstStudents.setChildIndicator(null);
        listAdapter = new MainActivityAdapter(
                (List<String>) CollectionUtils.map(viewModel.getLevels(), Level::getName),
                viewModel.getStudents());
        lstStudents.setAdapter(listAdapter);
        // All groups initially expanded.
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            lstStudents.expandGroup(i);
        }
        lstStudents.setOnChildClickListener(this);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
            int childPosition, long id) {
        // Use getExpandableListAdapter() instead of getAdapter() in case you need the listAdapter.
        Student student = listAdapter.getChild(groupPosition, childPosition);
        ToastUtils.toast(this, getString(R.string.main_activity_student_info, student.getName()));
        return true;
    }

}
