package es.iessaladillo.pedrojoya.pr138;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    private static final String URL_FOTO = "http://lorempixel.com/400/200/sports/";

    private ImageView imgFoto;
    private TextView lblVibrant;
    private TextView lblLightVibrant;
    private TextView lblDarkVibrant;
    private TextView lblMuted;
    private TextView lblLightMuted;
    private TextView lblDarkMuted;
    private Toolbar toolbar;

    private int mContador = 0;
    private Palette mPaleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarFoto();
            }
        });
        lblVibrant = (TextView) findViewById(R.id.lblVibrant);
        lblVibrant.setOnClickListener(this);
        lblLightVibrant = (TextView) findViewById(R.id.lblLightVibrant);
        lblLightVibrant.setOnClickListener(this);
        lblDarkVibrant = (TextView) findViewById(R.id.lblDarkVibrant);
        lblDarkVibrant.setOnClickListener(this);
        lblMuted = (TextView) findViewById(R.id.lblMuted);
        lblMuted.setOnClickListener(this);
        lblLightMuted = (TextView) findViewById(R.id.lblLightMuted);
        lblLightMuted.setOnClickListener(this);
        lblDarkMuted = (TextView) findViewById(R.id.lblDarkMuted);
        lblDarkMuted.setOnClickListener(this);
        // Se carga una foto.
        cargarFoto();
    }

    // Carga una foto desde Internet en el ImageView.
    private void cargarFoto() {
        Picasso.with(this).load(URL_FOTO + (mContador % 10 + 1) + "/")
                .into(imgFoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        obtenerPaleta();
                        mContador++;
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

    // Obtiene la paleta de colores a partir de la imagen.
    private void obtenerPaleta() {
        Bitmap bitmap = ((BitmapDrawable) imgFoto.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // La paleta de 16 colores ya ha sido generada asíncronamente.
                mPaleta = palette;
                pintarMuestras(palette);
            }
        });
    }

    // Pinta las muestras de color a partir de la paleta.
    private void pintarMuestras(Palette palette) {
        pintarMuestra(lblVibrant, palette.getVibrantSwatch());
        pintarMuestra(lblLightVibrant, palette.getLightVibrantSwatch());
        pintarMuestra(lblDarkVibrant, palette.getDarkVibrantSwatch());
        pintarMuestra(lblMuted, palette.getMutedSwatch());
        pintarMuestra(lblLightMuted, palette.getLightMutedSwatch());
        pintarMuestra(lblDarkMuted, palette.getDarkMutedSwatch());
        // Se actualiza la toolbar con la muestra Muted.
        actualizarToolbar(lblMuted);
    }

    // Pinta en el TextView la muestra de color recibida.
    private void pintarMuestra(TextView lbl, Palette.Swatch swatch) {
        lbl.setBackgroundColor(Color.WHITE);
        lbl.setTextColor(Color.BLACK);
        if (swatch != null) {
            lbl.setBackgroundColor(swatch.getRgb());
            lbl.setTextColor(swatch.getBodyTextColor());
        }
    }

    // Se ha pulsado sobre alguna muestra de color.
    @Override
    public void onClick(View view) {
        actualizarToolbar((TextView) view);
    }

    // Actualiza los colores de la toolbar y la status bar dependiendo de la
    // muestra.
    private void actualizarToolbar(TextView lblMuestra) {
        int defaultColor = getResources().getColor(R.color.colorPrimary);
        int backgroundColor = getBackgroundColor(lblMuestra, defaultColor);
        toolbar.setBackgroundColor(backgroundColor);
        toolbar.setTitleTextColor(lblMuestra.getCurrentTextColor());
        // Por defecto el color de la status bar será el del tema.
        int statusBarColor = getResources().getColor(R.color.colorPrimaryDark);
        switch (lblMuestra.getId()) {
            case R.id.lblVibrant:
            case R.id.lblLightVibrant:
            case R.id.lblDarkVibrant:
                statusBarColor = mPaleta.getDarkVibrantColor(statusBarColor);
                break;
            default:
                statusBarColor = mPaleta.getDarkMutedColor(statusBarColor);
        }
        setStatusBarcolor(getWindow(), statusBarColor);
    }

    // Retorna el color del fondo de la vista recibida. Si el fondo no es un
    // color retorna el valor por defecto recibido.
    private int getBackgroundColor(View view, int defaultColor) {
        int color = defaultColor;
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();
        return color;
    }

    // Establece el color de fondo de la status bar (API > 21).
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setStatusBarcolor(Window window, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
    }

}
