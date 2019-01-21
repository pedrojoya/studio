package es.iessaladillo.pedrojoya.pr040.base.http;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HttpRequest {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    private final URL url;
    private final String method;
    private final Map<String, String> headers;
    private final int timeout;
    private final Map<String, String> formUrlEncodedBody;
    private final String tag;

    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.timeout = builder.timeout;
        this.formUrlEncodedBody = builder.formUrlEncodedBody;
        this.tag = builder.tag;
    }

    public String getMethod() {
        return method;
    }

    public URL getUrl() {
        return url;
    }

    public int getTimeout() {
        return timeout;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getFormUrlEncodedBody() {
        return formUrlEncodedBody;
    }

    public String getTag() {
        return tag;
    }

    @SuppressWarnings("unused")
    public static class Builder {

        private final URL url;
        private String method = METHOD_GET;
        private final Map<String, String> headers = new LinkedHashMap<>();
        private int timeout = 0;
        private Map<String, String> formUrlEncodedBody;
        private String tag;

        public Builder(URL url) {
            this.url = url;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder addHeader(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public Builder setTimeout(int timeoutMillis) {
            timeout = timeoutMillis;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder setFormUrlEncodedBody(Map<String, String> formUrlEncodedBody) {
            this.formUrlEncodedBody = formUrlEncodedBody;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }

    }

}
