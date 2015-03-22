package es.iessaladillo.pedrojoya.pr048.actividades;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import es.iessaladillo.pedrojoya.pr048.R;
import es.iessaladillo.pedrojoya.pr048.fragmentos.AdaptadorDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.AdaptadorDialogFragment.AdaptadorDialogListener;
import es.iessaladillo.pedrojoya.pr048.fragmentos.DatePickerDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.LoginDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.LoginDialogFragment.LoginDialogListener;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SeleccionDirectaDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SeleccionDirectaDialogFragment.SeleccionDirectaDialogListener;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SeleccionMultipleDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SeleccionMultipleDialogFragment.SeleccionMultipleDialogListener;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SeleccionSimpleDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SeleccionSimpleDialogFragment.SeleccionSimpleDialogListener;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SiNoDialogFragment;
import es.iessaladillo.pedrojoya.pr048.fragmentos.SiNoDialogFragment.SiNoDialogListener;
import es.iessaladillo.pedrojoya.pr048.fragmentos.TimePickerDialogFragment;
import es.iessaladillo.pedrojoya.pr048.modelos.Alumno;

public class MainActivity extends ActionBarActivity implements OnDateSetListener,
        OnTimeSetListener, SiNoDialogListener, SeleccionDirectaDialogListener,
        SeleccionSimpleDialogListener, SeleccionMultipleDialogListener,
        AdaptadorDialogListener, LoginDialogListener {

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    // Muestra el diálogo de selección de fecha.
    @OnClick(R.id.btnDatePicker)
    void mostrarDialogoDatePicker() {
        (new DatePickerDialogFragment()).show(getSupportFragmentManager(), "DatePickerDialogFragment");
    }

    // Muestra el diálogo de selección de hora.
    @OnClick(R.id.btnTimePicker)
    void mostrarDialogoTimePicker() {
        (new TimePickerDialogFragment()).show(getSupportFragmentManager(), "TimePickerDialogFragment");
    }

    // Muestra el diálogo de confirmación Si/No.
    @OnClick(R.id.btnAlertaSiNo)
    void mostrarDialogoSiNo() {
        (new SiNoDialogFragment()).show(this.getSupportFragmentManager(), "SiNoDialogFragment");
    }

    // Muestra el diálogo de selección directa.
    @OnClick(R.id.btnAlertaSeleccionDirecta)
    void mostrarDialogoSeleccionDirecta() {
        (new SeleccionDirectaDialogFragment()).show(this.getSupportFragmentManager(),
                "SeleccionDirectaDialogFragment");
    }

    // Muestra el diálogo de selección simple.
    @OnClick(R.id.btnAlertaSeleccionSimple)
    void mostrarDialogoSeleccionSimple() {
        (new SeleccionSimpleDialogFragment()).show(this.getSupportFragmentManager(),
                "SeleccionSimpleDialogFragment");
    }

    // Muestra el diálogo de selección múltiple.
    @OnClick(R.id.btnAlertaSeleccionMultiple)
    void mostrarDialogoSeleccionMultiple() {
        (new SeleccionMultipleDialogFragment()).show(this.getSupportFragmentManager(),
                "SeleccionMultipleDialogFragment");
    }

    // Muestra el diálogo con layout propio.
    @OnClick(R.id.btnAlertaLayout)
    void mostrarDialogoLayout() {
        (new LoginDialogFragment()).show(this.getSupportFragmentManager(), "LoginDialogFragment");
    }

    // Muestra el diálogo con adaptador.
    @OnClick(R.id.btnAlertaAdaptador)
    void mostrarDialogoAdaptador() {
        (new AdaptadorDialogFragment()).show(this.getSupportFragmentManager(),
                "AdaptadorDialogFragment");
    }

    // Al establecer la fecha.
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        Toast.makeText(this, getString(R.string.ha_seleccionado)
                        + String.format("%02d", dayOfMonth) + "/"
                        + String.format("%02d", (monthOfYear + 1)) + "/"
                        + String.format("%04d", year), Toast.LENGTH_SHORT).show();
    }

    // Al establecer la hora.
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(this, getString(R.string.ha_seleccionado)
                        + String.format("%02d", hourOfDay) + ":"
                        + String.format("%02d", minute), Toast.LENGTH_SHORT).show();
    }

    // Al pulsar el botón positivo en la interfaz SiNoDialogListener.
    @Override
    public void onPositiveButtonClick(DialogFragment dialog) {
        Toast.makeText(this, getString(R.string.usuario_borrado), Toast.LENGTH_SHORT).show();
    }

    // Al pulsar el botón negativo en la interfaz SiNoDialogListener.
    @Override
    public void onNegativeButtonClick(DialogFragment dialog) {
        Toast.makeText(this, getString(R.string.no_borrar), Toast.LENGTH_SHORT).show();
    }

    // Al seleccionar un elemento en la interfaz SeleccionDirectaDialogListener.
    // Recibe el índice de la opción seleccionada.
    @Override
    public void onItemClick(DialogFragment dialog, int which) {
        String[] turnos = getResources().getStringArray(R.array.turnos);
        Toast.makeText(this,
                getString(R.string.ha_seleccionado) + turnos[which],
                Toast.LENGTH_SHORT).show();
    }

    // Al pulsar el botón positivo en la interfaz SeleccionSimpleDialogListener.
    // Recibe el índice de la opción seleccionada.
    @Override
    public void onPositiveButtonClick(DialogFragment dialog, int which) {
        String[] turnos = getResources().getStringArray(R.array.turnos);
        Toast.makeText(this, getString(R.string.ha_seleccionado) + turnos[which],
                Toast.LENGTH_SHORT).show();
    }

    // Al pulsar el botón positivo en la interfaz SeleccionMultipleDialogListener.
    // Recibe un array que indica si cada elemento ha sido seleccionado o no.
    @Override
    public void onPositiveButtonClick(DialogFragment dialog,
                                      boolean[] optionIsChecked) {
        String[] turnos = getResources().getStringArray(R.array.turnos);
        String mensaje = "";
        boolean primero = true;
        // Se construye la cadena con la lista de elementos seleccionados.
        for (int i = 0; i < optionIsChecked.length; i++) {
            if (optionIsChecked[i]) {
                if (primero) {
                    mensaje += getString(R.string.ha_seleccionado);
                    primero = false;
                } else {
                    mensaje += ", ";
                }
                mensaje += turnos[i];
            }
        }
        if (mensaje.equals("")) {
            mensaje = getString(R.string.no_ha_seleccionado_ningun_turno);
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    // Al hacer click sobre un elemento del adaptador. Recibe el alumno seleccionado.
    @Override
    public void onListItemClick(DialogFragment dialog, Alumno alumno) {
        Toast.makeText(this, getString(R.string.ha_seleccionado) + alumno.getNombre(),
                Toast.LENGTH_SHORT).show();
    }

    // Al hacer click sobre el botón Conectar del diálogo de Login.
    @Override
    public void onConectarClick(DialogFragment dialog) {
        Toast.makeText(this, "Usuario conectado", Toast.LENGTH_SHORT).show();
    }

    // Al hacer click sobre el botón Cancelar del diálogo de Login.
    @Override
    public void onCancelarClick(DialogFragment dialog) {
        // No se hace nada.
    }

}
