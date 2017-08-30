package es.iessaladillo.pedrojoya.pr011;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import es.iessaladillo.pedrojoya.pr011.components.MessageManager.MessageManager;
import es.iessaladillo.pedrojoya.pr011.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.pr011.data.Database;
import es.iessaladillo.pedrojoya.pr011.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr011.utils.ImageViewUtils;

public class MainActivity extends AppCompatActivity {

    private EditText txtName;
    private ImageButton btnAdd;
    private ListView lstStudents;

    private MainActivityViewModel mViewModel;
    private ArrayAdapter<String> mAdapter;
    private RepositoryImpl mRepository;
    private MessageManager mMessageManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRepository = RepositoryImpl.getInstance(Database.getInstance());
        mMessageManager = new ToastMessageManager();
        mViewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(mRepository)).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        btnAdd = findViewById(R.id.btnAdd);
        txtName = findViewById(R.id.txtName);
        lstStudents = findViewById(R.id.lstStudents);

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
        lstStudents.setEmptyView(findViewById(R.id.lblEmptyView));
        lstStudents.setOnItemClickListener(
                (adapterView, view, position, id) -> showStudent(mAdapter.getItem(position)));
        lstStudents.setOnItemLongClickListener((adapterView, view, position, id) -> {
            deleteStudent(position);
            return true;
        });
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mViewModel.getData());
        lstStudents.setAdapter(mAdapter);
        // Initial state
        ImageViewUtils.tintImageView(btnAdd, R.color.btn_color);
        checkIsValidForm();
    }

    private void showStudent(String student) {
        mMessageManager.showMessage(lstStudents, student);
    }

    private void checkIsValidForm() {
        btnAdd.setEnabled(isValidForm());
    }

    private boolean isValidForm() {
        return !TextUtils.isEmpty(txtName.getText().toString());
    }

    private void addStudent() {
        mRepository.addStudent(txtName.getText().toString());
        // ArrayAdapter always scroll to bottom on notifyDataSetChanged.
        mAdapter.notifyDataSetChanged();
        txtName.setText("");
        checkIsValidForm();
    }

    private void deleteStudent(int position) {
        mRepository.deleteStudent(position);
        // ArrayAdapter always scroll to bottom on notifyDataSetChanged.
        mAdapter.notifyDataSetChanged();
    }

}
