package es.iessaldillo.pedrojoya.pr208.data.remote;

import es.iessaldillo.pedrojoya.pr208.data.remote.responses.PlanetResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import es.iessaldillo.pedrojoya.pr208.data.remote.responses.PeopleResponse;

public interface StarWarsApi {

    @GET("people")
    Observable<PeopleResponse> searchPeople(@Query("search") String search);

    @GET("planets/{planetId}")
    Observable<PlanetResponse> getPlanet(@Path("planetId") String planetId);

}
