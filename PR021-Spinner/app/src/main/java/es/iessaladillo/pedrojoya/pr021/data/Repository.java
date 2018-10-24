package es.iessaladillo.pedrojoya.pr021.data;

import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr021.data.local.model.Country;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    @NonNull
    List<Country> queryCountries();

}
