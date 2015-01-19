package pedrojoya.iessaladillo.es.pr106;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// Adaptador para la lista de alumnos.
public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Alumno> datos;
    private final LayoutInflater inflater;
    private View emptyView;

    public AlumnosAdapter(Context context, ArrayList<Alumno> datos) {
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
        // Se obtiene el alumno correspondiente.
        Alumno alumno = datos.get(position);
        // Se escriben los datos en la vista.
        holder.lblNombre.setText(alumno.getNombre());
        // Se muestran u ocultan los botones de movimiento.
        holder.btnDown.setVisibility(position < datos.size() - 1 ? View.VISIBLE : View.GONE);
        holder.btnUp.setVisibility(position > 0 ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void removeItem(int position) {
        datos.remove(position);
        notifyItemRemoved(position);
        if (position > 0) {
            notifyItemChanged(position - 1);
        }
        if (position == 0 && datos.size() > 0) {
            notifyItemChanged(0);
        }
        checkIfEmpty();
    }

    public void addItem(Alumno alumno) {
        datos.add(alumno);
        notifyItemInserted(datos.size() - 1);
        notifyItemChanged(datos.size() - 2);
        checkIfEmpty();
    }

    public void swapItems(int from, int to) {
        Alumno alumnoTo = datos.get(to);
        datos.set(to, datos.get(from));
        datos.set(from, alumnoTo);
        notifyItemMoved(from, to);
        notifyItemChanged(from);
        notifyItemChanged(to);
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
        ImageButton btnDown;
        ImageButton btnUp;


        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            super(itemView);
            // Obtenemos las vistas de la vista fila.
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            btnDown = (ImageButton) itemView.findViewById(R.id.btnDown);
            btnUp = (ImageButton) itemView.findViewById(R.id.btnUp);
            // Cuando se hace click sobre el elemento.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, context.getString(R.string.ha_pulsado_sobre) + datos.get(getPosition()).getNombre(), Toast.LENGTH_SHORT).show();
                }
            });
            // Cuando se hace click largo sobre el elemento.
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Se elimina el alumno.
                    removeItem(getPosition());
                    return true;
                }
            });
            // Cuando se pulsa sobre bajar.
            btnDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Se mueve el alumno una posición hacia abajo.
                    int position = getPosition();
                    if (position < datos.size() - 1) {
                        swapItems(position, position + 1);
                    }
                }
            });

            // Cuando se pulsa sobre subir.
            btnUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Se mueve el alumno una posición hacia abajo.
                    int position = getPosition();
                    if (position > 0) {
                        swapItems(position, position - 1);
                    }
                }
            });
        }

    }

}
