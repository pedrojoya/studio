package es.iessaladillo.pedrojoya.pr119;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

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
        generar(R.raw.lorem, getFilesDir());
    }

    @OnClick(R.id.btnExternoPrivado)
    public void externoPrivado() {
        String estadoSD = Environment.getExternalStorageState();
        if (estadoSD.equals(Environment.MEDIA_MOUNTED)) {
            // Se puede leer y escribir en la tarjeta SD.
            generar(R.raw.lorem, getExternalFilesDir(Environment.DIRECTORY_MUSIC));
        }
    }

    @OnClick(R.id.btnExternoPublico)
    public void externoPublico() {
        String estadoSD = Environment.getExternalStorageState();
        if (estadoSD.equals(Environment.MEDIA_MOUNTED)) {
            // Se puede leer y escribir en la tarjeta SD.
            generar(R.raw.lorem,
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        }
    }

    @OnClick(R.id.btnCacheInterno)
    public void cacheInterno() {
        generar(R.raw.lorem, getCacheDir());
    }

    @OnClick(R.id.btnCacheExterno)
    public void cacheExterno() {
        generar(R.raw.lorem, getExternalCacheDir());
    }

    private void generar(int resIdRawFile, File dir) {
        InputStream entrada = getResources().openRawResource(resIdRawFile);
        BufferedReader lector = new BufferedReader(new InputStreamReader(entrada));
        FileOutputStream salida;
        String linea= null;
        try {
            File fichero = new File(dir, "lorem.txt");
            fichero.createNewFile();
            Toast.makeText(this, fichero.getPath().toString(), Toast.LENGTH_SHORT).show();
            salida = new FileOutputStream(fichero);
            PrintWriter escritor = new PrintWriter(salida);
            while ((linea = lector.readLine()) != null) {
                escritor.println(linea);
            }
            escritor.close();
            salida.close();
            lector.close();
            entrada.close();
            Toast.makeText(this, "Generado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
