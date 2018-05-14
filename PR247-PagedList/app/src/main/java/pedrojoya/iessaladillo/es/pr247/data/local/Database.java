package pedrojoya.iessaladillo.es.pr247.data.local;

import android.arch.core.util.Function;
import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mooveit.library.Fakeit;

import java.util.ArrayList;
import java.util.List;

import pedrojoya.iessaladillo.es.pr247.data.model.Student;

public class Database {

    private static Database instance;

    private PagedList<Student> students;
    private int mCount;

    private Database() {
        PagedList.Builder<Integer, Student> builder = new PagedList.Builder<Integer, Student>();
        insertInitialData();
    }

    public synchronized static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void insertInitialData() {
        for (int i = 0; i < 5; i++) {
            addFakeStudent();
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addFakeStudent() {
        Student student = new Student(Fakeit.name().name(), Fakeit.address().streetAddress(),
                "http://lorempixel.com/100/100/abstract/" + (++mCount % 10 + 1) + "/");
        students.add(student);
    }

    public void deleteStudent(int position) {
        students.remove(position);
    }

}
