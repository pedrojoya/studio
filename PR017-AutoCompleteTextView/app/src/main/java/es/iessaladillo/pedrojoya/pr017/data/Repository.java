package es.iessaladillo.pedrojoya.pr017.data;

import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr017.data.local.model.Word;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    @NonNull List<Word> queryWords();
    void addWord(@NonNull Word word);
    void deleteWord(@NonNull Word word);

}
