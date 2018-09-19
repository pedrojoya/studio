package es.iessaladillo.pedrojoya.pr017.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr017.data.local.model.Word;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Word> queryWords();
    void addWord(Word word);
    void deleteWord(Word word);

}
