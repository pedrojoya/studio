package es.iessaladillo.pedrojoya.PR004;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import es.iessaladillo.pedrojoya.PR004.components.MessageManager.ToastMessageManager;
import es.iessaladillo.pedrojoya.PR004.utils.IntentsUtils;
import es.iessaladillo.pedrojoya.PR004.utils.NetworkUtils;
import es.iessaladillo.pedrojoya.PR004.utils.PermissionUtils;

@SuppressWarnings({"FieldCanBeLocal", "SameParameterValue"})
public class MainActivity extends AppCompatActivity {

    private static final int RP_CALL = 1;

    private static final String WEB_URL = "http://www.genbeta.com";
    private static final String SEARCH_TEXT = "IES Saladillo";
    private static final String PHONE_NUMBER = "(+34)12345789";
    private static final double LONGITUDE = 36.1121;
    private static final double LATITUDE = -5.44347;
    private static final int ZOOM = 19;
    private static final String MAP_SEARCH_TEXT = "duque de rivas, Algeciras";

    private ToastMessageManager mMessageManager;

    private Button btnShowInBrowser;
    private Button btnSearch;
    private Button btnCall;
    private Button btnDial;
    private Button btnShowInMap;
    private Button btnSearchInMap;
    private Button btnShowContacts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageManager = new ToastMessageManager();
        initViews();
    }

    private void initViews() {
        btnShowInBrowser = ActivityCompat.requireViewById(this, R.id.btnShowInBrowser);
        btnSearch = ActivityCompat.requireViewById(this, R.id.btnSearch);
        btnCall = ActivityCompat.requireViewById(this, R.id.btnCall);
        btnDial = ActivityCompat.requireViewById(this, R.id.btnDial);
        btnShowInMap = ActivityCompat.requireViewById(this, R.id.btnShowInMap);
        btnSearchInMap = ActivityCompat.requireViewById(this, R.id.btnSearchInMap);
        btnShowContacts = ActivityCompat.requireViewById(this, R.id.btnShowContacts);

        btnShowInBrowser.setOnClickListener(v -> showInBrowser(WEB_URL));
        btnSearch.setOnClickListener(v -> search(SEARCH_TEXT));
        btnCall.setOnClickListener(v -> wantsToCall(PHONE_NUMBER));
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
                mMessageManager.showMessage(btnShowInBrowser, getString(R.string.main_activity_no_web_browser));
            }
        } else {
            mMessageManager.showMessage(btnShowInBrowser, getString(R.string.main_activity_no_connection));
        }
    }

    private void search(String text) {
        if (NetworkUtils.isConnectionAvailable(getApplicationContext())) {
            Intent intent = IntentsUtils.newWebSearchIntent(text);
            if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
                startActivity(intent);
            } else {
                mMessageManager.showMessage(btnSearch, getString(R.string.main_activity_no_web_search));
            }
        } else {
            mMessageManager.showMessage(btnSearch, getString(R.string.main_activity_no_connection));
        }
    }

    private void wantsToCall(String phoneNumber) {
        if (!PermissionUtils.canCall(this)) {
            requestCallPermission();
        } else {
            call(phoneNumber);
        }
    }

    private void requestCallPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                RP_CALL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == RP_CALL && PermissionUtils.canCall(this)) {
            call(PHONE_NUMBER);
        } else {
            // Check if the user set "Don't ask again"
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                reportRationale();
            } else {
                mMessageManager.showMessage(btnCall, getString(R.string.main_activity_no_call_permission_rationale));
            }
        }
    }

    private void reportRationale() {
        Snackbar.make(btnCall, R.string.general_permission_required, Snackbar.LENGTH_LONG).setAction(
                R.string.general_configure,
                view -> IntentsUtils.startInstalledAppDetailsActivity(MainActivity
                        .this)).show();
    }

    private void call(String phoneNumber) {
        Intent intent = IntentsUtils.newCallIntent(phoneNumber);
        if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            mMessageManager.showMessage(btnCall,
                    getString(R.string.main_activity_no_call_app));
        }
    }

    private void dial(String phoneNumber) {
        Intent intent = IntentsUtils.newDialIntent(phoneNumber);
        if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            mMessageManager.showMessage(btnDial, getString(R.string.main_activity_no_dial_app));
        }
    }

    private void showInMap(double longitude, double latitude, int zoom) {
        Intent intent = IntentsUtils.newShowInMapIntent(longitude, latitude, zoom);
        if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            mMessageManager.showMessage(btnShowInMap, getString(R.string.main_activity_no_maps_app));
        }
    }

    private void searchInMap(String text) {
        Intent intent = IntentsUtils.newSearchInMapIntent(text);
        if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            mMessageManager.showMessage(btnShowInMap, getString(R.string.main_activity_no_maps_app));
        }
    }

    private void showContacts() {
        Intent intent = IntentsUtils.newContactsIntent();
        if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            mMessageManager.showMessage(btnShowContacts,
                    getString(R.string.main_activity_no_contacts_app));
        }
    }

}
