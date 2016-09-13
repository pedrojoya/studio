package es.iessaladillo.pedrojoya.pr119;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

@SuppressWarnings({"WeakerAccess", "unused"})
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

    File mCarpetaDestino;
    InputStream mEntrada;
    String mNombreArchivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnCopiar)
    public void btnCopiarOnClick() {
        // Se selecciona el tipo de subcarpeta dependiendo del origen.
        String tipoSubcarpeta =
                mRgOrigen.getCheckedRadioButtonId() == R.id.rbRaw ?
                        Environment.DIRECTORY_DOWNLOADS :
                        Environment.DIRECTORY_MUSIC;
        // Se obtiene la carpeta de destino.
        boolean permisoRequerido = false;
        switch (mRgDestino.getCheckedRadioButtonId()) {
            case R.id.rbInterno:
                mCarpetaDestino = getFilesDir();
                break;
            case R.id.rbExternoPropio:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    mCarpetaDestino = getExternalFilesDir(tipoSubcarpeta);
                    permisoRequerido = true;
                }
                break;
            case R.id.rbExternoPublico:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    mCarpetaDestino = Environment.getExternalStoragePublicDirectory(tipoSubcarpeta);
                    permisoRequerido = true;
                }
                break;
            case R.id.rbCacheInterno:
                mCarpetaDestino = getCacheDir();
                break;
            case R.id.rbCacheExterno:
                mCarpetaDestino = getExternalCacheDir();
                permisoRequerido = true;
                break;
        }

        // Si es posible realizar la copia.
        if (mCarpetaDestino != null) {
            try {
                // Se abre al flujo de entrada correspondiente.
                if (mRgOrigen.getCheckedRadioButtonId() == R.id.rbRaw) {
                    mEntrada = getResources().openRawResource(R.raw.lorem);
                    mNombreArchivo = RAW_FILE_NAME;
                } else {
                    mEntrada = getAssets().open(ASSET_FILE_NAME);
                    mNombreArchivo = ASSET_FILE_NAME;
                }
                // Si no tenemos el permiso, lo solicitamos.
                if (permisoRequerido && puedeEscribirEnAlmacenamientoExterno()) {
                    solicitarPermisoEscrituraAlmacenamientoExterno();
                } else {
                    // Se copia el archivo en la carpeta de destino.
                    copiarArchivo();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean tienePermiso(String permissionName) {
        return ContextCompat.checkSelfPermission(this, permissionName) !=
                PackageManager.PERMISSION_GRANTED;
    }

    private boolean puedeEscribirEnAlmacenamientoExterno() {
        return tienePermiso(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void solicitarPermisoEscrituraAlmacenamientoExterno() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                RP_ALMACEN_EXTERNO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case RP_ALMACEN_EXTERNO: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Se copia el archivo en la carpeta de destino.
                    copiarArchivo();
                }
                else {
                    // Comprobamos si el usuario ha marcado No volver a preguntar.
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Aún tenemos esperanza. Informamos.
                        Snackbar.make(mBtnCopiar,
                                "Imposible realizar la acción si no se concede el permiso", Snackbar.LENGTH_LONG)
                                .show();
                    }
                    else {
                        // El usuario ha indicado que No se le vuelva a preguntar.
                        Snackbar.make(mBtnCopiar,
                                "Funciones deshabilitadas al no obtener permiso", Snackbar.LENGTH_LONG)
                                .show();
                        deshabilitarOpcionesExterno();
                    }
                }
            }
        }
    }

    private void deshabilitarOpcionesExterno() {
        mRbExternoPropio.setEnabled(false);
        mRbExternoPublico.setEnabled(false);
        mRbCacheExterno.setEnabled(false);
    }

    // Copia el archivo representado por el flujo de entrada ya abierto en la carpeta
    // de destino y con el nombre de destino indicado.
    private void copiarArchivo() {
        BufferedInputStream lector = new BufferedInputStream(mEntrada);
        final File fichero = new File(mCarpetaDestino, mNombreArchivo);
        Log.d(getString(R.string.app_name), fichero.getPath());
        try {
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
            Snackbar.make(mBtnCopiar,
                    getString(R.string.generado, fichero.getPath()), Snackbar.LENGTH_LONG)
                    .setAction(R.string.abrir, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            abrirArchivo(fichero);
                        }
                    }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Envía un intent implícito para abrir el fichero.
    private void abrirArchivo(File fichero) {
        // Si se ha almacenado en directorio privado no podrá ser
        // accedido desde una aplicación externa.
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String mimeType = mRgOrigen.getCheckedRadioButtonId() == R.id.rbRaw ?
                "text/plain" : "audio/mp3";
        intent.setDataAndType(Uri.fromFile(fichero), mimeType);
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
