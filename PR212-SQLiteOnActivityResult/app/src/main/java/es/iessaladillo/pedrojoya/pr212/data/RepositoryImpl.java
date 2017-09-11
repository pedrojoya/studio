package es.iessaladillo.pedrojoya.pr212.data;

import android.content.Context;

import java.util.List;

import es.iessaladillo.pedrojoya.pr212.data.local.DbHelper;
import es.iessaladillo.pedrojoya.pr212.data.local.StudentDao;
import es.iessaladillo.pedrojoya.pr212.data.model.Student;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private RepositoryImpl(Context context) {
        this.studentDao = StudentDao.getInstance(context, DbHelper.getInstance(context));
    }

    public static synchronized RepositoryImpl getInstance(Context context) {
        if (instance == null) {
            instance = new RepositoryImpl(context);
        }
        return instance;
    }

    private final StudentDao studentDao;

    @Override
    public List<Student> getStudents() {
        return studentDao.getStudents();
    }

    @Override
    public Student getStudent(long studentId) {
        return studentDao.getStudent(studentId);
    }

    @Override
    public long addStudent(Student student) {
        return studentDao.createStudent(student);
    }

    @Override
    public boolean updateStudent(Student student) {
        return studentDao.updateStudent(student);
    }

    @Override
    public boolean deleteStudent(long studentId) {
        return studentDao.deleteStudent(studentId);
    }

    @Override
    public void onDestroy() {
        studentDao.closeDatabase();
    }

}
