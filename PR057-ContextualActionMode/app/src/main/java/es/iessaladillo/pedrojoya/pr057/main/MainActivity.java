package es.iessaladillo.pedrojoya.pr057.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import es.iessaladillo.pedrojoya.pr057.R;
import es.iessaladillo.pedrojoya.pr057.utils.ListViewUtils;

public class MainActivity extends AppCompatActivity {

    private EditText txtStudent;
    private ListView lstSubjects;

    private MainActivityViewModel mViewModel;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
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
        // Se inicia el modo de acción contextual pasándole el objeto listener que atenderá a los
        // eventos del modo de acción contextual, que creamos de forma inline.
        startSupportActionMode(new android.support.v7.view.ActionMode.Callback() {

            @Override
            public boolean onPrepareActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
                // Nothing done.
                return false;
            }

            @Override
            public boolean onCreateActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.activity_main_contextual, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(android.support.v7.view.ActionMode mode,
                    MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnuEvaluate:
                        evaluateStudent();
                        break;
                    case R.id.mnuDelete:
                        removeStudent();
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(android.support.v7.view.ActionMode arg0) {
                // Nothing done.
            }

        });
        // Select view.
        v.setSelected(true);
    }

    private void removeStudent() {
        txtStudent.setText("");
    }

    private void evaluateStudent() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.main_activity_evaluate_student, txtStudent.getText().toString()),
                Toast.LENGTH_SHORT).show();
    }

    private void setupSubjectList() {
        mAdapter = new ArrayAdapter<>(this, R.layout.activity_main_item, R.id.lblSubject,
                mViewModel.getSubjects());
        lstSubjects.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lstSubjects.setAdapter(mAdapter);

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
        Toast.makeText(this, getString(R.string.main_activity_evaluate_subjects,
                TextUtils.join(", ", ListViewUtils.getSelectedItems(lstSubjects, false))),
                Toast.LENGTH_SHORT).show();
    }

    private void deleteSubjects() {
        List<Object> items = ListViewUtils.getSelectedItems(lstSubjects, true);
        for (Object item : items) {
            mAdapter.remove((String) item);
        }
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(),
                getResources().getQuantityString(R.plurals.main_activity_subjects_deleted,
                        items.size(), items.size()), Toast.LENGTH_SHORT).show();
    }

}