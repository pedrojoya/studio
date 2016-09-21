package es.iessaladillo.pedrojoya.pr188;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@SuppressWarnings({"WeakerAccess", "unused"})
@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private static final String RAW_FILE_NAME = "lorem.txt";
    private static final String ASSET_FILE_NAME = "audio.mp3";
    private static final int BUFFER_SIZE = 1000;
    private static final int RP_ALMACEN_EXTERNO = 1;

    @BindView(R.id.rgOrigen)
    RadioGroup mRgOrigen;
    @BindView(R.id.rgDestino)
    RadioGroup mRgDestino;
    @BindView(R.id.btnCopiar)
    Button mBtnCopiar;
    @BindView(R.id.rbExternoPropio)
    RadioButton mRbExternoPropio;
    @BindView(R.id.rbExternoPublico)
    RadioButton mRbExternoPublico;
    @BindView(R.id.rbCacheExterno)
    RadioButton mRbCacheExterno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        habilitarOpciones(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }

    @OnClick(R.id.btnCopiar)
    public void btnCopiarOnClick() {
        // Si no tenemos el permiso, lo solicitamos.
        if (isPermisoRequerido()) {
            MainActivityPermissionsDispatcher.copiarArchivoConPermisoWithCheck(this);
        } else {
            // Se copia el archivo en la carpeta de destino.
            copiarArchivo();
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void copiarArchivoConPermiso() {
        copiarArchivo();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void mostrarDialogoInformativoPermiso(PermissionRequest request) {
        new AlertDialog.Builder(this).setMessage(R.string.es_necesario)
                .setTitle(R.string.permiso_requerido)
                .setPositiveButton(android.R.string.ok,
                        (dialogInterface, i) -> ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                RP_ALMACEN_EXTERNO))
                .show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void mostrarError() {
        Snackbar.make(mBtnCopiar, R.string.no_se_pudo, Snackbar.LENGTH_LONG).show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void mostrarNoDisponible() {
        Snackbar.make(mBtnCopiar, R.string.accion_no_disponible, Snackbar.LENGTH_LONG)
                .setAction(R.string.configurar,
                        view -> startInstalledAppDetailsActivity(MainActivity.this))
                .show();
    }

    private String getTipoSubcarpeta() {
        return mRgOrigen.getCheckedRadioButtonId()
                       == R.id.rbRaw ? Environment.DIRECTORY_DOWNLOADS : Environment
                       .DIRECTORY_MUSIC;
    }

    private File getCarpetaDestino() {
        switch (mRgDestino.getCheckedRadioButtonId()) {
            case R.id.rbExternoPropio:
                return getExternalFilesDir(getTipoSubcarpeta());
            case R.id.rbExternoPublico:
                return Environment.getExternalStoragePublicDirectory(getTipoSubcarpeta());
            case R.id.rbCacheInterno:
                return getCacheDir();
            case R.id.rbCacheExterno:
                return getExternalCacheDir();
            default:
                return getFilesDir();
        }
    }

    private InputStream getFlujoEntrada() throws IOException {
        return mRgOrigen.getCheckedRadioButtonId() == R.id.rbRaw ? getResources().openRawResource(
                R.raw.lorem) : getAssets().open(getNombreArchivo());
    }

    private String getNombreArchivo() {
        return mRgOrigen.getCheckedRadioButtonId() == R.id.rbRaw ? RAW_FILE_NAME : ASSET_FILE_NAME;
    }

    @NonNull
    private String getMimeType() {
        return mRgOrigen.getCheckedRadioButtonId() == R.id.rbRaw ? "text/plain" : "audio/mp3";
    }

    private boolean isPermisoRequerido() {
        int id = mRgDestino.getCheckedRadioButtonId();
        return (id == R.id.rbExternoPropio) || (id == R.id.rbExternoPublico) || (id
                == R.id.rbCacheExterno);
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

    // Copia el archivo representado por el flujo de entrada en la carpeta
    // de destino y con el nombre de destino indicado.
    private void copiarArchivo() {
        BufferedInputStream lector;
        try {
            lector = new BufferedInputStream(getFlujoEntrada());
            final File fichero = new File(getCarpetaDestino(), getNombreArchivo());
            Log.d(getString(R.string.app_name), fichero.getPath());
            FileOutputStream salida = new FileOutputStream(fichero);
            BufferedOutputStream escritor = new BufferedOutputStream(salida);
            byte[] array = new byte[BUFFER_SIZE];
            int leidos = lector.read(array);
            while (leidos > 0) {
                escritor.write(array, 0, leidos);
                leidos = lector.read(array);
            }
            escritor.close();
            lector.close();
            Snackbar.make(mBtnCopiar, getString(R.string.generado, fichero.getPath()),
                    Snackbar.LENGTH_LONG)
                    .setAction(R.string.abrir, v -> abrirArchivo(fichero))
                    .show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Envía un intent implícito para abrir el fichero.
    private void abrirArchivo(File fichero) {
        // Si se ha almacenado en directorio privado no podrá ser
        // accedido desde una aplicación externa.
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(fichero), getMimeType());
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void habilitarOpciones(boolean mounted) {
        mRbExternoPropio.setEnabled(mounted);
        mRbExternoPublico.setEnabled(mounted);
        mRbCacheExterno.setEnabled(mounted);
    }

}
