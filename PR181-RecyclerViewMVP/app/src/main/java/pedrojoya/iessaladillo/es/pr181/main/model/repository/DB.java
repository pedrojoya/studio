package pedrojoya.iessaladillo.es.pr181.main.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;

public class DB {

    private static final List<Student> mData;
    private static int next = 1;
    private static final Random random = new Random();

    static {
        mData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mData.add(getNextStudent());
        }
    }

    public static List<Student> getStudents() {
        return mData;
    }

    public static void addStudent(Student student) {
        mData.add(student);
    }

    public static void removeStudent(int position) {
        mData.remove(position);
    }

    public static int getStudentsCount() {
        return mData.size();
    }

    private static int getNext() {
        return next++;
    }

    public static Student getNextStudent() {
        int num = next++;
        return new Student(
                "Alumno " + num,
                "c/ Su casa, nº " + num,
                "http://lorempixel.com/100/100/abstract/" + (num%10 + 1) + "/"
        );
    }

}
