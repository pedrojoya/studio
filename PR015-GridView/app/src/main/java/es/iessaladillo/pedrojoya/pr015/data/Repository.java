package es.iessaladillo.pedrojoya.pr015.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr015.data.model.Word;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Word> getWords();
    void addWord(Word word);
    void deleteWord(int position);

}
