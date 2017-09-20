package es.iessaladillo.pedrojoya.pr196.data;

import android.content.Context;

import java.util.List;

import es.iessaladillo.pedrojoya.pr196.data.local.DbHelper;
import es.iessaladillo.pedrojoya.pr196.data.local.StudentDao;
import es.iessaladillo.pedrojoya.pr196.data.model.Student;
import io.reactivex.Single;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final StudentDao studentDao;

    private RepositoryImpl(Context context) {
        this.studentDao = StudentDao.getInstance(context, DbHelper.getInstance(context));
    }

    public static synchronized RepositoryImpl getInstance(Context context) {
        if (instance == null) {
            instance = new RepositoryImpl(context);
        }
        return instance;
    }

    @Override
    public Single<List<Student>> getStudents() {
        return studentDao.getStudentsRx();
    }

    @Override
    public Single<Student> getStudent(long studentId) {
        return studentDao.getStudentRx(studentId);
    }

    @Override
    public Single<Long> addStudent(Student student) {
        return studentDao.createStudentRx(student);
    }

    @Override
    public Single<Integer> updateStudent(Student student) {
        return studentDao.updateAlumnoRx(student);
    }

    @Override
    public Single<Integer> deleteStudent(Student student) {
        return studentDao.deleteStudentRx(student);
    }

}
