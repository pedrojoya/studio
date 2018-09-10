package es.iessaladillo.pedrojoya.pr140.data.remote;

import es.iessaladillo.pedrojoya.pr140.data.remote.model.Counting;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("{cityCode}.xml")
    Call<Counting> getCityCounting(@Path("cityCode") String cityCode);

}
