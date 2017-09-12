package es.iessaladillo.pedrojoya.pr095.ui.main;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr095.R;
import es.iessaladillo.pedrojoya.pr095.data.model.Song;

// Adaptador para la lista.
public class MainFragmentAdapter extends ArrayAdapter<Song> {

    private final ArrayList<Song> mCanciones;
    private final LayoutInflater mInflador;
    private final ListView mListView;

    // Constructor.
    public MainFragmentAdapter(Context contexto, ArrayList<Song> canciones, ListView lst) {
        super(contexto, R.layout.fragment_main_item, canciones);
        mCanciones = canciones;
        mInflador = LayoutInflater.from(contexto);
        mListView = lst;
    }

    // Retorna la vista que se debe "dibujar" para un determinado elemento.
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se obtiene la vista-fila inflando el layout.
            convertView = mInflador.inflate(R.layout.fragment_main_item, parent, false);
            // Se crea el contenedor de vistas para la vista-fila.
            holder = new ViewHolder(convertView);
            // Se almacena el contenedor en la vista.
            convertView.setTag(holder);
        }
        // Si se puede reciclar.
        else {
            // Se obtiene el contenedor de vistas desde la vista reciclada.
            holder = (ViewHolder) convertView.getTag();
        }
        // Se escriben los datos en las vistas del contenedor de vistas.
        onBindViewHolder(holder, position);
        // Se retorna la vista que representa el elemento.
        return convertView;
    }

    // Cuando se deben escribir los datos en la vista del elemento.
    private void onBindViewHolder(ViewHolder holder, int position) {
        // Se obtiene la canción que debe mostrar el elemento.
        Song song = mCanciones.get(position);
        // Se escriben los datos de la canción en las vistas.
        holder.lblNombre.setText(song.getNombre());
        holder.lblDuracion.setText(song.getDuracion());
        holder.lblAutor.setText(song.getAutor());
        // Se obtiene el icono a mostrar.
        File directory = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File fichero = new File(directory, song.getNombre()
                + MainFragment.EXTENSION_ARCHIVO);
        if (fichero.exists()) {
            holder.imgPlaying.setImageResource(
                    mListView.isItemChecked(position) ? R.drawable.ic_equalizer_black_24dp :
                            R.drawable.ic_play_circle_outline_black_24dp);
        }
        else {
            holder.imgPlaying.setImageResource(R.drawable.ic_file_download_white_24dp);
        }
    }

    // Contenedor de vistas para la vista-fila.
    static class ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView lblNombre;
        private final TextView lblDuracion;
        private final TextView lblAutor;
        private final ImageView imgPlaying;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            // Se obtienen las vistas de la vista-fila.
            lblNombre = (TextView) itemView
                    .findViewById(R.id.lblNombre);
            lblDuracion = (TextView) itemView
                    .findViewById(R.id.lblDuracion);
            lblAutor = (TextView) itemView
                    .findViewById(R.id.lblAutor);
            imgPlaying = (ImageView) itemView
                    .findViewById(R.id.imgPlaying);
        }

    }
}
