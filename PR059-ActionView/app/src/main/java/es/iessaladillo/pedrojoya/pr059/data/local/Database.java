package es.iessaladillo.pedrojoya.pr059.data.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    public LiveData<List<String>> queryStudents(String criteria) {
        List<String> result = new ArrayList<>();
        for (String student: students) {
            if (student.toUpperCase().contains(criteria.toUpperCase())) {
                result.add(student);
            }
        }
        MutableLiveData<List<String>> resultLiveData = new MutableLiveData<>();
        resultLiveData.postValue(result);
        return resultLiveData;
    }

}
