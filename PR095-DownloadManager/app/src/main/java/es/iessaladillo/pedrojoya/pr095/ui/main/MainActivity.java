package es.iessaladillo.pedrojoya.pr095.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import es.iessaladillo.pedrojoya.pr095.R;
import es.iessaladillo.pedrojoya.pr095.utils.FragmentUtils;
import es.iessaladillo.pedrojoya.pr095.utils.IntentsUtils;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT";

    private FloatingActionButton btnPlayStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        MainActivityPermissionsDispatcher.loadFragmentWithPermissionCheck(this);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void loadFragment() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT) == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                    MainFragment.newInstance(), TAG_MAIN_FRAGMENT);
            btnPlayStop.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        }
    }

    private void initViews() {
        btnPlayStop = ActivityCompat.requireViewById(this, R.id.btnPlayStop);
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showError() {
        Snackbar.make(btnPlayStop, R.string.main_activity_error_permission_required,
                Snackbar.LENGTH_LONG).setAction(R.string.main_activity_configure,
                view -> startInstalledAppDetailsActivity()).show();
        btnPlayStop.setImageResource(R.drawable.ic_settings_white_24dp);
        btnPlayStop.setOnClickListener(
                view -> startInstalledAppDetailsActivity());
    }

    private void startInstalledAppDetailsActivity() {
        startActivity(IntentsUtils.newInstalledAppDetailsActivityIntent(this));
    }

}