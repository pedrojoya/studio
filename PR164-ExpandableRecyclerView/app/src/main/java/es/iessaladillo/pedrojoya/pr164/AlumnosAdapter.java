package es.iessaladillo.pedrojoya.pr164;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlumnosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Interfaz que debe implementar el listener para cuando se haga click sobre un elemento.
    public interface OnItemClickListener {
        void onItemClick(View view, Alumno alumno, int position);
    }

    private final ArrayList<ListItem> mDatos;
    private OnItemClickListener onItemClickListener;

    // Constructor.
    public AlumnosAdapter(ArrayList<ListItem> datos) {
        mDatos = datos;
    }

    // Retorna el tipo de ítem.
    @Override
    public int getItemViewType(int position) {
        return mDatos.get(position).getType();
    }

    // Cuando se debe crear una nueva vista para el elemento.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ListItem.TYPE_CHILD) {
            return onCreateAlumnoViewHolder(parent);
        } else {
            return onCreateGrupoViewHolder(parent);
        }
    }

    // Cuando se debe crear una nueva vista para un elemento de tipo Alumno.
    private RecyclerView.ViewHolder onCreateAlumnoViewHolder(ViewGroup parent) {
        // Se infla el layout.
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_hijo, parent, false);
        // Se crea el contenedor de vistas para la fila.
        final RecyclerView.ViewHolder viewHolder = new AlumnoViewHolder(itemView);
        // Cuando se hace click sobre el elemento.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    // Se informa al listener.
                    onItemClickListener.onItemClick(v,
                            (Alumno) mDatos.get(viewHolder.getAdapterPosition()),
                            viewHolder.getAdapterPosition());
                }
            }
        });
        // Se retorna el contenedor.
        return viewHolder;
    }

    // Cuando se debe crear una nueva vista para un elemento de tipo Grupo.
    private RecyclerView.ViewHolder onCreateGrupoViewHolder(ViewGroup parent) {
        // Se infla el layout.
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_grupo, parent, false);
        // Se crea el contenedor de vistas para la fila.
        final RecyclerView.ViewHolder viewHolder = new GrupoViewHolder(itemView);
        // Cuando se hace click sobre el elemento.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAlumnosDelGrupo((GrupoViewHolder) viewHolder);
            }
        });
        // Se retorna el contenedor.
        return viewHolder;
    }

    private void toggleAlumnosDelGrupo(GrupoViewHolder viewHolder) {
        int posicionGrupo = viewHolder.getAdapterPosition();
        Grupo grupo = (Grupo) mDatos.get(posicionGrupo);
        // Si se trata de ocultar los hijos.
        if (grupo.getHiddenChildren() == null) {
            int desde = posicionGrupo + 1;
            int cuantos = 0;
            ArrayList<Alumno> hijosOcultos = new ArrayList<>();
            while (desde < mDatos.size() && mDatos.get(desde).getType() == ListItem.TYPE_CHILD) {
                hijosOcultos.add((Alumno) mDatos.get(desde));
                mDatos.remove(desde);
                cuantos++;
            }
            grupo.setHiddenChildren(hijosOcultos);
            notifyItemRangeRemoved(desde, cuantos);
            viewHolder.imgIndicador.setImageResource(R.drawable.ic_arrow_drop_down);
        }
        // Si se trata de volver a mostrar.
        else {
            int indice = posicionGrupo + 1;
            for (Alumno alumno : grupo.getHiddenChildren()) {
                mDatos.add(indice, alumno);
                indice++;
            }
            notifyItemRangeInserted(posicionGrupo + 1, indice - posicionGrupo - 1);
            viewHolder.imgIndicador.setImageResource(R.drawable.ic_arrow_drop_up);
            grupo.setHiddenChildren(null);
        }
    }

    // Cuando se deben escribir los datos en las subvistas de la
    // vista correspondiente al ítem.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mDatos.get(position).getType() == ListItem.TYPE_CHILD) {
            ((AlumnoViewHolder) holder).onBind((Alumno) mDatos.get(position));
        } else {
            ((GrupoViewHolder) holder).onBind((Grupo) mDatos.get(position));
        }
    }

    // Retorna el número de ítems gestionados.
    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    // Establece el listener a informar cuando se hace click sobre un elemento de la lista.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public ArrayList<ListItem> getData() {
        return mDatos;
    }

    // Contenedor de vistas para la vista-fila.
    static class AlumnoViewHolder extends RecyclerView.ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView lblNombre;
        private final TextView lblCurso;

        // El constructor recibe la vista-fila.
        public AlumnoViewHolder(View itemView) {
            super(itemView);
            // Se obtienen las vistas de la vista-fila.
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            lblCurso = (TextView) itemView.findViewById(R.id.lblCurso);
        }

        public void onBind(Alumno alumno) {
            // Se escriben los datos en la vista.
            lblNombre.setText(alumno.getNombre());
            lblCurso.setText(alumno.getCurso());
        }

    }

    // Contenedor de vistas para la vista-fila.
    static class GrupoViewHolder extends RecyclerView.ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView lblEncCiclo;
        private final ImageView imgIndicador;

        // El constructor recibe la vista-fila.
        public GrupoViewHolder(View itemView) {
            super(itemView);
            // Se obtienen las vistas de la vista-fila.
            lblEncCiclo = (TextView) itemView.findViewById(R.id.lblEncCiclo);
            imgIndicador = (ImageView) itemView.findViewById(R.id.imgIndicador);
        }

        public void onBind(Grupo grupo) {
            // Se escriben los datos en la vista.
            lblEncCiclo.setText(grupo.getNombre());
            imgIndicador.setImageResource(
                    grupo.getHiddenChildren()==null?
                            R.drawable.ic_arrow_drop_up:
                            R.drawable.ic_arrow_drop_down);
        }

    }

}