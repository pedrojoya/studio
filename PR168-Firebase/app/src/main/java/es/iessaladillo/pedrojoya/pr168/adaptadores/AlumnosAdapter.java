package es.iessaladillo.pedrojoya.pr168.adaptadores;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr168.R;
import es.iessaladillo.pedrojoya.pr168.modelos.Alumno;

public class AlumnosAdapter extends FirebaseRecyclerAdapter<Alumno, AlumnosAdapter.ViewHolder> {

    private final TextDrawable.IBuilder mDrawableBuilder;

    // Interfaz que debe implementar el listener para cuando se haga click
    // sobre un elemento.
    public interface OnItemClickListener {
        void onItemClick(View view, Alumno alumno, String key, int position);
    }

    // Interfaz que debe implementar el listener para cuando se haga click
    // largo sobre un elemento.
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Alumno alumno, int position);
    }

    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemClickListener mOnItemClickListener;
    private final SparseBooleanArray mSelectedItems = new SparseBooleanArray();

    public AlumnosAdapter(Firebase ref) {
        super(Alumno.class, R.layout.fragment_lista_alumnos_item, ViewHolder.class, ref);
        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                .width(100)
                .height(100)
                .toUpperCase()
                .endConfig()
                .round();
    }

    public AlumnosAdapter(Query ref) {
        super(Alumno.class, R.layout.fragment_lista_alumnos_item, ViewHolder.class, ref);
        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                .width(100)
                .height(100)
                .toUpperCase()
                .endConfig()
                .round();
    }

    // Cuando se debe crear una nueva vista para el elemento.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Se infla la especificación XML para obtener la vista-fila.
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lista_alumnos_item, parent, false);
        // Se crea el contenedor de vistas para la fila.
        final ViewHolder viewHolder = new ViewHolder(itemView);

        // Se establecen los listener de la vista correspondiente al ítem
        // y de las subvistas.

        // Cuando se hace click sobre el elemento.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    // Se informa al listener.
                    mOnItemClickListener.onItemClick(v,
                            getItem(viewHolder.getAdapterPosition()), getRef(viewHolder.getAdapterPosition()).getKey(),
                            viewHolder.getAdapterPosition());
                }
            }
        });
        // Cuando se hace click largo sobre el elemento.
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    // Se informa al listener.
                    mOnItemLongClickListener.onItemLongClick(v,
                            getItem(viewHolder.getAdapterPosition()),
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

    @Override
    protected void populateViewHolder(ViewHolder holder, Alumno alumno, int position) {
        holder.lblNombre.setText(alumno.getNombre());
        holder.lblCurso.setText(alumno.getCurso());
        holder.lblDireccion.setText(alumno.getDireccion());
        holder.itemView.setActivated(mSelectedItems.get(position, false));
        holder.imgAvatar.setImageDrawable(mDrawableBuilder.build(
                holder.itemView.isActivated() ?
                        "\u2713" :
                        alumno.getNombre().substring(0, 1),
                holder.itemView.isActivated() ?
                        Color.GRAY :
                        ColorGenerator.MATERIAL.getColor(alumno.getNombre())));
    }

    // Retorna si la lista está vacía.
    public boolean isEmpty() {
        return getItemCount() <= 0;
    }

    // Establece el listener a informar cuando se hace click sobre un
    // elemento de la lista.
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    // Establece el listener a informar cuando se hace click largo sobre un
    // elemento de la lista.
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    // Cambia el estado de selección de un elemento.
    public void toggleSelection(int position) {
        if (mSelectedItems.get(position, false)) {
            mSelectedItems.delete(position);
        } else {
            mSelectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    // Selecciona un elemento.
    public void setSelected(int position) {
        mSelectedItems.put(position, true);
        notifyItemChanged(position);

    }

    // Quita la selección de un elemento.
    public void clearSelection(int position) {
        if (mSelectedItems.get(position, false)) {
            mSelectedItems.delete(position);
        }
        notifyItemChanged(position);
    }

    // Quita la selección de todos los elementos seleccionados.
    public void clearSelections() {
        if (mSelectedItems.size() > 0) {
            mSelectedItems.clear();
            notifyDataSetChanged();
        }
    }

    // Retorna el número de elementos seleccionados.
    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    // Retorna un array con las posiciones de los elementos seleccionados.
    public ArrayList<Integer> getSelectedItemsPositions() {
        ArrayList<Integer> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mSelectedItems.keyAt(i));
        }
        return items;
    }

    // Contenedor de vistas para la vista-fila.
    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblNombre;
        private final TextView lblDireccion;
        private final ImageView imgAvatar;
        private final TextView lblCurso;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            lblNombre = (TextView) itemView
                    .findViewById(R.id.lblNombre);
            lblCurso = (TextView) itemView
                    .findViewById(R.id.lblCurso);
            lblDireccion = (TextView) itemView
                    .findViewById(R.id.lblDireccion);
        }

    }

}