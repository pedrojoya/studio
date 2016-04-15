package pedrojoya.iessaladillo.es.pr176;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

// Adaptador para la lista de alumnos.
public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.ViewHolder> {

    private ArrayList<Alumno> mDatos;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;

    // Cuando se debe crear una nueva vista para el elemento.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Se infla la especificación XML para obtener la vista-fila.
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false);
        // Se crea el contenedor de vistas para la fila.
        final ViewHolder viewHolder = new ViewHolder(itemView);

        // Se establecen los listener de la vista correspondiente al ítem
        // y de las subvistas.

        // Cuando se hace click sobre el elemento.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    // Se informa al listener.
                    onItemClickListener.onItemClick(v,
                            mDatos.get(viewHolder.getAdapterPosition()),
                            viewHolder.getAdapterPosition());
                }
            }
        });
        // Cuando se hace click largo sobre el elemento.
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    // Se informa al listener.
                    onItemLongClickListener.onItemLongClick(v,
                            mDatos.get(viewHolder.getAdapterPosition()),
                            viewHolder.getAdapterPosition());
                    return true;
                } else {
                    return false;
                }
            }
        });
        // Se retorna el contenedor.
        return viewHolder;
    }

    // Cuando se deben escribir los datos en las subvistas de la
    // vista correspondiente al ítem.
    @Override
    public void onBindViewHolder(AlumnosAdapter.ViewHolder holder, int position) {
        // Se obtiene el alumno correspondiente.
        Alumno alumno = mDatos.get(position);
        // Se escriben los mDatos en la vista.
        holder.lblNombre.setText(alumno.getNombre());
        holder.lblDireccion.setText(alumno.getDireccion());
        Picasso.with(holder.imgAvatar.getContext())
                .load(alumno.getUrlFoto())
                .into(holder.imgAvatar);

    }

    // Retorna el número de ítems gestionados.
    @Override
    public int getItemCount() {
        if (mDatos != null) {
            return mDatos.size();
        }
        else {
            return 0;
        }
    }

    // Establece los datos.
    public void setData(ArrayList<Alumno> data) {
        mDatos = data;
        notifyDataSetChanged();
    }

    // Elimina un elemento de la lista.
    public void removeItem(int position) {
        if (mDatos != null) {
            mDatos.remove(position);
            notifyItemRemoved(position);
        }
    }

    // Añade un elemento a la lista.
    public void addItem(Alumno alumno) {
        if (mDatos != null) {
            // Se añade el elemento.
            mDatos.add(alumno);
            // Se notifica que se ha insertado un elemento en la última posición.
            notifyItemInserted(mDatos.size() - 1);
        }
    }

    // Intercambia dos elementos de la lista.
    void swapItems(int from, int to) {
        if (mDatos != null) {
            // Se realiza el intercambio.
            Collections.swap(mDatos, from, to);
            // Se notifica el movimiento.
            notifyItemMoved(from, to);
        }
    }

    // Establece el listener a informar cuando se hace click sobre un elemento de la lista.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Establece el listener a informar cuando se hace click largo sobre un elemento de la lista.
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    // Contenedor de vistas para la vista-fila.
    static class ViewHolder extends RecyclerView.ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView lblNombre;
        private final TextView lblDireccion;
        private final CircleImageView imgAvatar;


        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            super(itemView);
            // Se obtienen las vistas de la vista-fila.
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            lblDireccion = (TextView) itemView.findViewById(R.id.lblDireccion);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
        }

    }

    // Interfaz que debe implementar el listener para cuando se haga click sobre un elemento.
    public interface OnItemClickListener {
        void onItemClick(View view, Alumno alumno, int position);
    }

    // Interfaz que debe implementar el listener para cuando se haga click largo sobre un elemento.
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Alumno alumno, int position);
    }

}
