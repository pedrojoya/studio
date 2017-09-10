package es.iessaladillo.pedrojoya.pr027.ui.student;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr027.Constants;
import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.data.Repository;
import es.iessaladillo.pedrojoya.pr027.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr027.data.model.Student;
import es.iessaladillo.pedrojoya.pr027.utils.ClickToSelectEditText;

@SuppressWarnings({"unchecked", "unused"})
public class StudentFragment extends Fragment {

    private EditText txtName;
    private ClickToSelectEditText<String> spnGrade;
    private EditText txtPhone;
    private EditText txtAddress;
    private TextInputLayout tilName;
    private TextInputLayout tilGrade;
    private TextInputLayout tilPhone;

    private boolean editMode;
    private long studentId;
    private Repository repository;

    public static StudentFragment newInstance() {
        return new StudentFragment();
    }

    public static StudentFragment newInstance(long studentId) {
        StudentFragment studentFragment = new StudentFragment();
        Bundle arguments = new Bundle();
        arguments.putLong(Constants.EXTRA_STUDENT_ID, studentId);
        studentFragment.setArguments(arguments);
        return studentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        obtainArguments(getArguments());
        super.onCreate(savedInstanceState);
    }

    private void obtainArguments(Bundle arguments) {
        if (arguments != null && arguments.containsKey(Constants.EXTRA_STUDENT_ID)) {
            editMode = true;
            studentId = arguments.getLong(Constants.EXTRA_STUDENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        repository = RepositoryImpl.getInstance(getActivity());
        initViews(getView());
    }

    private void initViews(View view) {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        tilName = view.findViewById(R.id.tilName);
        tilGrade = view.findViewById(R.id.tilGrade);
        tilPhone = view.findViewById(R.id.tilPhone);
        TextInputLayout tilAddress = view.findViewById(R.id.tilAddress);
        txtName = view.findViewById(R.id.txtName);
        spnGrade = view.findViewById(R.id.txtGrade);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtAddress = view.findViewById(R.id.txtAddress);

        fab.setOnClickListener(v -> saveStudent());
        txtAddress.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveStudent();
                return true;
            }
            return false;
        });
        loadGrades();
        updateTitle();
        if (editMode) {
            loadStudent(studentId);
        }
    }

    private void loadGrades() {
        ArrayAdapter<CharSequence> gradesAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.grades, android.R.layout.simple_list_item_1);
        spnGrade.setAdapter(gradesAdapter);
        spnGrade.setOnItemSelectedListener((item, selectedIndex) -> spnGrade.setText(item));
    }

    private void updateTitle() {
        getActivity().setTitle(editMode ? R.string.student_fragment_edit_student : R.string.student_fragment_add_student);
    }

    private void loadStudent(long studentId) {
        (new LoadStudentTask()).execute(studentId);
    }

    private void showErrorLoadingStudentAndFinish() {
        Toast.makeText(getActivity(), R.string.student_fragment_error_loading_student, Toast.LENGTH_LONG).show();
        getActivity().finish();
    }

    public void saveStudent() {
        if (isValidForm()) {
            Student student = createStudent();
            if (editMode) {
                student.setId(studentId);
                updateStudent(student);
            } else {
                addStudent(student);
            }
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
            til.setError(getString(R.string.student_fragment_required_field));
            return false;
        } else {
            til.setErrorEnabled(false);
            til.setError("");
            return true;
        }
    }

    private void addStudent(Student student) {
        (new AddStudentTask()).execute(student);
    }

    private void showSuccessAddingStudent() {
        Toast.makeText(getActivity(), getString(R.string.student_fragment_student_added), Toast.LENGTH_SHORT)
                .show();
    }

    private void showErrorAddingStudent() {
        Toast.makeText(getActivity(), getString(R.string.student_fragment_error_adding_student), Toast.LENGTH_SHORT)
                .show();
    }

    private void updateStudent(Student student) {
        (new EditStudentTask()).execute(student);
    }

    private void showSucessUpdatingStudent() {
        Toast.makeText(getActivity(), getString(R.string.student_fragment_student_updated),
                Toast.LENGTH_SHORT).show();

    }

    private void showErrorUpdatingStudent() {
        Toast.makeText(getActivity(), getString(R.string.student_fragment_error_updating_student),
                Toast.LENGTH_SHORT).show();
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
        student.setGrade(spnGrade.getText().toString());
        return student;
    }

    private class LoadStudentTask extends AsyncTask<Long, Void, Student> {

        @Override
        protected Student doInBackground(Long... studentIds) {
            return repository.getStudent(studentIds[0]);
        }

        @Override
        protected void onPostExecute(Student student) {
            if (student != null) {
                showStudent(student);
            } else {
                showErrorLoadingStudentAndFinish();
            }
        }
    }

    private class AddStudentTask extends AsyncTask<Student, Void, Long> {

        @Override
        protected Long doInBackground(Student... students) {
            return repository.addStudent(students[0]);
        }

        @Override
        protected void onPostExecute(Long studentId) {
            if (studentId >= 0) {
                showSuccessAddingStudent();
            } else {
                showErrorAddingStudent();
            }
            getActivity().finish();
        }

    }

    private class EditStudentTask extends AsyncTask<Student, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Student... students) {
            return repository.updateStudent(students[0]);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                showSucessUpdatingStudent();
            } else {
                showErrorUpdatingStudent();
            }
            getActivity().finish();
        }

    }

}
