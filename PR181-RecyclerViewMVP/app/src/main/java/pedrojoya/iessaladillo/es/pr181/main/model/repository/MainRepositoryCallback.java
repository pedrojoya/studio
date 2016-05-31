package pedrojoya.iessaladillo.es.pr181.main.model.repository;

import java.util.List;

import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;

public interface MainRepositoryCallback {

    void onListReceived(List<Student> list);

    void onElementAdded(Student student);

    void onElementRemoved(int position, Student student);
}
