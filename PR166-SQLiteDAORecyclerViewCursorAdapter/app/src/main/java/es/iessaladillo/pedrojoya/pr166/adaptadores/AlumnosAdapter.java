package es.iessaladillo.pedrojoya.pr166.adaptadores;

import android.database.Cursor;
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

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr166.R;
import es.iessaladillo.pedrojoya.pr166.bd.Instituto;

@SuppressWarnings("unused")
public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.ViewHolder> {

    private final TextDrawable.IBuilder mDrawableBuilder;

    // Interfaz que debe implementar el listener para cuando se haga click
    // sobre un elemento.
    @SuppressWarnings("UnusedParameters")
    public interface OnItemClickListener {
        void onItemClick(View view, Cursor cursor, int position);
    }

    // Interfaz que debe implementar el listener para cuando se haga click
    // largo sobre un elemento.
    @SuppressWarnings("UnusedParameters")
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Cursor cursor, int position);
    }

    // Interfaz que debe implementar el listener para cuando la lista pase a
    // o deje de estar vacía.
    public interface OnEmptyStateChangedListener {
        void onEmptyStateChanged(boolean isEmpty);
    }

    private Cursor mDatos;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemClickListener mOnItemClickListener;
    private OnEmptyStateChangedListener mOnEmptyStateChangedListener;
    private final SparseBooleanArray mSelectedItems = new SparseBooleanArray();
    private boolean mIsEmpty = true;


    // Constructor.
    public AlumnosAdapter() {
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
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_lista_alumnos_item, parent, false);
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
                            getItemAtPosition(viewHolder.getAdapterPosition()),
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
                            getItemAtPosition(viewHolder.getAdapterPosition()),
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
        // Se obtiene el alumno correspondiente y se escriben sus datos.
        if (mDatos != null) {
            mDatos.moveToPosition(position);
            String nombre = mDatos.getString(mDatos.getColumnIndexOrThrow(Instituto.Alumno.NOMBRE));
            holder.lblNombre.setText(nombre);
            holder.lblCurso.setText(
                    mDatos.getString(mDatos.getColumnIndexOrThrow(Instituto.Alumno.CURSO)));
            holder.lblDireccion.setText(
                    mDatos.getString(mDatos.getColumnIndexOrThrow(Instituto.Alumno.DIRECCION)));
            holder.itemView.setActivated(mSelectedItems.get(position, false));
            holder.imgAvatar.setImageDrawable(mDrawableBuilder.build(
                    holder.itemView.isActivated() ? "\u2713" : nombre.substring(0, 1),
                    holder.itemView.isActivated() ? Color.GRAY : ColorGenerator.MATERIAL.getColor(
                            nombre)));

        }
    }

    // Retorna el número de ítems gestionados.
    @Override
    public int getItemCount() {
        if (mDatos != null) {
            return mDatos.getCount();
        }
        return 0;
    }

    // Retorna el cursor de datos
    public Cursor getCursor() {
        return mDatos;
    }

    // Intercambia el cursor de datos actual por el nuevo recibido.
    public void swapCursor(Cursor nuevoCursor) {
        if (nuevoCursor != null) {
            if (mDatos != null) {
                mDatos.close();
            }
            mDatos = nuevoCursor;
            notifyDataSetChanged();
            checkEmptyStateChanged();
        }
    }

    public Cursor getItemAtPosition(int posicion) {
        if (mDatos != null) {
            if (mDatos.moveToPosition(posicion)) {
                return mDatos;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // Retorna si la lista está vacía.
    public boolean isEmpty() {
        return mIsEmpty;
    }

    // Comprueba si ha pasa a estar vacía o deja de estar vacía.
    private void checkEmptyStateChanged() {
        // Deja de estar vacía
        if (mIsEmpty && mDatos != null && mDatos.getCount() > 0) {
            mIsEmpty = false;
            if (mOnEmptyStateChangedListener != null) {
                mOnEmptyStateChangedListener.onEmptyStateChanged(false);
            }
        } else if (!mIsEmpty && mDatos != null && mDatos.getCount() == 0) {
            mIsEmpty = true;
            if (mOnEmptyStateChangedListener != null) {
                mOnEmptyStateChangedListener.onEmptyStateChanged(true);
            }
        }
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

    // Establece el listener a informar cuando la lista pasa a o deja de
    // estar vacía.
    public void setOnEmptyStateChangedListener(OnEmptyStateChangedListener listener) {
        mOnEmptyStateChangedListener = listener;
        checkEmptyStateChanged();
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
            imgAvatar = (ImageView) ViewCompat.requireViewById(itemView, R.id.imgAvatar);
            lblNombre = (TextView) ViewCompat.requireViewById(itemView, R.id.lblNombre);
            lblCurso = (TextView) ViewCompat.requireViewById(itemView, R.id.lblCurso);
            lblDireccion = (TextView) ViewCompat.requireViewById(itemView, R.id.lblDireccion);
        }

    }

}
