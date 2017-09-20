package es.iessaladillo.pedrojoya.pr203.data;

import es.iessaladillo.pedrojoya.pr203.data.model.Student;
import es.iessaladillo.pedrojoya.pr203.data.model.Student_;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;
    private BoxStore boxStore;
    private Box<Student> studentBox;

    public static RepositoryImpl getInstance(BoxStore boxStore) {
        if (instance == null) {
            instance = new RepositoryImpl(boxStore);
        }
        return instance;
    }

    private RepositoryImpl(BoxStore boxStore) {
        this.boxStore = boxStore;
        studentBox = boxStore.boxFor(Student.class);
    }

    @Override
    public Query<Student> queryStudents() {
        return studentBox.query().order(Student_.name).build();
    }

    @Override
    public void deleteStudent(Student student) {
        studentBox.remove(student);
    }

    @Override
    public Student queryStudent(long studentId) {
        return studentBox.get(studentId);
    }

    @Override
    public void saveStudent(Student student) {
        studentBox.put(student);
    }

}
