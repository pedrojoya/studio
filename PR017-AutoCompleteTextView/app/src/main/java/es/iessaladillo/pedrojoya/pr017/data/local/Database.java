package es.iessaladillo.pedrojoya.pr017.data.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.iessaladillo.pedrojoya.pr017.R;
import es.iessaladillo.pedrojoya.pr017.data.local.model.Word;

public class Database {

    private static Database instance;

    private final ArrayList<Word> words;
    private long wordIdAuto = 0;

    private Database() {
        words = new ArrayList<>(Arrays.asList(
                new Word(++wordIdAuto, R.drawable.animal, "Animal","Animal"),
                new Word(++wordIdAuto,R.drawable.bridge, "Bridge", "Puente"),
                new Word(++wordIdAuto,R.drawable.flag, "Flag", "Bandera"),
                new Word(++wordIdAuto,R.drawable.food, "Food", "Comida"),
                new Word(++wordIdAuto,R.drawable.fruit, "Fruit", "Fruta"),
                new Word(++wordIdAuto,R.drawable.glass, "Glass", "Vaso"),
                new Word(++wordIdAuto,R.drawable.plant, "Plant", "Planta"),
                new Word(++wordIdAuto,R.drawable.science, "Science", "Ciencia"),
                new Word(++wordIdAuto,R.drawable.sea, "Sea", "Mar"),
                new Word(++wordIdAuto,R.drawable.plant, "Space", "Espacio"),
                new Word(++wordIdAuto,R.drawable.science, "Art", "Arte"),
                new Word(++wordIdAuto,R.drawable.sea, "Furniture", "Mobiliario")
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

    public List<Word> queryWords() {
        return words;
    }

    public void addWord(Word word) {
        word.setId(++wordIdAuto);
        words.add(word);
    }

    public void deleteWord(Word word) {
        words.remove(word);
    }

}
