package es.iessaladillo.pedrojoya.pr012.data.local;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr012.R;
import es.iessaladillo.pedrojoya.pr012.data.local.model.Student;

public class Database {

    private static volatile Database instance;

    private final ArrayList<Student> students = new ArrayList<>();
    private long studentsAutoId = 0;

    private Database() {
        insertInitialData();
    }

    private void insertInitialData() {
        insertStudent(new Student(R.drawable.photo1,
                "Dolores Fuertes de Barriga", 22, "CFGS DAM", "2º", true));
        insertStudent(new Student(R.drawable.photo2, "Baldomero LLégate Ligero", 17,
                "CFGM SMR", "2º", true));
        insertStudent(new Student(R.drawable.photo3, "Jorge Javier Jiménez Jaén", 36,
                "CFGM SMR", "1º", false));
        insertStudent(new Student(R.drawable.photo4, "Fabián Gonzáles Palomino", 67,
                "CFGS DAM", "1º", false));
        insertStudent(new Student(R.drawable.photo, "Manuela González Cuevas", 17,
                "CFGM SMR", "1º", true));
        insertStudent(new Student(R.drawable.photo, "Rodolfo Valentino Martínez", 54,
                "CFGS DAM", "2º", false));
        insertStudent(new Student(R.drawable.photo, "Faemino López Acosta", 36,
                "CFGM SMR", "2º", true));
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

    public List<Student> queryStudents() {
        return new ArrayList<>(students);
    }

    @SuppressWarnings("WeakerAccess")
    public synchronized void insertStudent(Student student) {
        student.setId(++studentsAutoId);
        students.add(student);
    }

}
