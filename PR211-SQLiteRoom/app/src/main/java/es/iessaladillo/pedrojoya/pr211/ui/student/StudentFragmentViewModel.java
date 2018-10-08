package es.iessaladillo.pedrojoya.pr211.ui.student;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr211.R;
import es.iessaladillo.pedrojoya.pr211.data.Repository;
import es.iessaladillo.pedrojoya.pr211.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr211.ui.main.MainActivityViewModel;

class StudentFragmentViewModel extends ViewModel {

    private final Repository repository;
    private final MainActivityViewModel activityViewModel;
    private LiveData<Student> studentLiveData;

    StudentFragmentViewModel(Repository repository, MainActivityViewModel activityViewModel) {
        this.repository = repository;
        this.activityViewModel = activityViewModel;
    }

    LiveData<Student> getStudent(long studentId) {
        if (studentLiveData == null) {
            studentLiveData = repository.queryStudent(studentId);
        }
        return studentLiveData;
    }

    public void addStudent(Student student) {
        (new AddStudentTask()).execute(student);
    }

    public void updateStudent(Student student) {
        (new EditStudentTask()).execute(student);
    }

    @SuppressLint("StaticFieldLeak")
    private class AddStudentTask extends AsyncTask<Student, Void, Void> {

        @Override
        protected Void doInBackground(Student... students) {
            long inserted = repository.insertStudent(students[0]);
            activityViewModel.setInfoMessage(inserted >= 0 ? R.string.student_fragment_student_added : R.string.student_fragment_error_adding_student);
            return null;
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class EditStudentTask extends AsyncTask<Student, Void, Void> {

        @Override
        protected Void doInBackground(Student... students) {
            long updated = repository.updateStudent(students[0]);
            activityViewModel.setInfoMessage(updated > 0 ? R.string
                    .student_fragment_student_updated : R.string
                    .student_fragment_error_updating_student);
            return null;
        }

    }



}
