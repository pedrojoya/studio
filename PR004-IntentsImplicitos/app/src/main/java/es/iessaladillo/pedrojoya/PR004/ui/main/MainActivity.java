package es.iessaladillo.pedrojoya.PR004.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import es.iessaladillo.pedrojoya.PR004.R;
import es.iessaladillo.pedrojoya.PR004.utils.IntentsUtils;
import es.iessaladillo.pedrojoya.PR004.utils.NetworkUtils;
import es.iessaladillo.pedrojoya.PR004.utils.ToastUtils;

@SuppressWarnings({"FieldCanBeLocal", "SameParameterValue"})
public class MainActivity extends AppCompatActivity {

    private static final String WEB_URL = "http://www.genbeta.com";
    private static final String SEARCH_TEXT = "IES Saladillo";
    private static final String PHONE_NUMBER = "(+34)12345789";
    private static final double LONGITUDE = 36.1121;
    private static final double LATITUDE = -5.44347;
    private static final int ZOOM = 19;
    private static final String MAP_SEARCH_TEXT = "duque de rivas, Algeciras";

    private Button btnShowInBrowser;
    private Button btnSearch;
    private Button btnDial;
    private Button btnShowInMap;
    private Button btnSearchInMap;
    private Button btnShowContacts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnShowInBrowser = ActivityCompat.requireViewById(this, R.id.btnShowInBrowser);
        btnSearch = ActivityCompat.requireViewById(this, R.id.btnSearch);
        btnDial = ActivityCompat.requireViewById(this, R.id.btnDial);
        btnShowInMap = ActivityCompat.requireViewById(this, R.id.btnShowInMap);
        btnSearchInMap = ActivityCompat.requireViewById(this, R.id.btnSearchInMap);
        btnShowContacts = ActivityCompat.requireViewById(this, R.id.btnShowContacts);

        btnShowInBrowser.setOnClickListener(v -> showInBrowser(WEB_URL));
        btnSearch.setOnClickListener(v -> search(SEARCH_TEXT));
        btnDial.setOnClickListener(v -> dial(PHONE_NUMBER));
        btnShowInMap.setOnClickListener(v -> showInMap(LONGITUDE, LATITUDE, ZOOM));
        btnSearchInMap.setOnClickListener(v -> searchInMap(MAP_SEARCH_TEXT));
        btnShowContacts.setOnClickListener(v -> showContacts());
    }

    private void showInBrowser(String url) {
        if (NetworkUtils.isConnectionAvailable(getApplicationContext())) {
            Intent intent = IntentsUtils.newViewUriIntent(Uri.parse(url));
            if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
                startActivity(intent);
            } else {
                ToastUtils.toast(this, getString(R.string.main_activity_no_web_browser));
            }
        } else {
            ToastUtils.toast(this, getString(R.string.main_activity_no_connection));
        }
    }

    private void search(String text) {
        if (NetworkUtils.isConnectionAvailable(getApplicationContext())) {
            Intent intent = IntentsUtils.newWebSearchIntent(text);
            if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
                startActivity(intent);
            } else {
                ToastUtils.toast(this, getString(R.string.main_activity_no_web_search));
            }
        } else {
            ToastUtils.toast(this, getString(R.string.main_activity_no_connection));
        }
    }

    private void dial(String phoneNumber) {
        Intent intent = IntentsUtils.newDialIntent(phoneNumber);
        if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            ToastUtils.toast(this, getString(R.string.main_activity_no_dial_app));
        }
    }

    private void showInMap(double longitude, double latitude, int zoom) {
        Intent intent = IntentsUtils.newShowInMapIntent(longitude, latitude, zoom);
        if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            ToastUtils.toast(this, getString(R.string.main_activity_no_maps_app));
        }
    }

    private void searchInMap(String text) {
        Intent intent = IntentsUtils.newSearchInMapIntent(text);
        if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            ToastUtils.toast(this, getString(R.string.main_activity_no_maps_app));
        }
    }

    private void showContacts() {
        Intent intent = IntentsUtils.newContactsIntent();
        if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            ToastUtils.toast(this, getString(R.string.main_activity_no_contacts_app));
        }
    }

}
