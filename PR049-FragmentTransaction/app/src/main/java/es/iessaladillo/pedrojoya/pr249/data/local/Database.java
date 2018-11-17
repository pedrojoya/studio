package es.iessaladillo.pedrojoya.pr249.data.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {

    private static Database instance;

    private final ArrayList<String> students = new ArrayList<>();

    private Database() {
        insertInitialData();
    }

    private void insertInitialData() {
        students.addAll(
                Arrays.asList("Baldomero", "Sergio", "Pablo", "Rodolfo", "Atanasio", "Gervasio",
                        "Prudencia", "Oswaldo", "Gumersindo", "Gerardo", "Rodrigo", "Óscar"));
    }

    public List<String> queryStudents() {
        return new ArrayList<>(students);
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

}
