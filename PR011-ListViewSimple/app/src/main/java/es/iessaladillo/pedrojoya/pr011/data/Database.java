package es.iessaladillo.pedrojoya.pr011.data;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Database instance;
    private final ArrayList<String> students;

    private Database() {
        students = new ArrayList<>();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public List<String> getStudents() {
        return students;
    }

    public void addStudent(String student) {
        students.add(student);
    }

    public void deleteStudent(int position) {
        students.remove(position);
    }

}
