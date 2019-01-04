package es.iessaladillo.pedrojoya.pr251.ui.student;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr251.R;
import es.iessaladillo.pedrojoya.pr251.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr251.data.local.DbHelper;
import es.iessaladillo.pedrojoya.pr251.data.local.StudentDao;
import es.iessaladillo.pedrojoya.pr251.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr251.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr251.views.SpinnerEditText;

@SuppressWarnings({"unchecked", "unused"})
public class StudentFragment extends Fragment {

    private static final String EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID";

    private EditText txtName;
    private SpinnerEditText<String> spnGrade;
    private EditText txtPhone;
    private EditText txtAddress;
    private TextInputLayout tilName;
    private TextInputLayout tilGrade;
    private TextInputLayout tilPhone;

    private boolean editMode;
    private long studentId;
    private StudentFragmentViewModel viewModel;

    public static StudentFragment newInstance() {
        return new StudentFragment();
    }

    public static StudentFragment newInstance(long studentId) {
        StudentFragment studentFragment = new StudentFragment();
        Bundle arguments = new Bundle();
        arguments.putLong(EXTRA_STUDENT_ID, studentId);
        studentFragment.setArguments(arguments);
        return studentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        obtainArguments(getArguments());
        super.onCreate(savedInstanceState);
    }

    private void obtainArguments(Bundle arguments) {
        if (arguments != null && arguments.containsKey(EXTRA_STUDENT_ID)) {
            editMode = true;
            studentId = arguments.getLong(EXTRA_STUDENT_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, new StudentFragmentViewModelFactory(
            new RepositoryImpl(StudentDao.getInstance(
                DbHelper.getInstance(requireContext().getApplicationContext()))))).get(
            StudentFragmentViewModel.class);
        setupViews(requireView());
        if (editMode) {
            viewModel.getStudent(studentId).observe(getViewLifecycleOwner(), this::showStudent);
        }
    }

    private void setupViews(View view) {
        tilName = ViewCompat.requireViewById(view, R.id.tilName);
        tilGrade = ViewCompat.requireViewById(view, R.id.tilGrade);
        tilPhone = ViewCompat.requireViewById(view, R.id.tilPhone);
        TextInputLayout tilAddress = ViewCompat.requireViewById(view, R.id.tilAddress);
        txtName = ViewCompat.requireViewById(view, R.id.txtName);
        spnGrade = ViewCompat.requireViewById(view, R.id.txtGrade);
        txtPhone = ViewCompat.requireViewById(view, R.id.txtPhone);
        txtAddress = ViewCompat.requireViewById(view, R.id.txtAddress);

        setupToolbar();
        setupFab();
        txtAddress.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveStudent();
                return true;
            }
            return false;
        });
    }

    private void setupFab() {
        FloatingActionButton fab = requireActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_save_white_24dp);
        fab.setOnClickListener(v -> saveStudent());
    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(
                editMode ? R.string.student_edit_student : R.string.student_add_student);
        }
    }

    private void saveStudent() {
        if (isValidForm()) {
            Student student = createStudent();
            if (editMode) {
                student.setId(studentId);
                viewModel.updateStudent(student);
            } else {
                viewModel.insertStudent(student);
            }
            KeyboardUtils.hideSoftKeyboard(requireActivity());
            requireActivity().onBackPressed();
        }
    }

    private boolean isValidForm() {
        boolean valido = true;
        if (!checkRequiredEditText(txtName, tilName)) {
            valido = false;
        }
        if (!checkRequiredEditText(spnGrade, tilGrade)) {
            valido = false;
        }
        if (!checkRequiredEditText(txtPhone, tilPhone)) {
            valido = false;
        }
        return valido;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkRequiredEditText(EditText txt, TextInputLayout til) {
        if (TextUtils.isEmpty(txt.getText().toString())) {
            til.setErrorEnabled(true);
            til.setError(getString(R.string.student_required_field));
            return false;
        } else {
            til.setErrorEnabled(false);
            til.setError("");
            return true;
        }
    }

    private void showStudent(Student student) {
        txtName.setText(student.getName());
        spnGrade.setText(student.getGrade());
        txtPhone.setText(student.getPhone());
        txtAddress.setText(student.getAddress());
    }

    private Student createStudent() {
        Student student = new Student();
        student.setName(txtName.getText().toString());
        student.setPhone(txtPhone.getText().toString());
        student.setAddress(txtAddress.getText().toString());
        //noinspection ConstantConditions
        student.setGrade(spnGrade.getText().toString());
        return student;
    }

}
