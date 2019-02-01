package es.iessaladillo.pedrojoya.pr251.data;

import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr251.base.Resource;
import es.iessaladillo.pedrojoya.pr251.data.local.StudentDao;
import es.iessaladillo.pedrojoya.pr251.data.local.model.Student;

public class RepositoryImpl implements Repository {

    private final StudentDao studentDao;

    public RepositoryImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public LiveData<List<Student>> queryStudents() {
        return studentDao.queryStudents();
    }

    @Override
    public LiveData<Student> queryStudent(long studentId) {
        return studentDao.queryStudent(studentId);
    }

    @Override
    public LiveData<Resource<Long>> insertStudent(Student student) {
        MutableLiveData<Resource<Long>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                long id = studentDao.insertStudent(student);
                result.postValue(Resource.success(id));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }

    @Override
    public LiveData<Resource<Integer>> updateStudent(Student student) {
        MutableLiveData<Resource<Integer>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                int updated = studentDao.updateStudent(student);
                result.postValue(Resource.success(updated));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }

    @Override
    public LiveData<Resource<Integer>> deleteStudent(Student student) {
        MutableLiveData<Resource<Integer>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                int deleted = studentDao.deleteStudent(student);
                result.postValue(Resource.success(deleted));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }

}
