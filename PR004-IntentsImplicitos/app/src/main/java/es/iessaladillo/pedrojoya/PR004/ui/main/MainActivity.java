package es.iessaladillo.pedrojoya.pr004.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr004.R;
import es.iessaladillo.pedrojoya.pr004.utils.IntentsUtils;
import es.iessaladillo.pedrojoya.pr004.utils.ToastUtils;

@SuppressWarnings("SameParameterValue")
public class MainActivity extends AppCompatActivity {

    private static final String WEB_URL = "http://www.genbeta.com";
    private static final String SEARCH_TEXT = "IES Saladillo";
    private static final String PHONE_NUMBER = "(+34)12345789";
    private static final double LONGITUDE = 36.1121;
    private static final double LATITUDE = -5.44347;
    private static final int ZOOM = 19;
    private static final String MAP_SEARCH_TEXT = "duque de rivas, Algeciras";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        Button btnShowInBrowser = ActivityCompat.requireViewById(this, R.id.btnShowInBrowser);
        Button btnSearch = ActivityCompat.requireViewById(this, R.id.btnSearch);
        Button btnDial = ActivityCompat.requireViewById(this, R.id.btnDial);
        Button btnShowInMap = ActivityCompat.requireViewById(this, R.id.btnShowInMap);
        Button btnSearchInMap = ActivityCompat.requireViewById(this, R.id.btnSearchInMap);
        Button btnShowContacts = ActivityCompat.requireViewById(this, R.id.btnShowContacts);

        btnShowInBrowser.setOnClickListener(v -> showInBrowser(WEB_URL));
        btnSearch.setOnClickListener(v -> search(SEARCH_TEXT));
        btnDial.setOnClickListener(v -> dial(PHONE_NUMBER));
        btnShowInMap.setOnClickListener(v -> showInMap(LONGITUDE, LATITUDE, ZOOM));
        btnSearchInMap.setOnClickListener(v -> searchInMap(MAP_SEARCH_TEXT));
        btnShowContacts.setOnClickListener(v -> showContacts());
    }

    private void showInBrowser(String url) {
        Intent intent = IntentsUtils.newViewUriIntent(Uri.parse(url));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.toast(this, getString(R.string.main_no_web_browser));
        }
    }

    private void search(String text) {
        Intent intent = IntentsUtils.newWebSearchIntent(text);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.toast(this, getString(R.string.main_no_web_search));
        }
    }

    private void dial(String phoneNumber) {
        Intent intent = IntentsUtils.newDialIntent(phoneNumber);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.toast(this, getString(R.string.main_no_dial_app));
        }
    }

    private void showInMap(double longitude, double latitude, int zoom) {
        Intent intent = IntentsUtils.newShowInMapIntent(longitude, latitude, zoom);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.toast(this, getString(R.string.main_no_maps_app));
        }
    }

    private void searchInMap(String text) {
        Intent intent = IntentsUtils.newSearchInMapIntent(text);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.toast(this, getString(R.string.main_no_maps_app));
        }
    }

    private void showContacts() {
        Intent intent = IntentsUtils.newContactsIntent();
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.toast(this, getString(R.string.main_no_contacts_app));
        }
    }

}
