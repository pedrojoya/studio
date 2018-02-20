package es.iessaladillo.pedrojoya.pr220.ui.student;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.util.UUID;

import es.iessaladillo.pedrojoya.pr220.R;
import es.iessaladillo.pedrojoya.pr220.data.Repository;
import es.iessaladillo.pedrojoya.pr220.data.model.Student;
import es.iessaladillo.pedrojoya.pr220.utils.ViewMessage;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public class StudentActivityViewModel extends ViewModel {

    private final Repository repository;
    private final String studentId;

    private final LiveData<Student> student;
    private final MutableLiveData<ViewMessage> message = new MutableLiveData<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public StudentActivityViewModel(Repository repository, String studentId) {
        this.repository = repository;
        this.studentId = studentId;
        if (TextUtils.isEmpty(studentId)) {
            MutableLiveData<Student> newStudent = new MutableLiveData<>();
            newStudent.setValue(new Student());
            student = newStudent;
        } else {
            student = repository.getStudent(studentId);
        }
    }

    public LiveData<Student> getStudent() {
        return student;
    }

    public LiveData<ViewMessage> getMessage() {
        return message;
    }

    public void onFabClick() {
        Student s = student.getValue();
        if (s != null && s.isValid()) {
            if (isInEditMode()) {
                updateStudent(s);
            } else {
                s.setId(UUID.randomUUID().toString());
                addStudent(s);
            }
        }
    }

    public boolean isInEditMode() {
        return !TextUtils.isEmpty(studentId);
    }

    private void addStudent(Student student) {
        compositeDisposable.add(repository.addStudent(student)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(studentResponse -> showSuccessAddingStudent(),
                        throwable -> showErrorAddingStudent()));
    }

    private void showSuccessAddingStudent() {
        message.setValue(new ViewMessage(R.string.student_fragment_student_added, true));
    }

    private void showErrorAddingStudent() {
        message.setValue(new ViewMessage(R.string.student_fragment_error_adding_student, false));
    }

    @SuppressWarnings("unused")
    public boolean txtAddressOnEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onFabClick();
            return true;
        }
        return false;
    }

    private void updateStudent(Student student) {
        repository.updateStudent(student).observeOn(AndroidSchedulers.mainThread()).subscribe(
                list -> showSucessUpdatingStudent(), this::showErrorUpdatingStudent);
    }

    private void showSucessUpdatingStudent() {
        message.setValue(new ViewMessage(R.string.student_fragment_student_updated, true));
    }

    private void showErrorUpdatingStudent(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            if (exception.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                message.setValue(new ViewMessage(R.string.student_fragment_error_no_student, true));
            }
        } else {
            message.setValue(new ViewMessage(R.string.student_fragment_error_no_student, true));
        }
        message.setValue(new ViewMessage(R.string.student_fragment_error_updating_student, true));
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
