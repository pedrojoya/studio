package pedrojoya.iessaladillo.es.pr106;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

// Adaptador para la lista de alumnos.
public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.ViewHolder> {

    private final ArrayList<Alumno> mDatos;
    private View emptyView;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;

    // Constructor.
    public AlumnosAdapter(ArrayList<Alumno> datos) {
        mDatos = datos;
    }

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
        // Cuando se pulsa sobre bajar.
        viewHolder.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se mueve el alumno una posición hacia abajo.
                int position = viewHolder.getAdapterPosition();
                if (position < mDatos.size() - 1) {
                    swapItems(position, position + 1);
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
        // Se muestran u ocultan el botón de movimiento.
        holder.btnDown.setVisibility(position < mDatos.size() - 1 ?
                View.VISIBLE : View.GONE);

    }

    // Retorna el número de ítems gestionados.
    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    // Elimina un elemento de la lista.
    public void removeItem(int position) {
        mDatos.remove(position);
        notifyItemRemoved(position);
/*
        if (position > 0) {
            notifyItemChanged(position - 1);
        }
        if (position == 0 && mDatos.size() > 0) {
            notifyItemChanged(0);
        }
*/      // Si aún hay mDatos y se ha eliminado el último elemento, se notifica que ha habido cambios
        // en el elemento anterior para que se oculte la flecha.
        if (mDatos.size() > 0 && position == mDatos.size()) {
            notifyItemChanged(position - 1);
        }
        // Si la lista ha quedado vacía se muestra la empty view.
        checkIfEmpty();
    }

    // Añade un elemento a la lista.
    public void addItem(Alumno alumno) {
        // Se añade el elemento.
        mDatos.add(alumno);
        // Se notifica que se ha insertado un elemento en la última posición.
        notifyItemInserted(mDatos.size() - 1);
        // Se notifica que ha habido cambios en el elemento anterior, para que se muestre la flecha.
        if (mDatos.size() > 1) {
            notifyItemChanged(mDatos.size() - 2);
        }
        // Si la lista estaba vacía se oculta la empty view.
        checkIfEmpty();
    }

    // Intercambia dos elementos de la lista.
    void swapItems(int from, int to) {
        // Se realiza el intercambio.
        Alumno alumnoTo = mDatos.get(to);
        mDatos.set(to, mDatos.get(from));
        mDatos.set(from, alumnoTo);
        // Se notifica el movimiento.
        notifyItemMoved(from, to);
        // Si en el intercambio ha participado el último elemento se notifica que ha habido
        // cambios en los elementos para que se muestren u oculten adecuadamente las flechas.
        if (from == mDatos.size() - 1 || to == mDatos.size() - 1) {
            notifyItemChanged(from);
            notifyItemChanged(to);
        }
    }

    // Comprueba si la lista está vacía.
    void checkIfEmpty() {
        if (emptyView != null) {
            // Muestra u oculta la empty view dependiendo de si la lista está vacía o no.
            emptyView.setVisibility(getItemCount() > 0 ? View.GONE : View.VISIBLE);
        }
    }

    // Establece la empty view para la lista.
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        // Muestra la empty view si  la lista está vacía.
        checkIfEmpty();
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
        private final ImageButton btnDown;


        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            super(itemView);
            // Se obtienen las vistas de la vista-fila.
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            lblDireccion = (TextView) itemView.findViewById(R.id.lblDireccion);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            btnDown = (ImageButton) itemView.findViewById(R.id.btnDown);
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
