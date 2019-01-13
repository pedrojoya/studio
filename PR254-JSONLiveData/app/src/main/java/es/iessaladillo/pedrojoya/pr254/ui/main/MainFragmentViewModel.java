package es.iessaladillo.pedrojoya.pr254.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr254.base.Resource;
import es.iessaladillo.pedrojoya.pr254.data.Repository;
import es.iessaladillo.pedrojoya.pr254.data.model.Student;

class MainFragmentViewModel extends ViewModel {

    private final MediatorLiveData<Resource<List<Student>>> students;
    private final MutableLiveData<Boolean> queryStudentsTrigger = new MutableLiveData<>();

    MainFragmentViewModel(Repository repository) {
        LiveData<Resource<List<Student>>> studentsResource = Transformations.switchMap(
            queryStudentsTrigger, query -> repository.queryStudents());
        students = new MediatorLiveData<>();
        students.addSource(studentsResource, currentValue -> {
            final Resource<List<Student>> previousValue = students.getValue();
            if (previousValue == null || currentValue == null) {
                students.setValue(currentValue);
            } else {
                if (currentValue.isLoading()) {
                    students.setValue(Resource.loading(previousValue.getData()));
                } else if (currentValue.hasError()) {
                    students.setValue(Resource.error(currentValue.getException(), previousValue.getData()));
                } else {
                    students.setValue(currentValue);
                }
            }
        });
        refreshStudents();
    }

    LiveData<Resource<List<Student>>> getStudents() {
        return students;
    }

    void refreshStudents() {
        queryStudentsTrigger.postValue(true);
    }

}
