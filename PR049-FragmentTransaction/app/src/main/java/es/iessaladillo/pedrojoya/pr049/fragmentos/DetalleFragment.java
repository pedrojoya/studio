package es.iessaladillo.pedrojoya.pr049.fragmentos;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr049.R;
import es.iessaladillo.pedrojoya.pr049.modelos.Obra;

public class DetalleFragment extends Fragment {

    // Interfaz para notificaci�n de eventos desde el fragmento.
    public interface OnDetalleShownListener {
        // Cuando se selecciona una obra.
        public void onDetalleShown(int position);
    }

    // Constantes.
    public static final String EXTRA_OBRA = "obra";
    public static final String EXTRA_POSITION = "position";

    // Vistas.
    private ImageView imgFoto;
    private TextView lblNombre;
    private TextView lblAutor;

    // Variables
    private Obra mObra;
    private int mPosition;
    private OnDetalleShownListener mListener;

    // Retorna una nueva instancia del fragmento configurado.
    public static DetalleFragment newInstance(Obra obra, int position) {
        DetalleFragment frgDetalle = new DetalleFragment();
        Bundle argumentos = new Bundle();
        argumentos.putParcelable(EXTRA_OBRA, obra);
        argumentos.putInt(EXTRA_POSITION, position);
        frgDetalle.setArguments(argumentos);
        return frgDetalle;
    }

    // Retorna la vista que debe mostrar el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Se infla el layout y se retorna la vista.
        return inflater.inflate(R.layout.fragment_detalle, container, false);
    }

    // Cuando se vincula el fragmento a la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // La actividad actuar� como listener cuando se seleccione una obra.
            mListener = (OnDetalleShownListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz necesaria.
            throw new ClassCastException(activity.toString()
                    + " debe implementar OnDetalleShownListener");
        }
    }

    // Cuando se desvincula el fragmento de la actividad.
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Al terminarse de crear la actividad al completo.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se obtienen e inicializan las vistas.
        initVistas();
        // Se obtiene la obra desde el bundle de parámetros.
        mObra = this.getArguments().getParcelable(EXTRA_OBRA);
        mPosition = getArguments().getInt(EXTRA_POSITION);
        // Si hay obra, se muestra.
        if (mObra != null) {
            mostrarDetalle();
        }
        super.onActivityCreated(savedInstanceState);
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        imgFoto = (ImageView) getView().findViewById(R.id.imgFoto);
        lblNombre = (TextView) getView().findViewById(R.id.lblNombre);
        lblAutor = (TextView) getView().findViewById(R.id.lblAutor);
        lblNombre.setTypeface(Typeface.createFromAsset(getActivity()
                .getAssets(), "fonts/alegreya-boldItalic.ttf"));
        lblAutor.setTypeface(Typeface.createFromAsset(
                getActivity().getAssets(), "fonts/alegreya-bold.ttf"));
    }

    // Muestra el detalle de un album en las vistas correspondientes.
    void mostrarDetalle() {
        // Escribo los datos en las vistas.
        getActivity().setTitle(mObra.getNombre());
        imgFoto.setImageResource(mObra.getFotoResId());
        lblNombre.setText(mObra.getNombre());
        lblAutor.setText(mObra.getAutor());
        // Se notifica a la actividad.
        if (mListener != null) {
            mListener.onDetalleShown(mPosition);
        }
    }
}
