package es.iessaladillo.pedrojoya.pr138;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@SuppressWarnings({"WeakerAccess", "unused", "CanBeFinal"})
public class MainActivity extends AppCompatActivity {

    private static final String URL_FOTO = "http://lorempixel.com/400/200/abstract/";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgFoto)
    ImageView imgFoto;
    @BindView(R.id.lblVibrant)
    TextView lblVibrant;
    @BindView(R.id.lblLightVibrant)
    TextView lblLightVibrant;
    @BindView(R.id.lblDarkVibrant)
    TextView lblDarkVibrant;
    @BindView(R.id.lblMuted)
    TextView lblMuted;
    @BindView(R.id.lblLightMuted)
    TextView lblLightMuted;
    @BindView(R.id.lblDarkMuted)
    TextView lblDarkMuted;

    private int mContador = 0;
    private Palette mPaleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        cargarFoto();
    }

    // Carga una foto desde Internet en el ImageView.
    @OnClick(R.id.imgFoto)
    public void cargarFoto() {
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

    // Actualiza los colores de la toolbar y la status bar dependiendo de la
    // muestra.
    @OnClick({R.id.lblVibrant, R.id.lblLightVibrant, R.id.lblDarkVibrant,
            R.id.lblMuted, R.id.lblLightMuted, R.id.lblDarkMuted})
    public void actualizarToolbar(TextView lblMuestra) {
        if (mPaleta != null) {
            int defaultColor = ContextCompat.getColor(this, R.color.colorPrimary);
            int backgroundColor = getBackgroundColor(lblMuestra, defaultColor);
            toolbar.setBackgroundColor(backgroundColor);
            toolbar.setTitleTextColor(lblMuestra.getCurrentTextColor());
            // Por defecto el color de la status bar será el del tema.
            int statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
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
