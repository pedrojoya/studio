package es.iessaladillo.pedrojoya.pr158;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

// Adaptador para la lista de alumnos.
public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.ViewHolder> {

    private final RealmResults<Alumno> mDatos;
    private final Realm mRealm;
    private View emptyView;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;

    // Constructor.
    public AlumnosAdapter(Realm realm, RealmResults<Alumno> datos) {
        mRealm = realm;
        mDatos = datos;
        // Se establece que cada item tiene un id único.
        setHasStableIds(true);
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
        // Se retorna el contenedor.
        return viewHolder;
    }

    // Cuando se deben escribir los datos en las subvistas de la
    // vista correspondiente al ítem.
    @Override
    public void onBindViewHolder(AlumnosAdapter.ViewHolder holder, int position) {
        // Se obtiene el alumno correspondiente y se escriben sus datos
        // en las vistas.
        holder.bind(mDatos.get(position));
    }

    // Retorna el número de ítems gestionados.
    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    // Retorna el id único correspondiente al elemento de una posición.
    // Debemos sobrescribirlo para que se realicen las animaciones correctamente,
    // ya que al insertar o eliminar no especificamos posición insertada o eliminada
    // porque Realm lo gestiona automáticamente.
    // Además el RecyclerView debe tener rv.setStableIds(true).
    @Override
    public long getItemId(int position) {
        // Muestra la empty view si  la lista está vacía.
        checkIfEmpty();
        return mDatos.get(position).getTimestamp();
    }

    // Elimina un elemento de la lista.
    public void removeItem(int position) {
        // Se elimina el alumno de la base de datos.
        mRealm.beginTransaction();
        mDatos.remove(position);
        mRealm.commitTransaction();
        // Se notifica al adaptador la eliminación.
        notifyItemRemoved(position);
        // Muestra la empty view si  la lista está vacía.
        checkIfEmpty();
    }

    // Añade un elemento a la lista.
    public void addItem(Alumno alumno) {
        // Se añade el alumno a la base de datos.
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(alumno);
        mRealm.commitTransaction();
        // Se indica al adaptador que han cambiado los datos (ojo NO se indoca
        // cual porque Realm no lo permite.
        notifyDataSetChanged();
        // Muestra la empty view si  la lista está vacía.
        checkIfEmpty();
    }

    // Comprueba si la lista está vacía.
    private void checkIfEmpty() {
        if (emptyView != null) {
            // Muestra u oculta la empty view dependiendo de si la lista está vacía o no.
            emptyView.setVisibility(getItemCount() > 0 ? View.GONE : View.VISIBLE);
        }
    }

    // Establece la empty view para la lista.
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
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

        // Escribe el alumno en las vistas.
        public void bind(Alumno alumno) {
            // Se escriben los mDatos en la vista.
            lblNombre.setText(alumno.getNombre());
            lblDireccion.setText(alumno.getDireccion());
            String url = alumno.getUrlFoto();
            Glide.with(imgAvatar.getContext())
                    .load(url)
                    .into(imgAvatar);
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
