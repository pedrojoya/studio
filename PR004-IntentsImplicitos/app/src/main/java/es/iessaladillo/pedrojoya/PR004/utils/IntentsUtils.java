package es.iessaladillo.pedrojoya.pr004.utils;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;

import static android.content.ContentResolver.SCHEME_CONTENT;
import static android.webkit.WebView.SCHEME_GEO;
import static android.webkit.WebView.SCHEME_TEL;

@SuppressWarnings("unused")
public class IntentsUtils {

    private IntentsUtils() {
    }

    public static Intent newViewUriIntent(Uri uri) {
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent newWebSearchIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, text);
        return intent;
    }

    public static Intent newDialIntent(String phoneNumber) {
        return new Intent(Intent.ACTION_DIAL,
            Uri.parse(SCHEME_TEL + phoneNumber.trim()));
    }

    public static Intent newShowInMapIntent(double longit, double lat, int zoom) {
        return new Intent(Intent.ACTION_VIEW,
            Uri.parse("geo:" + longit + "," + lat + "?z=" + zoom));
    }

    public static Intent newSearchInMapIntent(String text) {
        return new Intent(Intent.ACTION_VIEW,
            Uri.parse(SCHEME_GEO + text.trim()));
    }

    public static Intent newContactsIntent() {
        return new Intent(Intent.ACTION_VIEW,
            Uri.parse(SCHEME_CONTENT + "://contacts/people/"));
    }

}
