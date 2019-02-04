package pedrojoya.iessaladillo.es.pr256.data;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import pedrojoya.iessaladillo.es.pr256.data.local.StudentDao;
import pedrojoya.iessaladillo.es.pr256.data.local.model.Student;

public class RepositoryImpl implements Repository {

    private static final int STUDENTS_PAGE_SIZE = 15;
    private static RepositoryImpl instance;

    private final StudentDao studentDao;

    private RepositoryImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public synchronized static RepositoryImpl getInstance(StudentDao studentDao) {
        if (instance == null) {
            instance = new RepositoryImpl(studentDao);
        }
        return instance;
    }

    @Override
    public LiveData<PagedList<Student>> queryAllStudents() {
        return new LivePagedListBuilder<>(studentDao.queryAllStudents(),
            STUDENTS_PAGE_SIZE).build();
    }

    @Override
    public void insertStudent(Student student) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> studentDao.insert(student));
    }

    @Override
    public void deleteStudent(Student student) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> studentDao.delete(student));
    }

}
