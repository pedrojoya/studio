package es.iessaladillo.pedrojoya.pr084;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class FotosAdapter extends RecyclerView.Adapter<FotosAdapter
        .ViewHolder> {

    private final ArrayList<Foto> mDatos;
    private final ImageLoader cargadorImagenes;

    // Constructor. Recibe contexto y datos.
    public FotosAdapter(ArrayList<Foto> datos) {
        mDatos = datos;
        // Se obtiene el cargador de imágenes.
        cargadorImagenes = App.getImageLoader();
    }

    // Retorna el número de ítems de datos.
    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    // Cuando se debe crear una nueva vista que represente el ítem.
    // Recibe la vista padre que lo contendrá (para el inflado) y el tipo de vista
    // (por si hay varios tipos de ítems en el recyclerview).
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Se infla la especificación XML para obtener la vista correspondiente al ítem.
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false);
        // Se crea y retorna el contenedor de subvistas de la vista correspondiente
        // al ítem.
        return new ViewHolder(itemView);
    }

    // Cuando se deben escribir los datos en las subvistas de la vista correspondiente
    // al ítem. Recibe el contenedor y la posición del ítem en la colección de datos.
    @Override
    public void onBindViewHolder(FotosAdapter.ViewHolder holder, int position) {
        // Se obtiene el alumno correspondiente.
        Foto foto = mDatos.get(position);
        // Se escriben los datos en la vista.
        holder.lblDescripcion.setText(foto.getDescripcion());
        holder.imgFoto.setImageUrl(foto.getUrl(), cargadorImagenes);
    }

    // Añade un elemento al adaptador.
    public void addItem(Foto alumno, int position) {
        // Se añade el elemento.
        mDatos.add(position, alumno);
        // Se notifica que se ha insertado un elemento en la última posición.
        notifyItemInserted(position);
    }

    // Elimina un elemento del adaptador.
    public void removeItem(int position) {
        mDatos.remove(position);
        notifyItemRemoved(position);
    }

    // Elimina todos los elementos del adaptador.
    public void removeAllItems() {
        for (int i = getItemCount() - 1; i >= 0; i--) {
            removeItem(i);
        }
    }

    // Contenedor de vistas para la vista correspondiente al elemento.
    static class ViewHolder extends RecyclerView.ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView lblDescripcion;
        private final NetworkImageView imgFoto;

        // El constructor recibe la vista correspondiente al elemento.
        public ViewHolder(View itemView) {
            // No olvidar llamar al constructor del padre.
            super(itemView);
            // Se obtienen las subvistas de la vista correspondiente al elemento.
            lblDescripcion = (TextView) ViewCompat.requireViewById(itemView, R.id.lblDescripcion);
            imgFoto = (NetworkImageView) ViewCompat.requireViewById(itemView, R.id.imgFoto);
        }

    }

}