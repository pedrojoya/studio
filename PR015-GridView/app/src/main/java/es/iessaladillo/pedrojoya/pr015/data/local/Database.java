package es.iessaladillo.pedrojoya.pr015.data.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.iessaladillo.pedrojoya.pr015.R;
import es.iessaladillo.pedrojoya.pr015.data.local.model.Word;

public class Database {

    private static volatile Database instance;

    private final ArrayList<Word> words = new ArrayList<>();
    private long wordsAutoId = 0;

    private Database() {
        insertInitialData();
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

    private void insertInitialData() {
        words.addAll(new ArrayList<>(Arrays.asList(
                new Word(R.drawable.animal, "Animal","Animal"),
        new Word(R.drawable.bridge, "Bridge", "Puente"),
        new Word(R.drawable.flag, "Flag", "Bandera"),
        new Word(R.drawable.food, "Food", "Comida"),
        new Word(R.drawable.fruit, "Fruit", "Fruta"),
        new Word(R.drawable.glass, "Glass", "Vaso"),
        new Word(R.drawable.plant, "Plant", "Planta"),
        new Word(R.drawable.science, "Science", "Ciencia"),
        new Word(R.drawable.sea, "Sea", "Mar"),
        new Word(R.drawable.space, "Space", "Espacio"),
        new Word(R.drawable.art, "Art", "Arte"),
        new Word(R.drawable.furniture, "Furniture", "Mobiliario"))));
    }

    public List<Word> queryWords() {
        return new ArrayList<>(words);
    }

    public synchronized void insertWord(Word word) {
        word.setId(++wordsAutoId);
        words.add(word);
    }

    public void deleteWord(Word word) {
        words.remove(word);
    }

}
