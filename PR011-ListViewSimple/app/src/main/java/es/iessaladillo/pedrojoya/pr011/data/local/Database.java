package es.iessaladillo.pedrojoya.pr011.data.local;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private static volatile Database instance;

    private final ArrayList<String> students;

    private Database() {
        students = new ArrayList<>();
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public List<String> getStudents() {
        return students;
    }

    public void addStudent(String student) {
        students.add(student);
    }

    public void deleteStudent(String student) {
        students.remove(student);
    }

}
