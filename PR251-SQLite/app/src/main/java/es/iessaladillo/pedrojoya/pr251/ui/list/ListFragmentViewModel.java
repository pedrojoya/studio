package es.iessaladillo.pedrojoya.pr251.ui.list;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr251.R;
import es.iessaladillo.pedrojoya.pr251.data.Repository;
import es.iessaladillo.pedrojoya.pr251.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr251.ui.main.MainActivityViewModel;

class ListFragmentViewModel extends ViewModel {

    private final Repository repository;
    private final MainActivityViewModel activityViewModel;
    private LiveData<List<Student>> students;

    ListFragmentViewModel(Repository repository, MainActivityViewModel activityViewModel) {
        this.repository = repository;
        this.activityViewModel = activityViewModel;
    }

    @SuppressWarnings("SameParameterValue")
    LiveData<List<Student>> getStudents(boolean forceLoad) {
        if (students == null || forceLoad) {
            students = repository.queryStudents();
        }
        return students;
    }

    public void deleteStudent(Student student) {
        (new DeleteStudentTask()).execute(student);
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteStudentTask extends AsyncTask<Student, Void, Void> {

        @Override
        protected Void doInBackground(Student... students) {
            long deleted = repository.deleteStudent(students[0]);
            activityViewModel.setInfoMessage(deleted > 0 ? R.string.main_fragment_student_deleted
                                                         : R.string
                    .main_fragment_error_deleting_student);
            return null;
        }

    }

}
