package es.iessaladillo.pedrojoya.pr011.data;

import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr011.data.local.StudentDao;

public class RepositoryImpl implements Repository {

    private static volatile RepositoryImpl instance;

    @NonNull
    private final StudentDao studentDao;

    private RepositoryImpl(@NonNull StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public static RepositoryImpl getInstance(StudentDao studentDao) {
        if (instance == null) {
            synchronized (RepositoryImpl.class) {
                if (instance == null) {
                    instance = new RepositoryImpl(studentDao);
                }
            }
        }
        return instance;
    }

    @Override
    @NonNull
    public List<String> queryStudents() {
        return studentDao.queryStudents();
    }

    @Override
    public int addStudent(@NonNull String student) {
        return studentDao.addStudent(student);
    }

    @Override
    public int deleteStudent(@NonNull String student) {
        return studentDao.deleteStudent(student);
    }

}
