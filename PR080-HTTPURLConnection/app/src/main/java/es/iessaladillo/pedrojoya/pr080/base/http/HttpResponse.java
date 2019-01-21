package es.iessaladillo.pedrojoya.pr080.base.http;

import java.io.IOException;

public class HttpResponse {

    private final int code;
    private final String message;
    private final byte[] body;
    private final IOException exception;

    HttpResponse(int code, String message, byte[] body, IOException exception) {
        this.code = code;
        this.message = message;
        this.body = body;
        this.exception = exception;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public byte[] getBody() {
        return body;
    }

    public IOException getException() {
        return exception;
    }

}
