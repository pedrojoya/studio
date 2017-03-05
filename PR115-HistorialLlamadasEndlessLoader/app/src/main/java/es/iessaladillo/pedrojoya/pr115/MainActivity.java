package es.iessaladillo.pedrojoya.pr115;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
@RuntimePermissions
public class MainActivity extends AppCompatActivity implements EndlessListView.LoadAgent,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LIMIT = 10;
    private static final int DATOS_LOADER = 0;
    private static final String ARG_LIMIT = "limit";

    @BindView(R.id.lstLlamadas)
    EndlessListView mLstLlamadas;

    private int mOffset;
    private Adaptador mAdaptador;
    private LoaderManager mLoaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initVistas();
        mLoaderManager = getSupportLoaderManager();
        mOffset = 0;
        MainActivityPermissionsDispatcher.initCargadorWithCheck(this);
        //loadData();
    }

    @NeedsPermission({Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS})
    void initCargador() {
        Bundle args = new Bundle();
        args.putInt(ARG_LIMIT, mOffset + LIMIT);
        mLoaderManager.initLoader(DATOS_LOADER, args, this);
    }

    private void initVistas() {
        String[] from = {CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE,
                         CallLog.Calls.DATE, CallLog.Calls.DURATION};
        int[] to = {R.id.lblNumero, R.id.lblNombre, R.id.imgTipoLlamada, R.id.lblFecha, R.id
                .lblDuracion};
        mAdaptador = new Adaptador(this, R.layout.activity_main_item, null, from, to, 0);
        mLstLlamadas.setAdapter(mAdaptador);
        mLstLlamadas.setLoadAgent(this);
    }

    // Carga más datos.
    @Override
    public void loadData() {
        MainActivityPermissionsDispatcher.restartCargadorWithCheck(this);
        mOffset += LIMIT;
    }

    @NeedsPermission({Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS})
    void restartCargador() {
        Bundle args = new Bundle();
        args.putInt(ARG_LIMIT, mOffset + LIMIT);
        mLoaderManager.restartLoader(0, args, this);
    }

    @OnShowRationale({Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS})
    void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this).setMessage(R.string.permission_rationale).setPositiveButton(
                R.string.solicitar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                }).setNegativeButton(R.string.rechazar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();
            }
        }).show();
    }

    @OnPermissionDenied({Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS})
    void showDenied() {
        new AlertDialog.Builder(this).setMessage(R.string.lo_sentimos).setPositiveButton(
                R.string.solicitar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivityPermissionsDispatcher.restartCargadorWithCheck(
                                MainActivity.this);
                    }
                }).setNegativeButton(R.string.cerrar_app, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }

    @OnNeverAskAgain({Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS})
    void showNeverAsk() {
        new AlertDialog.Builder(this).setMessage(R.string.lo_sentimos).setPositiveButton(
                R.string.solicitar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startInstalledAppDetailsActivity(MainActivity.this);
                        finish();
                    }
                }).setNegativeButton(R.string.cerrar_app, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();

        Snackbar.make(mLstLlamadas, R.string.permission_neverask, Snackbar.LENGTH_LONG).setAction(
                R.string.configurar, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startInstalledAppDetailsActivity(MainActivity.this);
                    }
                }).show();
    }

    private static void startInstalledAppDetailsActivity(@NonNull final Activity context) {
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
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }

    // Cuando se debe crear el cargador. Retorna el cargador.
    // Recibe el id del cargador y sus argumentos.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Se obtienen los argumentos.
        int limit = LIMIT;
        if (args != null) {
            limit = args.getInt(ARG_LIMIT);
        }

        // Se crea el cargador, que realiza una consulta al content provider.
        // Recibe: contexto, uri del content provider, array de nombres de campos
        // a seleccionar, condición where, parámetros del where y orden.
        String[] campos = {CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,
                           CallLog.Calls.TYPE, CallLog.Calls.CACHED_NUMBER_LABEL, CallLog.Calls
                                   .DATE, CallLog.Calls.DURATION};
        String orden = CallLog.Calls.DATE + " DESC LIMIT " + 0 + "," + limit;
        return new CursorLoader(this, CallLog.Calls.CONTENT_URI, campos, null, null, orden);
    }

    // Cuando terminan de cargarse los datos en el cargador.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Se cambia el cursor del adaptador por el que tiene los datos cargados.
        mAdaptador.changeCursor(data);
        mLstLlamadas.setLoaded();
    }

    // Cuando se resetea el cargador.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Se vacía de datos el adaptador asignándole un cursor nulo.
        mAdaptador.changeCursor(null);
    }
}
