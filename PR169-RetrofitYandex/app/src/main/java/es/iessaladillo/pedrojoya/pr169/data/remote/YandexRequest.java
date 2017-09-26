package es.iessaladillo.pedrojoya.pr169.data.remote;

import es.iessaladillo.pedrojoya.pr169.data.models.TranslateResponse;

public class YandexRequest {

    private final TranslateResponse translateResponse;
    private final Throwable throwable;

    private YandexRequest(TranslateResponse translateResponse, Throwable throwable) {
        this.translateResponse = translateResponse;
        this.throwable = throwable;
    }

    public static YandexRequest newErrorInstance(Throwable throwable) {
        return new YandexRequest(null, throwable);
    }

    public static YandexRequest newResponse(TranslateResponse translateResponse) {
        return new YandexRequest(translateResponse, null);
    }

    public TranslateResponse getTranslateResponse() {
        return translateResponse;
    }

    public Throwable getThrowable() {
        return throwable;
    }

}
