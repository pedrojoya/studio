package pedrojoya.iessaladillo.es.pr181.main.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;

@SuppressWarnings("unused")
public class DB {

    private static DB db;

    private final List<Student> mData;
    private int next = 1;
    private final Random random = new Random();

    private DB() {
        mData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mData.add(getNextStudent());
        }
    }

    public static synchronized DB getInstance() {
        if (db == null) {
            db = new DB();
        }
        return db;
    }

    public List<Student> getStudents() {
        return mData;
    }

    public void addStudent(Student student) {
        mData.add(student);
    }

    public void removeStudent(int position) {
        mData.remove(position);
    }

    public int getStudentsCount() {
        return mData.size();
    }

    private int getNext() {
        return next++;
    }

    public Student getNextStudent() {
        int num = next++;
        return new Student("Alumno " + num, "c/ Su casa, nº " + num,
                "http://lorempixel.com/100/100/abstract/" + (num % 10 + 1) + "/");
    }

}
