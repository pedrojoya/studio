package es.iessaladillo.pedrojoya.pr095.data.local;

import java.util.List;

import es.iessaladillo.pedrojoya.pr095.data.model.Song;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Song> getSongs();

}
