package es.iessaladillo.pedrojoya.pr011.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr011.R;
import es.iessaladillo.pedrojoya.pr011.base.MessageManager;
import es.iessaladillo.pedrojoya.pr011.injection.Injector;
import es.iessaladillo.pedrojoya.pr011.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr011.utils.TextViewUtils;

public class MainActivity extends AppCompatActivity {

    private EditText txtName;
    private ImageButton btnAdd;
    private ListView lstStudents;

    private MainActivityViewModel viewModel;
    private ArrayAdapter<String> listAdapter;
    private MessageManager messageManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = Injector.getInstance().provideMainActivityViewModel(this);
        messageManager = Injector.getInstance().provideMessageManager(this);
        setupViews();
        checkInitialState();
    }

    private void checkInitialState() {
        checkIsValidForm();
    }

    private void setupViews() {
        btnAdd = ActivityCompat.requireViewById(this, R.id.btnAdd);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);

        btnAdd.setOnClickListener(v -> addStudent());
        TextViewUtils.addAfterTextChangedListener(txtName, s -> checkIsValidForm());
        TextViewUtils.setOnImeActionDoneListener(txtName, (v, event) -> addStudent());
        setupListView();
    }

    private void setupListView() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        lstStudents.setEmptyView(ActivityCompat.requireViewById(this, R.id.lblEmptyView));
        lstStudents.setOnItemClickListener(
            (adapterView, view, position, id) -> showStudent(listAdapter.getItem(position)));
        lstStudents.setOnItemLongClickListener((adapterView, view, position, id) -> {
            deleteStudent(listAdapter.getItem(position));
            return true;
        });
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
            viewModel.getStudents(false));
        lstStudents.setAdapter(listAdapter);
    }

    private void showStudent(String student) {
        KeyboardUtils.hideSoftKeyboard(this);
        if (student != null) {
            messageManager.showMessage(student);
        }
    }

    private void checkIsValidForm() {
        btnAdd.setEnabled(isValidForm());
    }

    private boolean isValidForm() {
        return !TextUtils.isEmpty(txtName.getText().toString());
    }

    private void addStudent() {
        if (isValidForm()) {
            KeyboardUtils.hideSoftKeyboard(this);
            viewModel.addStudent(txtName.getText().toString());
            updateListAdapterData();
            lstStudents.smoothScrollToPosition(listAdapter.getCount() - 1);
            resetForm();
        }
    }

    private void resetForm() {
        txtName.setText("");
    }

    private void deleteStudent(String student) {
        KeyboardUtils.hideSoftKeyboard(this);
        if (student != null) {
            viewModel.deleteStudent(student);
            updateListAdapterData();
        }
    }

    private void updateListAdapterData() {
        listAdapter.clear();
        listAdapter.addAll(viewModel.getStudents(true));
    }

}
