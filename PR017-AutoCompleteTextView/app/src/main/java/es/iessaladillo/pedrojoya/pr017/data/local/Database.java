package es.iessaladillo.pedrojoya.pr017.data.local;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr017.R;
import es.iessaladillo.pedrojoya.pr017.data.local.model.Word;

public class Database {

    private static Database instance;

    private final ArrayList<Word> words = new ArrayList<>();
    private long wordIdAuto;

    private Database() {
        insertInitialData();
    }

    private void insertInitialData() {
        insertWord(new Word(++wordIdAuto, R.drawable.animal, "Animal", "Animal"));
        insertWord(new Word(++wordIdAuto, R.drawable.bridge, "Bridge", "Puente"));
        insertWord(new Word(++wordIdAuto, R.drawable.flag, "Flag", "Bandera"));
        insertWord(new Word(++wordIdAuto, R.drawable.food, "Food", "Comida"));
        insertWord(new Word(++wordIdAuto, R.drawable.fruit, "Fruit", "Fruta"));
        insertWord(new Word(++wordIdAuto, R.drawable.glass, "Glass", "Vaso"));
        insertWord(new Word(++wordIdAuto, R.drawable.plant, "Plant", "Planta"));
        insertWord(new Word(++wordIdAuto, R.drawable.science, "Science", "Ciencia"));
        insertWord(new Word(++wordIdAuto, R.drawable.sea, "Sea", "Mar"));
        insertWord(new Word(++wordIdAuto, R.drawable.plant, "Space", "Espacio"));
        insertWord(new Word(++wordIdAuto, R.drawable.science, "Art", "Arte"));
        insertWord(new Word(++wordIdAuto, R.drawable.sea, "Furniture", "Mobiliario"));

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

    public List<Word> queryWords() {
        return new ArrayList<>(words);
    }

    public synchronized void insertWord(Word word) {
        word.setId(++wordIdAuto);
        words.add(word);
    }

    public void deleteWord(Word word) {
        words.remove(word);
    }

}
