package es.iessaladillo.pedrojoya.pr057.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr057.R;
import es.iessaladillo.pedrojoya.pr057.utils.ListViewUtils;
import es.iessaladillo.pedrojoya.pr057.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private EditText txtStudent;
    private ListView lstSubjects;

    private MainActivityViewModel viewModel;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        txtStudent = ActivityCompat.requireViewById(this, R.id.txtStudent);
        lstSubjects = ActivityCompat.requireViewById(this, R.id.lstSubjects);

        setupStudent();
        setupSubjectList();
    }

    private void setupStudent() {
        txtStudent.setOnLongClickListener(v -> {
            startContextualMode(v);
            return true;
        });
    }

    private void startContextualMode(View v) {
        startSupportActionMode(new androidx.appcompat.view.ActionMode.Callback() {

            @Override
            public boolean onPrepareActionMode(androidx.appcompat.view.ActionMode mode, Menu menu) {
                // Nothing done.
                return false;
            }

            @Override
            public boolean onCreateActionMode(androidx.appcompat.view.ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.activity_main_contextual, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(androidx.appcompat.view.ActionMode mode,
                    MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnuEvaluate:
                        evaluateStudent();
                        break;
                    case R.id.mnuDelete:
                        deleteStudent();
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(androidx.appcompat.view.ActionMode arg0) {
                // Nothing done.
            }

        });
        // Select view.
        v.setSelected(true);
    }

    private void deleteStudent() {
        txtStudent.setText("");
    }

    private void evaluateStudent() {
        ToastUtils.toast(this, getString(R.string.main_activity_evaluate_student, txtStudent.getText().toString()));
    }

    private void setupSubjectList() {
        listAdapter = new ArrayAdapter<>(this, R.layout.activity_main_item, R.id.lblSubject,
                viewModel.getSubjects());
        lstSubjects.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lstSubjects.setAdapter(listAdapter);

        lstSubjects.setMultiChoiceModeListener(new MultiChoiceModeListener() {
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Nothing done.
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Nothing done.
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.activity_main_contextual, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnuEvaluate:
                        evaluateSubjects();
                        break;
                    case R.id.mnuDelete:
                        deleteSubjects();
                        break;
                }
                return true;
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
                    boolean checked) {
                // Update action bar title.
                mode.setTitle(getString(R.string.main_activity_number_of_number,
                        lstSubjects.getCheckedItemCount(), lstSubjects.getCount()));
            }
        });
        // Simple click also activates contextual action mode.
        lstSubjects.setOnItemClickListener(
                (adaptador, v, position, id) -> lstSubjects.setItemChecked(position, true));
    }

    private void evaluateSubjects() {
        ToastUtils.toast(this, getString(R.string.main_activity_evaluate_subjects,
                TextUtils.join(", ", ListViewUtils.getSelectedItems(lstSubjects, false))));
    }

    private void deleteSubjects() {
        List<String> items = ListViewUtils.getSelectedItems(lstSubjects, true);
        for (String item : items) {
            listAdapter.remove(item);
        }
        listAdapter.notifyDataSetChanged();
        ToastUtils.toast(this, getResources().getQuantityString(R.plurals.main_activity_subjects_deleted,
                        items.size(), items.size()));
    }

}