package es.iessaladillo.pedrojoya.pr015.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr015.data.local.model.Word;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Word> queryWords();
    void insertWord(Word word);
    void deleteWord(Word word);

}
