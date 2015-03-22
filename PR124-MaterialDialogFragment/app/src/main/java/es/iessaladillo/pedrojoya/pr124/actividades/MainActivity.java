package es.iessaladillo.pedrojoya.pr124.actividades;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.avast.android.dialogs.fragment.ListDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.IListDialogListener;
import com.avast.android.dialogs.iface.IMultiChoiceListDialogListener;
import com.avast.android.dialogs.iface.ISimpleDialogListener;

import java.util.Arrays;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import es.iessaladillo.pedrojoya.pr124.R;
import es.iessaladillo.pedrojoya.pr124.fragmentos.AdaptadorDialogFragment;
import es.iessaladillo.pedrojoya.pr124.fragmentos.AdaptadorDialogFragment.AdaptadorDialogListener;
import es.iessaladillo.pedrojoya.pr124.fragmentos.LoginDialogFragment;
import es.iessaladillo.pedrojoya.pr124.modelos.Alumno;

public class MainActivity extends ActionBarActivity implements
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        AdaptadorDialogListener,
        ISimpleDialogListener,
        IListDialogListener,
        IMultiChoiceListDialogListener {

    private static final int RC_DLG_SI_NO = 0;
    private static final int RC_DLG_SELECCION_DIRECTA = 1;
    private static final int RC_DLG_SELECCION_SIMPLE = 2;
    private static final int RC_DLG_SELECCION_MULTIPLE = 3;
    private static final int RC_DLG_LOGIN = 4;

    private static final int MODO_SELECCION_SIMPLE = 1;
    private static final int MODO_SELECCION_MULTIPLE = 2;

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
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .show(getFragmentManager(), "DatePickerDialogFragment");
    }

    // Muestra el diálogo de selección de hora.
    @OnClick(R.id.btnTimePicker)
    void mostrarDialogoTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true)
                .show(getFragmentManager(), "TimePickerDialogFragment");
    }

    // Muestra el diálogo de Si/No.
    @OnClick(R.id.btnAlertaSiNo)
    void mostrarDialogoSiNo() {
        SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle(R.string.eliminar_usuario)
                .setMessage(R.string.esta_seguro_de_que_quiere_eliminar_el_usuario)
                .setPositiveButtonText(R.string.si)
                .setNegativeButtonText(R.string.no)
                .setRequestCode(RC_DLG_SI_NO)
                .show();
    }

    // Muestra el diálogo de selección directa.
    @OnClick(R.id.btnAlertaSeleccionDirecta)
    void mostrarDialogoSeleccionDirecta() {
        ListDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle(R.string.turno)
                .setItems(R.array.turnos)
                .setRequestCode(RC_DLG_SELECCION_DIRECTA)
                .show();
    }

    // Muestra el diálogo de selección simple.
    @OnClick(R.id.btnAlertaSeleccionSimple)
    void mostrarDialogoSeleccionSimple() {
        ListDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle(R.string.turno)
                .setItems(R.array.turnos)
                .setRequestCode(RC_DLG_SELECCION_SIMPLE)
                .setChoiceMode(MODO_SELECCION_SIMPLE)
                .setConfirmButtonText(R.string.aceptar)
                .show();
    }

    // Muestra el diálogo de selección múltiple.
    @OnClick(R.id.btnAlertaSeleccionMultiple)
    void mostrarDialogoSeleccionMultiple() {
        ListDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle(R.string.turno)
                .setItems(R.array.turnos)
                .setRequestCode(RC_DLG_SELECCION_MULTIPLE)
                .setChoiceMode(MODO_SELECCION_MULTIPLE)
                .setCheckedItems(new int[]{1})
                .setConfirmButtonText(R.string.aceptar)
                .show();
    }

    // Muestra un diálogo con layout propio.
    @OnClick(R.id.btnAlertaLayout)
    void mostrarDialogoLayoutPropio() {
        LoginDialogFragment.show(this, RC_DLG_LOGIN);
    }

    // Muestra el diálogo con adaptador.
    @OnClick(R.id.btnAlertaAdaptador)
    void mostrarDialogoAdaptador() {
        DialogFragment frgDialogo;
        frgDialogo = new AdaptadorDialogFragment();
        frgDialogo.show(this.getSupportFragmentManager(),
                "AdaptadorDialogFragment");
    }

    // Al seleccionar la fecha.
    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(this, getString(R.string.ha_seleccionado)
                        + String.format("%02d", dayOfMonth) + "/"
                        + String.format("%02d", (monthOfYear + 1)) + "/"
                        + String.format("%04d", year), Toast.LENGTH_SHORT).show();
    }

    // Al seleccionar la hora.
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        Toast.makeText(this, getString(R.string.ha_seleccionado)
                        + String.format("%02d", hourOfDay) + ":"
                        + String.format("%02d", minute), Toast.LENGTH_SHORT).show();
    }

    // Al pulsar el botón negativo.
    @Override
    public void onNegativeButtonClicked(int rc) {
        Toast.makeText(this, getString(R.string.no_borrar), Toast.LENGTH_SHORT).show();
    }

    // Al pulsar el botón neutral.
    @Override
    public void onNeutralButtonClicked(int rc) {
        Toast.makeText(this, getString(R.string.neutral), Toast.LENGTH_SHORT).show();
    }

    // Al pulsar el botón positivo.
    @Override
    public void onPositiveButtonClicked(int rc) {
        // Dependiendo del diálogo que haya producido el evento.
        if (rc == RC_DLG_SI_NO) {
            Toast.makeText(this, getString(R.string.usuario_borrado),
                    Toast.LENGTH_SHORT).show();
        } else if (rc == RC_DLG_LOGIN) {
            Toast.makeText(this, getString(R.string.conectando),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Cuando se selecciona un elemento (diálogos de selección directa y selección simple).
    @Override
    public void onListItemSelected(CharSequence value, int position, int rc) {
        Toast.makeText(this,
                getString(R.string.ha_seleccionado) + value,
                Toast.LENGTH_SHORT).show();
    }

    // Cuando se seleccionan elementos (diálogo de selección múltiple).
    @Override
    public void onListItemsSelected(CharSequence[] valores, int[] positions, int rc) {
        Toast.makeText(this, Arrays.asList(valores).toString(), Toast.LENGTH_SHORT).show();
    }

    // Al hacer click sobre un alumno. Interfaz AdaptadorDialogListener.
    // Recibe el DialogFragment que lo ha generado y el alumno seleccionado.
    @Override
    public void onListItemClick(DialogFragment dialog, Alumno alumno) {
        Toast.makeText(this,
                getString(R.string.ha_seleccionado) + alumno.getNombre(),
                Toast.LENGTH_SHORT).show();
    }

}
