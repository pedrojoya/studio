package es.iessaladillo.pedrojoya.pr014.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.iessaladillo.pedrojoya.pr014.data.model.Student;

public class Database {

    private static Database instance;
    private final ArrayList<Student> students;

    private Database() {
        students = new ArrayList<>();
        insertInitialData();
    }

    public synchronized static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void insertInitialData() {
        students.addAll(new ArrayList<>(Arrays.asList(new Student("Pedro", 22, "CFGS", "2"),
                new Student("Baldomero", 16, "CFGM", "2º"), new Student("Sergio", 27, "CFGM", "1º"),
                new Student("Pablo", 22, "CFGS", "2º"), new Student("Rodolfo", 21, "CFGS", "1º"),
                new Student("Atanasio", 17, "CFGM", "1º"),
                new Student("Gervasio", 24, "CFGS", "2º"),
                new Student("Prudencia", 20, "CFGS", "2º"),
                new Student("Oswaldo", 26, "CFGM", "1º"),
                new Student("Gumersindo", 17, "CFGS", "2º"),
                new Student("Gerardo", 18, "CFGS", "1º"), new Student("Rodrigo", 22, "CFGM", "2º"),
                new Student("Óscar", 21, "CFGS", "2º"))));
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void deleteStudent(int position) {
        students.remove(position);
    }

}
