package es.iessaladillo.pedrojoya.pr107;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Adaptador para la lista de alumnos.
public class AlumnosAdapter extends RecyclerViewAdapter<Alumno, AlumnosAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Alumno> datos;
    private final LayoutInflater inflater;
    private View emptyView;

    public AlumnosAdapter(Context context, ArrayList<Alumno> datos) {
        setHasStableIds(true);
        this.context = context;
        this.datos = datos;
        // Se obtiene un inflador de layouts.
        inflater = LayoutInflater.from(context);
    }

    // Cuando se debe crear una nueva vista para el elemento.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Se infla la especificación XML para obtener la vista-fila.
        View view = inflater.inflate(R.layout.activity_main_item, parent, false);
        // Se crea y retorna el contenedor de vistas para la fila.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlumnosAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        // Se obtiene el alumno correspondiente.
        Alumno alumno = datos.get(position);
        // Se escriben los datos en la vista.
        holder.lblNombre.setText(alumno.getNombre());
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= datos.size()) {
            return -1;
        }
        return datos.get(position).getId();
    }

    public Alumno getItem(int position) {
        return datos.get(position);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void removeItem(int position) {
        datos.remove(position);
        notifyItemRemoved(position);
        checkIfEmpty();
    }

    public void removeSelectedItems() {
        List<Integer> seleccionados = getSelectedItemsPositions();
        Collections.sort(seleccionados, Collections.reverseOrder());
        for (int i = 0; i < seleccionados.size(); i++) {
            int pos = seleccionados.get(i);
            toggleSelection(pos);
            removeItem(pos);
        }
    }

    public void addItem(Alumno alumno) {
        datos.add(alumno);
        notifyItemInserted(datos.size() - 1);
        checkIfEmpty();
    }

    public void swapPositions(int from, int to) {
        Collections.swap(datos, from, to);
    }

    void checkIfEmpty() {
        if (emptyView != null) {
            emptyView.setVisibility(getItemCount() > 0 ? View.GONE : View.VISIBLE);
        }
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        TextView lblNombre;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            super(itemView);
            // Obtenemos las vistas de la vista fila.
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
        }

    }

}
