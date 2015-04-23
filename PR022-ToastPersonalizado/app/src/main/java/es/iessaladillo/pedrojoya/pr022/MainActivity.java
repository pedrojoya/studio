package es.iessaladillo.pedrojoya.pr022;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

public class MainActivity extends AppCompatActivity {

    private TextView lblTexto;

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
        (findViewById(R.id.btnToastDinamico))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        // Se muestra un toast creado dinámicamente.
                        mostrarToastDinamico(
                                R.string.toast_creado_dinamicamente,
                                R.mipmap.ic_launcher);
                    }
                });
        findViewById(R.id.btnToastLayout)
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        // Se muestra un toast creado dinámicamente.
                        mostrarToastLayout(R.string.toast_con_layout_propio,
                                R.layout.toast, R.id.lblMensaje);
                    }
                });
        findViewById(R.id.btnSnackbar)
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        cambiarVisibilidad(lblTexto);
                        mostrarSnackbar(getString(R.string.visibilidad_cambiada));
                    }
                });
    }

    // Muestra un toast creado de forma dinámica que recrea el layout
    // res/layout/transient_notification.xml de la plataforma.
    private void mostrarToastDinamico(int stringResId, int drawableResId) {
        Toast toast = new Toast(this);
        // Se crea el LinearLayout que actuará como raíz del layout del toast y
        // se establece su fondo.
        LinearLayout raiz = new LinearLayout(this);
        raiz.setBackgroundResource(R.drawable.toast_frame);
        // Se crea y configura el TextView que mostrará el mensaje.
        TextView texto = new TextView(this);
        texto.setText(stringResId);
        texto.setTextAppearance(getApplicationContext(),
                android.R.style.TextAppearance_Small);
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
        SnackbarManager.show(
                Snackbar.with(this)
                        .text(mensaje)
                        .actionLabel(getString(R.string.deshacer))
                        .actionColorResource(R.color.accent)
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                cambiarVisibilidad(lblTexto);
                            }
                        })
                        .duration(Snackbar.SnackbarDuration.LENGTH_LONG)

        );
    }


}