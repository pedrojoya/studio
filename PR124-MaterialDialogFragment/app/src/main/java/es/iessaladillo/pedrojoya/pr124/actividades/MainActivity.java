package es.iessaladillo.pedrojoya.pr124.actividades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.ListDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.IListDialogListener;
import com.avast.android.dialogs.iface.IMultiChoiceListDialogListener;
import com.avast.android.dialogs.iface.ISimpleDialogListener;

import es.iessaladillo.pedrojoya.pr124.R;
import es.iessaladillo.pedrojoya.pr124.fragmentos.AdaptadorDialogFragment;
import es.iessaladillo.pedrojoya.pr124.fragmentos.AdaptadorDialogFragment.AdaptadorDialogListener;
import es.iessaladillo.pedrojoya.pr124.fragmentos.DatePickerDialogFragment;
import es.iessaladillo.pedrojoya.pr124.fragmentos.LoginDialogFragment;
import es.iessaladillo.pedrojoya.pr124.fragmentos.SeleccionDirectaDialogFragment;
import es.iessaladillo.pedrojoya.pr124.fragmentos.SeleccionDirectaDialogFragment.SeleccionDirectaDialogListener;
import es.iessaladillo.pedrojoya.pr124.fragmentos.SeleccionMultipleDialogFragment;
import es.iessaladillo.pedrojoya.pr124.fragmentos.SeleccionMultipleDialogFragment.SeleccionMultipleDialogListener;
import es.iessaladillo.pedrojoya.pr124.fragmentos.SeleccionSimpleDialogFragment;
import es.iessaladillo.pedrojoya.pr124.fragmentos.SeleccionSimpleDialogFragment.SeleccionSimpleDialogListener;
import es.iessaladillo.pedrojoya.pr124.fragmentos.SiNoDialogFragment;
import es.iessaladillo.pedrojoya.pr124.fragmentos.SiNoDialogFragment.SiNoDialogListener;
import es.iessaladillo.pedrojoya.pr124.fragmentos.TimePickerDialogFragment;
import es.iessaladillo.pedrojoya.pr124.modelos.Album;

public class MainActivity extends ActionBarActivity implements OnDateSetListener,
        OnTimeSetListener, SiNoDialogListener, SeleccionDirectaDialogListener,
        SeleccionSimpleDialogListener, SeleccionMultipleDialogListener,
        AdaptadorDialogListener, OnClickListener, ISimpleDialogListener,
        IListDialogListener, IMultiChoiceListDialogListener {

    private static final int RC_DLG_SI_NO = 0;
    private static final int RC_DLG_SELECCION_DIRECTA = 1;
    private static final int RC_DLG_SELECCION_SIMPLE = 2;
    private static final int RC_DLG_SELECCION_MULTIPLE = 3;
    private static final int RC_DLG_LOGIN = 4;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        ((Button) findViewById(R.id.btnDatePicker)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnTimePicker)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertaSiNo)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertaSeleccionDirecta))
                .setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertaSeleccionSimple))
                .setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertaSeleccionMultiple))
                .setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertaLayout)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnAlertaAdaptador))
                .setOnClickListener(this);
    }

    // Al hacer click sobre un bot�n.
    @Override
    public void onClick(View v) {
        // Se crea una instancia del fragmento de di�logo correspondiente y se
        // muestro, especificando un tag.
        DialogFragment frgDialogo = null;
        switch (v.getId()) {
        case R.id.btnDatePicker:
            frgDialogo = new DatePickerDialogFragment();
            frgDialogo.show(getSupportFragmentManager(), "DatePickerDialogFragment");
            break;
        case R.id.btnTimePicker:
            frgDialogo = new TimePickerDialogFragment();
            frgDialogo.show(getSupportFragmentManager(), "TimePickerDialogFragment");
            break;
        case R.id.btnAlertaSiNo:
            //frgDialogo = new SiNoDialogFragment();
            SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                    .setTitle(R.string.eliminar_usuario)
                    .setMessage(R.string.esta_seguro_de_que_quiere_eliminar_el_usuario)
                    .setPositiveButtonText(R.string.si)
                    .setNegativeButtonText(R.string.no)
                    .setRequestCode(RC_DLG_SI_NO)
                    .show();
            break;
        case R.id.btnAlertaSeleccionDirecta:
//            frgDialogo = new SeleccionDirectaDialogFragment();
//            frgDialogo.show(this.getSupportFragmentManager(),
 //                   "SeleccionDirectaDialogFragment");
            ListDialogFragment.createBuilder(this, getSupportFragmentManager())
                    .setTitle(R.string.turno)
                    .setItems(R.array.turnos)
                    .setRequestCode(RC_DLG_SELECCION_DIRECTA)
                    .show();
            break;
        case R.id.btnAlertaSeleccionSimple:
//            frgDialogo = new SeleccionSimpleDialogFragment();
//            frgDialogo.show(this.getSupportFragmentManager(),
//                    "SeleccionSimpleDialogFragment");
            ListDialogFragment.createBuilder(this, getSupportFragmentManager())
                    .setTitle(R.string.turno)
                    .setItems(R.array.turnos)
                    .setRequestCode(RC_DLG_SELECCION_SIMPLE)
                    .setChoiceMode(AbsListView.CHOICE_MODE_SINGLE)
                    .setConfirmButtonText(R.string.aceptar)
                    .show();
            break;
        case R.id.btnAlertaSeleccionMultiple:
//            frgDialogo = new SeleccionMultipleDialogFragment();
//            frgDialogo.show(this.getSupportFragmentManager(),
//                    "SeleccionMultipleDialogFragment");
            ListDialogFragment.createBuilder(this, getSupportFragmentManager())
                    .setTitle(R.string.turno)
                    .setItems(R.array.turnos)
                    .setRequestCode(RC_DLG_SELECCION_MULTIPLE)
                    .setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE)
                    .setCheckedItems(new int[]{1})
                    .setConfirmButtonText(R.string.aceptar)
                    .show();
            break;
        case R.id.btnAlertaLayout:
            LoginDialogFragment.show(this, RC_DLG_LOGIN);
            break;
        case R.id.btnAlertaAdaptador:
            frgDialogo = new AdaptadorDialogFragment();
            frgDialogo.show(this.getSupportFragmentManager(),
                    "AdaptadorDialogFragment");
            break;
        }
    }

    // Al establecer la fecha. Interfaz OnDateSetListener.
    // Recibe el DatePicker que lo ha generado, el a�o, el mes num�rico y el d�a
    // del mes seleccionado.
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
            int dayOfMonth) {
        Toast.makeText(
                this,
                getString(R.string.ha_seleccionado)
                        + String.format("%02d", dayOfMonth) + "/"
                        + String.format("%02d", (monthOfYear + 1)) + "/"
                        + String.format("%04d", year), Toast.LENGTH_SHORT)
                .show();
    }

    // Al establecer la hora. Interfaz OnTimeSetListener.
    // Recibe el TimePicker que lo ha generado, la hora y los minutos.
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
        Toast.makeText(
                this,
                getString(R.string.ha_seleccionado)
                        + String.format("%02d", hourOfDay) + ":"
                        + String.format("%02d", minute), Toast.LENGTH_SHORT)
                .show();
    }

    // Al hacer click sobre el bot�n S�. Interfaz SiNoDialogListener.
    // Recibe el DialogFragment que lo ha generado.
    @Override
    public void onPositiveButtonClick(DialogFragment dialog) {
        Toast.makeText(this, getString(R.string.usuario_borrado),
                Toast.LENGTH_SHORT).show();
    }

    // Al hacer click sobre el bot�n No. Interfaz SiNoDialogListener.
    // Recibe el DialogFragment que lo ha generado.
    @Override
    public void onNegativeButtonClick(DialogFragment dialog) {
        // No hace nada.
    }

    // Al seleccionar un elemento. Interfaz SeleccionDirectaDialogListener.
    // Recibe el DialogFragment que lo ha generado y el �ndice de la opci�n
    // seleccionada.
    @Override
    public void onItemClick(DialogFragment dialog, int which) {
        String[] turnos = getResources().getStringArray(R.array.turnos);
        Toast.makeText(this,
                getString(R.string.ha_seleccionado) + turnos[which],
                Toast.LENGTH_SHORT).show();
    }

    // Al hacer click en el bot�n neutral. Interfaz
    // SeleccionSimpleDialogListener.
    // Recibe el DialogFragment que lo ha generado y el �ndice de la opci�n
    // seleccionada.
    @Override
    public void onNeutralButtonClick(DialogFragment dialog, int which) {
        String[] turnos = getResources().getStringArray(R.array.turnos);
        Toast.makeText(this,
                getString(R.string.ha_seleccionado) + turnos[which],
                Toast.LENGTH_SHORT).show();
    }

    // Al pulsar el bot�n neutral. Interfaz SeleccionMultipleDialogListener.
    // Recibe el DialogFragment que lo ha generado y un array que indica si
    // cada elemento ha sido seleccionado o no.
    @Override
    public void onNeutralButtonClick(DialogFragment dialog,
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

    // Al hacer click sobre un �lbum. Interfaz AdaptadorDialogListener.
    // Recibe el DialogFragment que lo ha generado y el album seleccionado.
    @Override
    public void onListItemClick(DialogFragment dialog, Album album) {
        Toast.makeText(this,
                getString(R.string.ha_seleccionado) + album.getNombre(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeButtonClicked(int rc) {

    }

    @Override
    public void onNeutralButtonClicked(int rc) {

    }

    @Override
    public void onPositiveButtonClicked(int rc) {
        if (rc == RC_DLG_SI_NO) {
        Toast.makeText(this, getString(R.string.usuario_borrado),
                Toast.LENGTH_SHORT).show();}
        else if (rc == RC_DLG_LOGIN){
            Toast.makeText(this, getString(R.string.conectando),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onListItemSelected(CharSequence value, int position, int rc) {
        Toast.makeText(this,
                getString(R.string.ha_seleccionado) + value,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemsSelected(CharSequence[] valores, int[] positions, int rc) {
        Toast.makeText(this, Arrays.asList(valores).toString(), Toast.LENGTH_SHORT).show();
    }
}
