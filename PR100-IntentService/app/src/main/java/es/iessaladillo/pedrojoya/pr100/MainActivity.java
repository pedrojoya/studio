package es.iessaladillo.pedrojoya.pr100;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@SuppressWarnings("unused")
@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN_FRAGMENT = "MainFragment";

    FloatingActionButton btnExportar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        if (getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT) == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.flContenido,
                    MainFragment.newInstance(), TAG_MAIN_FRAGMENT).commit();
        }
    }

    private void initVistas() {
        setupToolbar();
        setupFAB();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void setupFAB() {
        btnExportar = (FloatingActionButton) findViewById(R.id.btnExportar);
        if (btnExportar != null) {
            btnExportar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivityPermissionsDispatcher.exportarWithCheck(MainActivity.this);
                }
            });
        }
    }

    @SuppressWarnings("WeakerAccess")
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void exportar() {
        Fragment frg = getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT);
        if (frg instanceof MainFragment) {
            ((MainFragment) frg).exportar();
        }
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForWriteExternalStorage(final PermissionRequest request) {
        new AlertDialog.Builder(this).setMessage(
                R.string.permission_write_external_storage_rationale).setPositiveButton(
                R.string.permitir, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                }).setNegativeButton(R.string.rechazar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();
            }
        }).show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForWriteExternalStorage() {
        Snackbar.make(btnExportar, R.string.permission_write_external_storage_denied, Snackbar.LENGTH_SHORT)
                .show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAskForWriteExternalStorage() {
        Snackbar.make(btnExportar, R.string.permission_write_external_storage_neverask, Snackbar.LENGTH_LONG)
                .setAction(R.string.configurar, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startInstalledAppDetailsActivity(MainActivity.this);
                    }
                })
                .show();
    }

    public static void startInstalledAppDetailsActivity(@NonNull final Activity context) {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        // Para que deje rastro en la pila de actividades se añaden flags.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }

}
