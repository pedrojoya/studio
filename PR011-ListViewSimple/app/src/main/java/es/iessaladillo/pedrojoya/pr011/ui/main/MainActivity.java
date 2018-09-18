package es.iessaladillo.pedrojoya.pr011.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import es.iessaladillo.pedrojoya.pr011.R;
import es.iessaladillo.pedrojoya.pr011.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr011.data.local.Database;
import es.iessaladillo.pedrojoya.pr011.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private EditText txtName;
    private ImageButton btnAdd;

    private MainActivityViewModel viewModel;
    private ArrayAdapter<String> listAdapter;

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
        btnAdd = ActivityCompat.requireViewById(this, R.id.btnAdd);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        ListView lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);

        btnAdd.setOnClickListener(v -> addStudent());
        txtName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIsValidForm();
            }

        });
        txtName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (isValidForm()) {
                    addStudent();
                }
                return true;
            }
            return false;
        });
        lstStudents.setEmptyView(ActivityCompat.requireViewById(this, R.id.lblEmptyView));
        lstStudents.setOnItemClickListener(
                (adapterView, view, position, id) -> showStudent(listAdapter.getItem(position)));
        lstStudents.setOnItemLongClickListener((adapterView, view, position, id) -> {
            deleteStudent(listAdapter.getItem(position));
            return true;
        });
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                viewModel.getStudents());
        lstStudents.setAdapter(listAdapter);
        // Initial state
        checkIsValidForm();
    }

    private void showStudent(String student) {
        ToastUtils.toast(this, student);
    }

    private void checkIsValidForm() {
        btnAdd.setEnabled(isValidForm());
    }

    private boolean isValidForm() {
        return !TextUtils.isEmpty(txtName.getText().toString());
    }

    private void addStudent() {
        viewModel.addStudent(txtName.getText().toString());
        // ArrayAdapter always scroll to bottom on notifyDataSetChanged.
        listAdapter.notifyDataSetChanged();
        txtName.setText("");
        checkIsValidForm();
    }

    private void deleteStudent(String student) {
        viewModel.deleteStudent(student);
        // ArrayAdapter always scroll to bottom on notifyDataSetChanged.
        listAdapter.notifyDataSetChanged();
    }

}
