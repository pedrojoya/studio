package es.iessaladillo.pedrojoya.pr249.data.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Database {

    private static Database instance;

    private final ArrayList<String> students = new ArrayList<>();
    private final MutableLiveData<List<String>> studentsLiveData = new MutableLiveData<>();

    private Database() {
        insertInitialData();
    }

    private void insertInitialData() {
        students.addAll(
                Arrays.asList("Baldomero", "Sergio", "Pablo", "Rodolfo", "Atanasio", "Gervasio",
                        "Prudencia", "Oswaldo", "Gumersindo", "Gerardo", "Rodrigo", "Óscar",
                    "Filomeno", "Fulgencio", "Ambrosio"));
        studentsLiveData.postValue(new ArrayList<>(students));
    }

    public LiveData<List<String>> queryStudents() {
        return studentsLiveData;
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
