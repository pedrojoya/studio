package pedrojoya.iessaladillo.es.pr181.main.model.repository;

import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;

public interface MainRepository {

    void getList(MainRepositoryCallback listener);

    void addElement(MainRepositoryCallback listener);

    void removeElement(int position, Student student, MainRepositoryCallback listener);

}
