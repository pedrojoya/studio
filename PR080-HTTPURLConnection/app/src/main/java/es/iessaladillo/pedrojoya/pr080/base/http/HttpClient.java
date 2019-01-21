package es.iessaladillo.pedrojoya.pr080.base.http;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class HttpClient {

    @SuppressWarnings("unused")
    public HttpResponse send(HttpRequest httpRequest) {
        return doRequest(httpRequest);
    }

    public void sendAsync(HttpRequest httpRequest, HttpResponse.Callback callback) {
        new SendAsyncTask(httpRequest, callback).execute();
    }

    private HttpResponse doRequest(HttpRequest httpRequest) {
        HttpURLConnection httpURLConnection = null;
        HttpResponse httpResponse;
        try {
            httpURLConnection = (HttpURLConnection) httpRequest.getUrl().openConnection();
            httpURLConnection.setRequestMethod(httpRequest.getMethod());
            httpURLConnection.setConnectTimeout(httpRequest.getTimeout());
            if (httpRequest.getHeaders() != null) {
                for (Map.Entry<String, String> entry : httpRequest.getHeaders().entrySet()) {
                    httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (httpRequest.getFormUrlEncodedBody() != null) {
                httpURLConnection.setDoOutput(true);
                String bodyContent = urlEncodeUTF8(httpRequest.getFormUrlEncodedBody());
                httpURLConnection.setFixedLengthStreamingMode(bodyContent.length());
                writeContent(httpURLConnection.getOutputStream(), bodyContent);
            }
            byte[] responseBody;
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                responseBody = readContent(httpURLConnection.getInputStream());
            } else {
                responseBody = readContent(httpURLConnection.getErrorStream());
            }
            httpResponse = new HttpResponse(httpURLConnection.getResponseCode(),
                httpURLConnection.getResponseMessage(), responseBody, null);
        } catch (IOException e) {
            httpResponse = new HttpResponse(0,
                null, null, e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return httpResponse;
    }

    private static String urlEncodeUTF8(Map<?, ?> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s", urlEncodeUTF8(entry.getKey().toString()),
                urlEncodeUTF8(entry.getValue().toString())));
        }
        return sb.toString();
    }

    private static String urlEncodeUTF8(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "UTF-8");
    }

    private byte[] readContent(InputStream inputStream) throws IOException {
        final int bufLen = 4 * 0x400; // 4KB
        byte[] buf = new byte[bufLen];
        int readLen;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            while ((readLen = inputStream.read(buf, 0, bufLen)) != -1)
                outputStream.write(buf, 0, readLen);

            return outputStream.toByteArray();
        }
    }

    private static void writeContent(OutputStream outputStream, String content) {
        try (PrintWriter printWriter = new PrintWriter(outputStream)) {
            printWriter.write(content);
            printWriter.flush();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SendAsyncTask extends AsyncTask<Void, Void, HttpResponse> {

        private final HttpRequest httpRequest;
        private final HttpResponse.Callback callback;

        private SendAsyncTask(HttpRequest httpRequest, HttpResponse.Callback callback) {
            this.httpRequest = httpRequest;
            this.callback = callback;
        }

        @Override
        protected HttpResponse doInBackground(Void... voids) {
            return doRequest(httpRequest);
        }


        @Override
        protected void onPostExecute(HttpResponse httpResponse) {
            if (httpResponse.getException() != null) {
                callback.onFailure(httpRequest, httpResponse.getException());
            } else {
                callback.onResponse(httpRequest, httpResponse);
            }
        }

    }

}
