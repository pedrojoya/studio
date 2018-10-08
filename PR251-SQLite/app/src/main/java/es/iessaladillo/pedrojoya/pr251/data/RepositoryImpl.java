package es.iessaladillo.pedrojoya.pr251.data;

import java.util.List;

import androidx.lifecycle.LiveData;
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
    public long insertStudent(Student student) {
        return studentDao.insertStudent(student);
    }

    @Override
    public long updateStudent(Student student) {
        return studentDao.updateStudent(student);
    }

    @Override
    public long deleteStudent(Student student) {
        return studentDao.deleteStudent(student);
    }

}
