package es.iessaladillo.pedrojoya.pr016.data.local;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Level;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr016.utils.CollectionUtils;

public class Database {

    private static volatile Database instance;

    @NonNull
    private final ArrayList<Level> levels = new ArrayList<>();
    @NonNull
    private final ArrayList<Student> students = new ArrayList<>();
    private long levelsAutoId = 0;
    private long studentsAutoId = 0;

    private Database() {
        insertInitialData();
    }

    private void insertInitialData() {
        insertLevel(new Level("CFGM Sistemas Microinformáticos y Redes"));
        insertLevel(new Level("CFGS Desarrollo de Aplicaciones Multiplataforma"));
        insertStudent(new Student("Baldomero", 16, 1, "2º"));
        insertStudent(new Student("Sergio", 27, 1, "1º"));
        insertStudent(new Student("Atanasio", 17, 1, "1º"));
        insertStudent(new Student("Oswaldo", 26, 1, "1º"));
        insertStudent(new Student("Rodrigo", 22, 1, "2º"));
        insertStudent(new Student("Antonio", 16, 1, "1º"));
        insertStudent(new Student("Pedro", 22, 2, "2º"));
        insertStudent(new Student("Pablo", 22, 2, "2º"));
        insertStudent(new Student("Rodolfo", 21, 2, "1º"));
        insertStudent(new Student("Gervasio", 24, 2, "2º"));
        insertStudent(new Student("Prudencia", 20, 2, "2º"));
        insertStudent(new Student("Gumersindo", 17, 2, "2º"));
        insertStudent(new Student("Gerardo", 18, 2, "1º"));
        insertStudent(new Student("Óscar", 21, 2, "2º"));
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
    public List<Level> queryLevels() {
        return new ArrayList<>(levels);
    }

    @NonNull
    public List<Student> queryStudentsByLevel(long levelId) {
        return (List<Student>) CollectionUtils.filter(students,
                student -> student.getLevel() == levelId);
    }

    @SuppressWarnings("WeakerAccess")
    public void insertLevel(@NonNull Level level) {
        level.setId(++levelsAutoId);
        levels.add(level);
    }

    private void insertStudent(@NonNull Student student) {
        student.setId(++studentsAutoId);
        students.add(student);
    }

}
