package es.iessaldillo.pedrojoya.pr208.data;

import android.content.Context;
import android.net.Uri;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import es.iessaldillo.pedrojoya.pr208.data.local.entities.Character;
import es.iessaldillo.pedrojoya.pr208.data.remote.StarWarsApi;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl ourInstance;
    private StarWarsApi api;

    static RepositoryImpl getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new RepositoryImpl(context);
        }
        return ourInstance;
    }

    public RepositoryImpl(Context context) {
        buildStarWarsApiClient(context);
    }

    private void buildStarWarsApiClient(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(
                new ChuckInterceptor(context))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://swapi.co/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        api = retrofit.create(StarWarsApi.class);
    }


    // Obtiene en forma de cadena separada por comas la lista de personajes que cumplen con el
    // criterio de búsqueda.
    @Override
    public Maybe<String> searchPeople(String search) {
        return api.searchPeople(search)
                .flatMap(respuesta -> Observable.fromIterable(respuesta.getPeopleResponseResults()))
                .map(result -> result.getName())
                .reduce((name1, name2) -> name1 + ", " + name2);
    }

    // Obtiene en forma de cadena separada por comas la lista de personajes que cumplen con el
    // criterio de búsqueda, mostrando el planeta de nacimiento de cada uno de ellos.
    @Override
    public Maybe<String> searchCharacter(String search) {
        return api.searchPeople(search)
                // Para cada respuesta, obtén la lista de resultados.
                .concatMap(peopleResponse -> Observable.fromIterable(peopleResponse.getPeopleResponseResults()))
                // Para cada resultado obtén el planeta de nacimiento y crea un objeto Character con
                // su nombre y el nombre del planeta.
/*
                .concatMap(peopleResponseResult -> api.getPlanet(
                        Uri.parse(peopleResponseResult.getHomeworld()).getLastPathSegment())
                        .map(planet -> new Character(peopleResponseResult.getName(), planet
                                .getName())))
*/
                // Con flatmap podemos poner una función combinadora para crear cada par.
                .flatMap(  peopleResponseResult ->
                        api.getPlanet(Uri.parse(peopleResponseResult.getHomeworld())
                                .getLastPathSegment()),
                        (peopleResponseResult, planetResponse) -> new Character(peopleResponseResult.getName(), planetResponse.getName()))
                // Para cada caracter obtén una cadena con el nombre y el planeta.
                .map(character -> character.getNombre() + " (" + character.getPlaneta() + ")")
                // Se reduce a una única cadena.
                .reduce((name1, name2) -> name1 + ", " + name2);
    }

}
