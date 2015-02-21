package es.iessaladillo.pedrojoya.pr119;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends ActionBarActivity {

    private static final String RAW_FILE_NAME = "lorem.txt";
    private static final String ASSET_FILE_NAME = "audio.mp3";
    private static final int BUFFER_SIZE = 1000;

    @InjectView(R.id.rgOrigen)
    RadioGroup mRgOrigen;
    @InjectView(R.id.rgDestino)
    RadioGroup mRgDestino;
    @InjectView(R.id.btnCopiar)
    Button mBtnCopiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btnCopiar)
    public void btnCopiarOnClick() {
        // Se selecciona el tipo de subcarpeta dependiendo del origen.
        String tipoSubcarpeta =
                mRgOrigen.getCheckedRadioButtonId() == R.id.rbRaw ?
                        Environment.DIRECTORY_DOWNLOADS :
                        Environment.DIRECTORY_MUSIC;
        // Se obtiene la carpeta de destino.
        File carpetaDestino = null;
        switch (mRgDestino.getCheckedRadioButtonId()) {
            case R.id.rbInterno:
                carpetaDestino = getFilesDir();
                break;
            case R.id.rbExternoPropio:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    carpetaDestino = getExternalFilesDir(tipoSubcarpeta);
                }
                break;
            case R.id.rbExternoPublico:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    carpetaDestino = Environment.getExternalStoragePublicDirectory(tipoSubcarpeta);
                }
                break;
            case R.id.rbCacheInterno:
                carpetaDestino = getCacheDir();
                break;
            case R.id.rbCacheExterno:
                carpetaDestino = getExternalCacheDir();
                break;
        }

        // Si es posible realizar la copia.
        if (carpetaDestino != null) {
            try {
                // Se abre al flujo de entrada correspondiente.
                InputStream entrada;
                String nombre;
                if (mRgOrigen.getCheckedRadioButtonId() == R.id.rbRaw) {
                    entrada = getResources().openRawResource(R.raw.lorem);
                    nombre = RAW_FILE_NAME;
                } else {
                    entrada = getAssets().open(ASSET_FILE_NAME);
                    nombre = ASSET_FILE_NAME;
                }
                // Se copia el archivo en la carpeta de destino.
                copiarArchivo(entrada, carpetaDestino, nombre);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // Copia el archivo representado por el flujo de entrada ya abierto en la carpeta
    // de destino y con el nombre de destino indicado.
    private void copiarArchivo(InputStream entrada, File dir, String nombreArchivo) {
        BufferedInputStream lector = new BufferedInputStream(entrada);
        File fichero = new File(dir, nombreArchivo);
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
            Toast.makeText(this,
                    getString(R.string.generado) + fichero.getPath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
