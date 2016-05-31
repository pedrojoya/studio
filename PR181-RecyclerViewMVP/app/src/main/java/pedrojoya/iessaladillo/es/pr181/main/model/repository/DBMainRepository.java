package pedrojoya.iessaladillo.es.pr181.main.model.repository;

import java.util.List;

import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;

public class DBMainRepository implements MainRepository {

    @Override
    public void getList(MainRepositoryCallback listener) {
        List<Student> list = DB.getStudents();
        listener.onListReceived(list);
    }

    @Override
    public void addElement(MainRepositoryCallback listener) {
        Student student = DB.getNextStudent();
        DB.addStudent(student);
        listener.onElementAdded(student);
    }

    @Override
    public void removeElement(int position, Student student, MainRepositoryCallback listener) {
        DB.removeStudent(position);
        listener.onElementRemoved(position, student);
    }

}
