package es.iessaladillo.pedrojoya.pr119;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends ActionBarActivity {

    private static final String FILE_NAME = "lorem.txt";

    @InjectView(R.id.btnInterno)
    Button mBtnInterno;
    @InjectView(R.id.btnExternoPrivado)
    Button mBtnExternoPrivado;
    @InjectView(R.id.btnExternoPublico)
    Button mBtnExternoPublico;
    @InjectView(R.id.btnCacheInterno)
    Button mBtnCacheInterno;
    @InjectView(R.id.btnCacheExterno)
    Button mBtnCacheExterno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btnInterno)
    public void interno() {
        copiarRawFileTo(R.raw.lorem, getFilesDir(), FILE_NAME);
    }

    @OnClick(R.id.btnExternoPrivado)
    public void externoPrivado() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // Se puede leer y escribir en la tarjeta SD.
            copiarRawFileTo(R.raw.lorem, getExternalFilesDir(Environment.DIRECTORY_MUSIC), FILE_NAME);
        }
    }

    @OnClick(R.id.btnExternoPublico)
    public void externoPublico() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // Se puede leer y escribir en la tarjeta SD.
            copiarRawFileTo(R.raw.lorem,
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), FILE_NAME);
        }
    }

    @OnClick(R.id.btnCacheInterno)
    public void cacheInterno() {
        copiarRawFileTo(R.raw.lorem, getCacheDir(), FILE_NAME);
    }

    @OnClick(R.id.btnCacheExterno)
    public void cacheExterno() {
        copiarRawFileTo(R.raw.lorem, getExternalCacheDir(), FILE_NAME);
    }

    private void copiarRawFileTo(int resIdRawFile, File dir, String name) {
        InputStream entrada = getResources().openRawResource(resIdRawFile);
        BufferedReader lector = new BufferedReader(new InputStreamReader(entrada));
        FileOutputStream salida;
        String linea= null;
        try {
            File fichero = new File(dir, name);
            Log.d("Mia", fichero.getPath());
            salida = new FileOutputStream(fichero);
            PrintWriter escritor = new PrintWriter(salida);
            while ((linea = lector.readLine()) != null) {
                escritor.println(linea);
            }
            escritor.close();
            salida.close();
            lector.close();
            entrada.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
