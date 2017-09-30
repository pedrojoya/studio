package es.iessaldillo.pedrojoya.pr159.data;

import java.util.List;

import es.iessaldillo.pedrojoya.pr159.data.model.Word;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Word> getWords();
    void addWord(Word word);
    void deleteWord(int position);

}
