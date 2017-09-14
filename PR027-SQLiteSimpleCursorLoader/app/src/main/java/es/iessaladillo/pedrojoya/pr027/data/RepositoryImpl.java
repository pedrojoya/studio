package es.iessaladillo.pedrojoya.pr027.data;

import android.content.Context;
import android.database.Cursor;

import es.iessaladillo.pedrojoya.pr027.data.local.DbHelper;
import es.iessaladillo.pedrojoya.pr027.data.local.StudentDao;
import es.iessaladillo.pedrojoya.pr027.data.model.Student;

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
    public Cursor queryStudents() {
        return studentDao.queryStudents();
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
