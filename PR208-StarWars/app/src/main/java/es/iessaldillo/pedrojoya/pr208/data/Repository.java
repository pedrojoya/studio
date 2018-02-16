package es.iessaldillo.pedrojoya.pr208.data;

import io.reactivex.Maybe;

public interface Repository {

    Maybe<String> searchPeople(String search);
    Maybe<String> searchCharacter(String search);

}
