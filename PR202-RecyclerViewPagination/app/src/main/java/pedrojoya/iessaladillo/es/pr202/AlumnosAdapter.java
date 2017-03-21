package pedrojoya.iessaladillo.es.pr202;

import android.support.annotation.NonNull;
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

@SuppressWarnings("unused")
class AlumnosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_LOADING = 1;

    private final ArrayList<Alumno> mDatos;
    private OnItemClickListener onItemClickListener;
    private boolean isLoadingAdded = false; // Indica si se están cargando datos.

    public AlumnosAdapter(ArrayList<Alumno> datos) {
        mDatos = datos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_ITEM:
                viewHolder = getItemVH(parent, inflater);
                break;
            default:
                viewHolder = getLoadingVH(parent, inflater);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getItemVH(ViewGroup parent, LayoutInflater inflater) {
        final RecyclerView.ViewHolder viewHolder;
        View itemView = inflater.inflate(R.layout.fragment_main_item, parent, false);
        viewHolder = new ItemVH(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    // Se informa al listener.
                    onItemClickListener.onItemClick(v, mDatos.get(viewHolder.getAdapterPosition()),
                            viewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getLoadingVH(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View itemViewLoading = inflater.inflate(R.layout.item_progress, parent, false);
        viewHolder = new LoadingVH(itemViewLoading);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            ((ItemVH) holder).bind(mDatos.get(position));
        }
        // Si el tipo es TYPE_LOADING no se "escribe" nada.

    }

    @Override
    public int getItemCount() {
        return mDatos == null ? 0 : mDatos.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Si es el último elemento cuando se está cargando el tipo es TYPE_LOADING.
        return (position == mDatos.size() - 1 && isLoadingAdded) ? TYPE_LOADING : TYPE_ITEM;
    }

    // Añade un elemento a la lista.
    @SuppressWarnings("WeakerAccess")
    public void addItem(Alumno alumno) {
        // Se añade el elemento.
        mDatos.add(alumno);
        // Se notifica que se ha insertado un elemento en la última posición.
        notifyItemInserted(mDatos.size() - 1);
    }

    // Añade una lista de elemento a la lista.
    public void addAll(List<Alumno> list) {
        for (Alumno alumno : list) {
            addItem(alumno);
        }
    }

    // Intercambia dos elementos de la lista.
    @SuppressWarnings("unused")
    void swapItems(int from, int to) {
        // Se realiza el intercambio.
        Collections.swap(mDatos, from, to);
        // Se notifica el movimiento.
        notifyItemMoved(from, to);
    }

    // Añade un elemento de "cargando".
    public void addLoadingFooter() {
        isLoadingAdded = true;
        addItem(new Alumno());
    }
    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = mDatos.size() - 1;
        Alumno item = getItem(position);
        if (item != null) {
            mDatos.remove(position);
            notifyItemRemoved(position);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public Alumno getItem(int position) {
        return mDatos.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    static class ItemVH extends RecyclerView.ViewHolder {

        private final TextView lblNombre;
        private final TextView lblDireccion;
        private final CircleImageView imgAvatar;


        public ItemVH(View itemView) {
            super(itemView);
            // Se obtienen las vistas de la vista-fila.
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            lblDireccion = (TextView) itemView.findViewById(R.id.lblDireccion);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
        }

        public void bind(Alumno alumno) {
            lblNombre.setText(alumno.getNombre());
            lblDireccion.setText(alumno.getDireccion());
            Picasso.with(imgAvatar.getContext()).load(alumno.getUrlFoto()).placeholder(R
                    .drawable.ic_user).error(R.drawable.ic_user).into(imgAvatar);
        }

    }

    static class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }

    }

    @SuppressWarnings("UnusedParameters")
    public interface OnItemClickListener {
        void onItemClick(View view, Alumno alumno, int position);
    }

}
