package es.iessaladillo.pedrojoya.pr084;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

// Adaptador para lista de fotos.
public class FotosAdapter extends ArrayAdapter<Foto> {

    // Variables.
    private Context contexto;
    private ArrayList<Foto> datos;
    private ImageLoader cargadorImagenes;

    // Clase interna contenedora de vistas.
    private class ContenedorVistas {
        public NetworkImageView imgFoto;
        public TextView lblDescripcion;
    }

    // Constructor.
    public FotosAdapter(Context contexto, ArrayList<Foto> datos) {
        super(contexto, R.layout.activity_main_item, datos);
        this.contexto = contexto;
        this.datos = datos;
        // Se obtiene el cargador de imágenes.
        cargadorImagenes = App.getImageLoader();
    }

    // Cuando debe visualizarse un elemento de la lista. Retorna la vista.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContenedorVistas contenedor = null;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se infla el layout.
            convertView = LayoutInflater.from(contexto).inflate(
                    R.layout.activity_main_item, parent, false);
            // Se obtienen las vistas.
            contenedor = new ContenedorVistas();
            contenedor.imgFoto = (NetworkImageView) convertView
                    .findViewById(R.id.imgFoto);
            contenedor.lblDescripcion = (TextView) convertView
                    .findViewById(R.id.lblDescripcion);
            // Se guarda el contenedor en la propiedad tag del item.
            convertView.setTag(contenedor);
        } else {
            // Si se puede reciclar, obtengo el contenedor de la vista
            // reciclada.
            contenedor = (ContenedorVistas) convertView.getTag();
        }
        // Se escriben los datos correspondientes en las vistas.
        Foto foto = datos.get(position);
        // Se le indica al NetworkImageView la URL de la foto que debe mostrar y
        // el cargador de imágenes que debe usarse para obtenerla.
        contenedor.imgFoto.setImageUrl(foto.getUrl(), cargadorImagenes);
        contenedor.lblDescripcion.setText(foto.getDescripcion());
        // Se retorna la vista que debe mostrarse para el elemento de la lista.
        return convertView;
    }

}
