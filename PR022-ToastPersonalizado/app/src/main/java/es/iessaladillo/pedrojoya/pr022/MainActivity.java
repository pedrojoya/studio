package es.iessaladillo.pedrojoya.pr022;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView lblTexto;
    private RelativeLayout rlRaiz;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lblTexto = (TextView) findViewById(R.id.lblTexto);
        rlRaiz = (RelativeLayout) findViewById(R.id.rlRaiz);
        Button btnToastDinamico = (Button) findViewById(R.id.btnToastDinamico);
        if (btnToastDinamico != null) {
            btnToastDinamico.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    // Se muestra un toast creado dinámicamente.
                    mostrarToastDinamico(
                            R.string.toast_creado_dinamicamente,
                            R.mipmap.ic_launcher);
                }
            });
        }
        Button btnToastLayout = (Button) findViewById(R.id.btnToastLayout);
        if (btnToastLayout != null) {
            btnToastLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    // Se muestra un toast creado dinámicamente.
                    mostrarToastLayout(R.string.toast_con_layout_propio,
                            R.layout.toast, R.id.lblMensaje);
                }
            });
        }
        Button btnSnackbar = (Button) findViewById(R.id.btnSnackbar);
        if (btnSnackbar != null) {
            btnSnackbar.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    cambiarVisibilidad(lblTexto);
                    mostrarSnackbar(getString(R.string.visibilidad_cambiada));
                }
            });
        }
    }

    // Muestra un toast creado de forma dinámica que recrea el layout
    // res/layout/transient_notification.xml de la plataforma.
    @SuppressWarnings("SameParameterValue")
    private void mostrarToastDinamico(int stringResId, int drawableResId) {
        Toast toast = new Toast(this);
        // Se crea el LinearLayout que actuará como raíz del layout del toast y
        // se establece su fondo.
        LinearLayout raiz = new LinearLayout(this);
        raiz.setBackgroundResource(R.drawable.toast_frame);
        // Se crea y configura el TextView que mostrará el mensaje.
        TextView texto = new TextView(this);
        texto.setText(stringResId);
        if (Build.VERSION.SDK_INT < 23) {
            //noinspection deprecation
            texto.setTextAppearance(this, android.R.style.TextAppearance_Small);
        } else {
            texto.setTextAppearance(android.R.style.TextAppearance_Small);
        }
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, 0);
        texto.setLayoutParams(params);
        texto.setGravity(Gravity.CENTER);
        texto.setShadowLayer(2.75f, 0, 0, Color.parseColor("#BB000000"));
        // Se establece el icono que se mostrará a la izquierda del TextView.
        texto.setCompoundDrawablesWithIntrinsicBounds(drawableResId, 0, 0, 0);
        texto.setCompoundDrawablePadding(10);
        // Se agrega el TextView como hijo del LinearLayout.
        raiz.addView(texto);
        // Se establece el LinearLayout como vista que debe mostrar el toast.
        toast.setView(raiz);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    // Muestra un toast con layout específico.
    @SuppressWarnings("SameParameterValue")
    private void mostrarToastLayout(int stringResId, int layoutId,
                                    int textViewId) {
        try {
            // Se infla el layout obteniendo la vista que mostrará.
            View raiz = LayoutInflater.from(this).inflate(layoutId, null);
            // Se obtiene el TextView donde debe aparecer el mensaje.
            TextView lblMensaje = (TextView) raiz.findViewById(textViewId);
            if (lblMensaje != null) {
                lblMensaje.setText(stringResId);
                Toast toast = new Toast(this);
                // Se establece la vista que debe mostrar el toast.
                toast.setView(raiz);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            } else {
                // Si hay algún problema se muestra un Toast estándar.
                Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // Si hay algún problema se muestra un Toast estándar.
            Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show();
        }
    }

    // Cambia la visibilidad de la vista recibida.
    private void cambiarVisibilidad(View view) {
        view.setVisibility(view.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    // Muestra una snackbar con el mensaje y la accion deshacer.
    private void mostrarSnackbar(String mensaje) {
        Snackbar.make(rlRaiz, mensaje, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.deshacer), new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cambiarVisibilidad(lblTexto);
                    }
                })
                .show();
    }

}