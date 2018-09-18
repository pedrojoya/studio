package es.iessaladillo.pedrojoya.pr021.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr021.data.local.model.Country;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Country> queryCountries();

}
