package es.iessaladillo.pedrojoya.pr016.data.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.iessaladillo.pedrojoya.pr016.data.local.model.Level;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr016.utils.CollectionUtils;

public class Database {

    private static volatile Database instance;

    private final ArrayList<Level> levels;
    private final ArrayList<Student> students;

    private Database() {
        long levelAutonumeric = 0;
        long studentAutonumeric = 0;
        levels = new ArrayList<>(Arrays.asList(
                new Level(++levelAutonumeric, "CFGM Sistemas Microinformáticos y Redes"),
                new Level(++levelAutonumeric, "CFGS Desarrollo de Aplicaciones Multiplataforma")
                ));
        students = new ArrayList<>(Arrays.asList(
                new Student(++studentAutonumeric, "Baldomero", 16, 1, "2º"),
                new Student(++studentAutonumeric, "Sergio", 27, 1, "1º"),
                new Student(++studentAutonumeric, "Atanasio", 17, 1, "1º"),
                new Student(++studentAutonumeric, "Oswaldo", 26, 1, "1º"),
                new Student(++studentAutonumeric, "Rodrigo", 22, 1, "2º"),
                new Student(++studentAutonumeric, "Antonio", 16, 1, "1º"),
                new Student(++studentAutonumeric, "Pedro", 22, 2, "2º"),
                new Student(++studentAutonumeric,"Pablo", 22, 2, "2º"),
                new Student(++studentAutonumeric,"Rodolfo", 21, 2, "1º"),
                new Student(++studentAutonumeric, "Gervasio", 24, 2, "2º"),
                new Student(++studentAutonumeric,"Prudencia", 20, 2, "2º"),
                new Student(++studentAutonumeric,"Gumersindo", 17, 2, "2º"),
                new Student(++studentAutonumeric,"Gerardo", 18, 2, "1º"),
                new Student(++studentAutonumeric,"Óscar", 21, 2, "2º")
        ));
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

    public List<Level> queryLevels() {
        return new ArrayList<>(levels);
    }

    public List<Student> queryStudentsByLevel(long levelId) {
        return (List<Student>) CollectionUtils.filter(students, student -> student.getLevel() == levelId);
    }

}
