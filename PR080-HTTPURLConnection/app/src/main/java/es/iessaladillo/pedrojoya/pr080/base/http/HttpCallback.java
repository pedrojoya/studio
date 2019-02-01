package es.iessaladillo.pedrojoya.pr080.base.http;

import java.io.IOException;

@SuppressWarnings("unused")
public interface HttpCallback {

    void onFailure(HttpCall httpCall, IOException exception);

    void onResponse(HttpCall httpCall, HttpResponse httpResponse);

}