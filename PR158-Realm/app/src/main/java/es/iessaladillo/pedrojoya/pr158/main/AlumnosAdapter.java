package es.iessaladillo.pedrojoya.pr158.main;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr158.R;
import es.iessaladillo.pedrojoya.pr158.db.entities.Alumno;
import es.iessaladillo.pedrojoya.pr158.db.entities.Asignatura;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.ViewHolder> implements
        OrderedRealmCollectionChangeListener<RealmResults<Alumno>> {

    private final RealmResults<Alumno> mDatos;
    private final Realm mRealm;
    private OnItemClickListener onItemClickListener;

    public AlumnosAdapter(Realm realm, RealmResults<Alumno> datos) {
        mRealm = realm;
        mDatos = datos;
        mDatos.addChangeListener(this);
        // Se establece que cada item tiene un id único.
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, mDatos.get(viewHolder.getAdapterPosition()),
                            viewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlumnosAdapter.ViewHolder holder, int position) {
        holder.bind(mDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    // Retorna el id único correspondiente al elemento de una posición.
    // Debemos sobrescribirlo para que se realicen las animaciones correctamente,
    // ya que al insertar o eliminar no especificamos posición insertada o eliminada
    // porque Realm lo gestiona automáticamente.
    // Además el adaptador debe tener rv.setHasStableIds(true).
    @Override
    public long getItemId(int position) {
        return mDatos.get(position).getTimestamp();
    }

    public void removeItem(int position) {
        // Se elimina el alumno de la base de datos.
        mRealm.beginTransaction();
        mDatos.deleteFromRealm(position);
        mRealm.commitTransaction();
    }

    // Establece el listener a informar cuando se hace click sobre un elemento de la lista.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onChange(RealmResults<Alumno> collection, OrderedCollectionChangeSet changeSet) {
        // `null`  means the async query returns the first time.
        if (changeSet == null) {
            notifyDataSetChanged();
            return;
        }
        // For deletions, the adapter has to be notified in reverse order.
        OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
        for (int i = deletions.length - 1; i >= 0; i--) {
            OrderedCollectionChangeSet.Range range = deletions[i];
            notifyItemRangeRemoved(range.startIndex, range.length);
        }

        OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
        for (OrderedCollectionChangeSet.Range range : insertions) {
            notifyItemRangeInserted(range.startIndex, range.length);
        }

        OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
        for (OrderedCollectionChangeSet.Range range : modifications) {
            notifyItemRangeChanged(range.startIndex, range.length);
        }
    }

    public void onDestroy() {
        mDatos.removeChangeListener(this);
    }

    @SuppressWarnings("unused")
    static class ViewHolder extends RecyclerView.ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        @BindView(R.id.lblNombre)
        public TextView lblNombre;
        @BindView(R.id.lblDireccion)
        public TextView lblDireccion;
        @BindView(R.id.imgFoto)
        public CircleImageView imgAvatar;
        @BindView(R.id.lblAsignaturas)
        public TextView lblAsignaturas;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // Escribe el alumno en las vistas.
        public void bind(Alumno alumno) {
            // Se escriben los mDatos en la vista.
            lblNombre.setText(alumno.getNombre());
            lblDireccion.setText(alumno.getDireccion());
            RealmList<Asignatura> asignaturas = alumno.getAsignaturas();
            ArrayList<String> nombresAsignaturas = new ArrayList<>();
            for (Asignatura asignatura : asignaturas) {
                nombresAsignaturas.add(asignatura.getId());
            }
            lblAsignaturas.setText(getCadenaAsignaturas(nombresAsignaturas));
            String url = alumno.getUrlFoto();
            Picasso.with(imgAvatar.getContext()).load(url).placeholder(R.drawable.ic_user).error(
                    R.drawable.ic_user).into(imgAvatar);
        }

        private String getCadenaAsignaturas(ArrayList<String> nombresAsignaturas) {
            if (nombresAsignaturas.size() > 0) {
                return TextUtils.join(", ", nombresAsignaturas);
            } else {
                return itemView.getContext().getString(R.string.ninguna);
            }
        }

    }

    // Interfaz que debe implementar el listener para cuando se haga click sobre un elemento.
    @SuppressWarnings("UnusedParameters")
    public interface OnItemClickListener {
        void onItemClick(View view, Alumno alumno, int position);
    }

}
