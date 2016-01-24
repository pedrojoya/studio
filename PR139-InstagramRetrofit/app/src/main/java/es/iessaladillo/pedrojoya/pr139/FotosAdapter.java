package es.iessaladillo.pedrojoya.pr139;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FotosAdapter extends RecyclerView.Adapter<FotosAdapter
        .ViewHolder> {

    private ArrayList<Foto> mDatos;

    // Constructor. Recibe contexto y datos.
    public FotosAdapter(ArrayList<Foto> datos) {
        mDatos = datos;
    }

    // Retorna el número de ítems de datos.
    @Override
    public int getItemCount() {
        if (mDatos != null) {
            return mDatos.size();
        }
        return 0;
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
        Picasso.with(holder.itemView.getContext()).load(foto.getUrl()).into(holder.imgFoto);
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

    public ArrayList<Foto> getData() {
        return mDatos;
    }

    // Contenedor de vistas para la vista correspondiente al elemento.
    static class ViewHolder extends RecyclerView.ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView lblDescripcion;
        private final ImageView imgFoto;

        // El constructor recibe la vista correspondiente al elemento.
        public ViewHolder(View itemView) {
            // No olvidar llamar al constructor del padre.
            super(itemView);
            // Se obtienen las subvistas de la vista correspondiente al elemento.
            lblDescripcion = (TextView) itemView.findViewById(R.id.lblDescripcion);
            imgFoto = (ImageView) itemView.findViewById(R.id.imgFoto);
        }

    }

}