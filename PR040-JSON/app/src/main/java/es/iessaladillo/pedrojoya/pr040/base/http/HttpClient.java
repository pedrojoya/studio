package es.iessaladillo.pedrojoya.pr040.base.http;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class HttpClient {

    private final List<HttpCall> httpCalls = new ArrayList<>();

    private HttpClient() {
    }

    public static HttpClient getInstance() {
        return new HttpClient();
    }

    public HttpCall newCall(HttpRequest request) {
        return new HttpCall(this, request);
    }

    void addCall(HttpCall httpCall) {
        httpCalls.add(httpCall);
    }

    public void cancel(String tag) {
        for (HttpCall httpCall : httpCalls) {
            if (httpCall.isExecuted() && !httpCall.isCanceled() && TextUtils.equals(
                httpCall.getHttpRequest().getTag(), tag)) {
                httpCall.cancel();
            }
        }
    }

}
