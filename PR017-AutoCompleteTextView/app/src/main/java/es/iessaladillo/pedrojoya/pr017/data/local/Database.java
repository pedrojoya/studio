package es.iessaladillo.pedrojoya.pr017.data.local;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr017.R;
import es.iessaladillo.pedrojoya.pr017.data.local.model.Word;

public class Database {

    private static volatile Database instance;

    @NonNull
    private final ArrayList<Word> words = new ArrayList<>();
    private long wordIdAuto;

    private Database() {
        insertInitialData();
    }

    private void insertInitialData() {
        insertWord(new Word(R.drawable.animal, "Animal", "Animal"));
        insertWord(new Word(R.drawable.bridge, "Bridge", "Puente"));
        insertWord(new Word(R.drawable.flag, "Flag", "Bandera"));
        insertWord(new Word(R.drawable.food, "Food", "Comida"));
        insertWord(new Word(R.drawable.fruit, "Fruit", "Fruta"));
        insertWord(new Word(R.drawable.glass, "Glass", "Vaso"));
        insertWord(new Word(R.drawable.plant, "Plant", "Planta"));
        insertWord(new Word(R.drawable.science, "Science", "Ciencia"));
        insertWord(new Word(R.drawable.sea, "Sea", "Mar"));
        insertWord(new Word(R.drawable.plant, "Space", "Espacio"));
        insertWord(new Word(R.drawable.science, "Art", "Arte"));
        insertWord(new Word(R.drawable.sea, "Furniture", "Mobiliario"));

    }

    @NonNull
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
    public List<Word> queryWords() {
        return new ArrayList<>(words);
    }

    public synchronized void insertWord(@NonNull Word word) {
        word.setId(++wordIdAuto);
        words.add(word);
    }

    public void deleteWord(@NonNull Word word) {
        words.remove(word);
    }

}
