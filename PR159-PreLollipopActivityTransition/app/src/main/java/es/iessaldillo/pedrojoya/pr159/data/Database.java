package es.iessaldillo.pedrojoya.pr159.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.iessaldillo.pedrojoya.pr159.R;
import es.iessaldillo.pedrojoya.pr159.data.model.Word;

public class Database {

    private static Database instance;
    private final ArrayList<Word> words;

    private Database() {
        words = new ArrayList<>();
        insertInitialData();
    }

    public synchronized static Database getInstance() {
        if (instance == null) {
            instance = new Database();
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

    public List<Word> getWords() {
        return words;
    }

    public void addWord(Word word) {
        words.add(word);
    }

    public void deleteWord(int position) {
        words.remove(position);
    }

}
