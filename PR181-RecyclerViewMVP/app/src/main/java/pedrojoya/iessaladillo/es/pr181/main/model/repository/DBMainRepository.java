package pedrojoya.iessaladillo.es.pr181.main.model.repository;

import android.os.Handler;

import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;

public class DBMainRepository implements MainRepository {

    @Override
    public void getList(final MainRepositoryCallback listener) {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                listener.onListReceived(DB.getStudents());
            }
        }, 5000);
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
