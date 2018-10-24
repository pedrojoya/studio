package es.iessaladillo.pedrojoya.pr014.data.local;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr014.data.local.model.Student;

public class Database {

    private static volatile Database instance;

    private final ArrayList<Student> students = new ArrayList<>();
    private long studentsAutoId = 0;

    private Database() {
        insertInitialData();
    }

    private void insertInitialData() {
        insertStudent(new Student("Pedro", 22, "CFGS", "2"));
        insertStudent(new Student("Baldomero", 16, "CFGM", "2º"));
        insertStudent(new Student("Sergio", 27, "CFGM", "1º"));
        insertStudent(new Student("Pablo", 22, "CFGS", "2º"));
        insertStudent(new Student("Rodolfo", 21, "CFGS", "1º"));
        insertStudent(new Student("Atanasio", 17, "CFGM", "1º"));
        insertStudent(new Student("Gervasio", 24, "CFGS", "2º"));
        insertStudent(new Student("Prudencia", 20, "CFGS", "2º"));
        insertStudent(new Student("Oswaldo", 26, "CFGM", "1º"));
        insertStudent(new Student("Gumersindo", 17, "CFGS", "2º"));
        insertStudent(new Student("Gerardo", 18, "CFGS", "1º"));
        insertStudent(new Student("Rodrigo", 22, "CFGM", "2º"));
        insertStudent(new Student("Óscar", 21, "CFGS", "2º"));
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

    @NonNull
    public List<Student> queryStudents() {
        return new ArrayList<>(students);
    }

    public synchronized void insertStudent(@NonNull Student student) {
        student.setId(++studentsAutoId);
        students.add(student);
    }

    public void deleteStudent(@NonNull Student student) {
        students.remove(student);
    }

}
