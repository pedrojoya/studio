package es.iessaladillo.pedrojoya.pr040.ui.main;

import android.annotation.SuppressLint;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr040.base.Resource;
import es.iessaladillo.pedrojoya.pr040.data.Repository;
import es.iessaladillo.pedrojoya.pr040.data.remote.model.Student;

@SuppressLint("StaticFieldLeak")
class MainFragmentViewModel extends ViewModel {

    private final LiveData<Resource<List<Student>>> students;
    private final MutableLiveData<Boolean> queryStudentsTrigger = new MutableLiveData<>();

    MainFragmentViewModel(Repository repository) {
        students = Transformations.switchMap(queryStudentsTrigger,
            query -> repository.queryStudents());
        refreshStudents();
    }

    LiveData<Resource<List<Student>>> getStudents() {
        return students;
    }

    void refreshStudents() {
        queryStudentsTrigger.postValue(true);
    }

}
