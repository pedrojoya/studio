package es.iessaladillo.pedrojoya.pr169;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import es.iessaladillo.pedrojoya.pr169.api.Constants;
import es.iessaladillo.pedrojoya.pr169.api.YandexAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("WeakerAccess")
public class App extends Application {

    private static YandexAPI mApi;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        buildAPIService();
    }

    // Crea a través de Retrofit el servicio de acceso a la API de Yandex,
    private void buildAPIService() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // Se crea el cliente OkHttp.
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Interceptor.Chain chain) throws IOException {
//                    // Se obtiene la petición original.
//                    Request request = chain.request();
//                    // Se obtiene una nueva URL basada en la original de la petición
//                    // agregando el parámetro de consulta correspondiente a la api key.
//                    HttpUrl newUrl = request.url().newBuilder().addQueryParameter(
//                            Constants.PARAM_API_KEY, Constants.API_KEY).build();
//                    // Se crea una nueva petición basándose en la original.
//                    // Se cambia la url por la nueva modificada.
//                    Request newRequest;
//                    newRequest = request.newBuilder()
//                            .url(newUrl)
//                            .build();
//                    // Se reemplaza la nueva petición por la antigua y se continúa.
//                    return chain.proceed(newRequest);
//                }
//            });
        builder.addInterceptor(logInterceptor);
        builder.addInterceptor(new StethoInterceptor());
        builder.addInterceptor(new ChuckInterceptor(getApplicationContext()));
        OkHttpClient client = builder.build();
        // Se crea el objeto Retrofit.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Se crea el servicio.
        mApi = retrofit.create(YandexAPI.class);
    }

    public static YandexAPI getAPIService() {
        return mApi;
    }

}
