package es.iessaladillo.pedrojoya.pr124.adaptadores;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.pr124.R;
import es.iessaladillo.pedrojoya.pr124.modelos.Album;

// Clase adaptador del grid.
public class AdaptadorAlbumes extends BaseAdapter {

    // Variables miembro.
    private ArrayList<Album> mAlbumes; // Array de datos.
    private LayoutInflater mInflador; // Inflador de layouts.

    // Clase interna para contener las vistas.
    private class ContenedorVistas {
        ImageView imgFoto;
        TextView lblNombre;
        TextView lblAnio;
    }

    // Constructor.
    public AdaptadorAlbumes(Activity contexto, ArrayList<Album> albumes) {
        // Hago una copia de los par�metros del constructor.
        this.mAlbumes = albumes;
        // Obtengo un objeto inflador de layouts.
        mInflador = LayoutInflater.from(contexto);
    }

    // Retorna cu�ntos elementos de datos maneja el adaptador.
    @Override
    public int getCount() {
        // Retorno el n�mero de elementos del ArrayList.
        return mAlbumes.size();
    }

    // Obtiene un dato.
    @Override
    public Object getItem(int position) {
        // Retorno el id de la imagen solicitada.
        return mAlbumes.get(position);
    }

    // Obtiene el identificador de un dato.
    @Override
    public long getItemId(int position) {
        // No gestionamos ids.
        return 0;
    }

    // Cuando se va a pintar un elemento de la lista.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Variables locales.
        ContenedorVistas contenedor; // Contenedor de vistas.
        // Intento reutilizar.
        if (convertView == null) {
            // Se infla la vista-fila a partir de la especificaci�n XML.
            convertView = mInflador.inflate(R.layout.activity_main_item, parent, false);
            // Se crea el contenedor de vistas y se almacenan en el tag de la
            // vista.
            contenedor = new ContenedorVistas();
            contenedor.imgFoto = (ImageView) convertView
                    .findViewById(R.id.imgFoto);
            contenedor.lblNombre = (TextView) convertView
                    .findViewById(R.id.lblNombre);
            contenedor.lblAnio = (TextView) convertView
                    .findViewById(R.id.lblAnio);
            convertView.setTag(contenedor);
        } else {
            // Se obtiene el contenedor desde la propiedad Tag de la vista.
            contenedor = (ContenedorVistas) convertView.getTag();
        }
        // Se escriben los valores en las vistas.
        Album album = mAlbumes.get(position);
        contenedor.imgFoto.setImageResource(album.getFotoResId());
        contenedor.lblNombre.setText(album.getNombre());
        contenedor.lblAnio.setText(album.getAnio());
        // Se retorna la vista.
        return convertView;
    }

}
