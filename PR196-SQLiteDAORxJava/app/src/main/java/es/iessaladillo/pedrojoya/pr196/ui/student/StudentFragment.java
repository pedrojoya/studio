package es.iessaladillo.pedrojoya.pr196.ui.student;

import android.app.Activity;
import android.content.Intent;
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

import es.iessaladillo.pedrojoya.pr196.Constants;
import es.iessaladillo.pedrojoya.pr196.R;
import es.iessaladillo.pedrojoya.pr196.data.Repository;
import es.iessaladillo.pedrojoya.pr196.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr196.data.model.Student;
import es.iessaladillo.pedrojoya.pr196.utils.ClickToSelectEditText;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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
    private CompositeDisposable compositeDisposable;

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
        repository = RepositoryImpl.getInstance(requireActivity());
        compositeDisposable = new CompositeDisposable();
        initViews(getView());
    }

    private void initViews(View view) {
        FloatingActionButton fab = requireActivity().findViewById(R.id.fab);
        tilName = ViewCompat.requireViewById(view, R.id.tilName);
        tilGrade = ViewCompat.requireViewById(view, R.id.tilGrade);
        tilPhone = ViewCompat.requireViewById(view, R.id.tilPhone);
        TextInputLayout tilAddress = ViewCompat.requireViewById(view, R.id.tilAddress);
        txtName = ViewCompat.requireViewById(view, R.id.txtName);
        spnGrade = ViewCompat.requireViewById(view, R.id.txtGrade);
        txtPhone = ViewCompat.requireViewById(view, R.id.txtPhone);
        txtAddress = ViewCompat.requireViewById(view, R.id.txtAddress);

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
        ArrayAdapter<CharSequence> gradesAdapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.grades, android.R.layout.simple_list_item_1);
        spnGrade.setAdapter(gradesAdapter);
        spnGrade.setOnItemSelectedListener((item, selectedIndex) -> spnGrade.setText(item));
    }

    private void updateTitle() {
        requireActivity().setTitle(
                editMode ? R.string.student_fragment_edit_student : R.string
                        .student_fragment_add_student);
    }

    private void loadStudent(long studentId) {
        compositeDisposable.add(repository.getStudent(studentId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(student -> {
                    if (student != null) {
                        showStudent(student);
                    } else {
                        showErrorLoadingStudentAndFinish();
                    }
                }));
    }

    private void showErrorLoadingStudentAndFinish() {
        Toast.makeText(requireActivity(), R.string.student_fragment_error_loading_student,
                Toast.LENGTH_LONG).show();
        requireActivity().finish();
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
        compositeDisposable.add(repository.addStudent(student)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(studentId -> {
                    if (studentId >= 0) {
                        showSuccessAddingStudent();
                    } else {
                        showErrorAddingStudent();
                    }
                    sendResultOkAndFinish();
                }));
    }

    private void showSuccessAddingStudent() {
        Toast.makeText(requireActivity(), getString(R.string.student_fragment_student_added),
                Toast.LENGTH_SHORT).show();
    }

    private void showErrorAddingStudent() {
        Toast.makeText(requireActivity(), getString(R.string.student_fragment_error_adding_student),
                Toast.LENGTH_SHORT).show();
    }

    private void updateStudent(Student student) {
        compositeDisposable.add(repository.updateStudent(student)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updated -> {
                    if (updated == 1) {
                        showSucessUpdatingStudent();
                    } else {
                        showErrorUpdatingStudent();
                    }
                    sendResultOkAndFinish();
                }));
    }

    private void showSucessUpdatingStudent() {
        Toast.makeText(requireActivity(), getString(R.string.student_fragment_student_updated),
                Toast.LENGTH_SHORT).show();

    }

    private void showErrorUpdatingStudent() {
        Toast.makeText(requireActivity(), getString(R.string.student_fragment_error_updating_student),
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

    private void sendResultOkAndFinish() {
        requireActivity().setResult(Activity.RESULT_OK, new Intent());
        requireActivity().finish();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}
