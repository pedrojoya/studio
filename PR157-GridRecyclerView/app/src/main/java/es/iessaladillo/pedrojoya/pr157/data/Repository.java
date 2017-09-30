package es.iessaladillo.pedrojoya.pr157.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr157.data.model.Word;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Word> getWords();
    void addWord(Word word);
    void deleteWord(int position);

}
