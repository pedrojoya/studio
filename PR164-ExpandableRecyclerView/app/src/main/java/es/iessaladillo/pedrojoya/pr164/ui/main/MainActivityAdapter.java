package es.iessaladillo.pedrojoya.pr164.ui.main;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr164.data.model.Group;
import es.iessaladillo.pedrojoya.pr164.data.model.Student;
import es.iessaladillo.pedrojoya.pr164.multityperecycleradapter.Item;
import es.iessaladillo.pedrojoya.pr164.multityperecycleradapter.MultiTypeListAdapter;
import es.iessaladillo.pedrojoya.pr164.multityperecycleradapter.MultiTypeViewHolder;

public class MainActivityAdapter extends MultiTypeListAdapter<MultiTypeViewHolder> {

    public void addGroups(List<Group> groups) {
        List<Item> newList = new ArrayList<>();
        for (Group group : groups) {
            newList.add((Item) group);
            for (Student student : group.getStudents()) {
                newList.add((Item) student);
            }
        }
        submitList(newList);
    }



/*
    public MultiTypeListAdapter.Item getItem(int position) {
        return data.get(position);
    }

    // Cuando se debe crear una nueva vista para un elemento de tipo Student.
    private RecyclerView.ViewHolder onCreateAlumnoViewHolder(ViewGroup parent) {
        // Se infla el layout.
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_student, parent, false);
        // Se crea el contenedor de vistas para la fila.
        final RecyclerView.ViewHolder viewHolder = new AlumnoViewHolder(itemView);
        // Cuando se hace click sobre el elemento.
        itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                // Se informa al listener.
                onItemClickListener.onItemClick(v,
                        (Student) data.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
            }
        });
        // Se retorna el contenedor.
        return viewHolder;
    }

    // Cuando se debe crear una nueva vista para un elemento de tipo Group.
    private RecyclerView.ViewHolder onCreateGrupoViewHolder(ViewGroup parent) {
        // Se infla el layout.
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_group, parent, false);
        // Se crea el contenedor de vistas para la fila.
        final RecyclerView.ViewHolder viewHolder = new GrupoViewHolder(itemView);
        // Cuando se hace click sobre el elemento.
        itemView.setOnClickListener(v -> toggleAlumnosDelGrupo((GrupoViewHolder) viewHolder));
        // Se retorna el contenedor.
        return viewHolder;
    }

    private void toggleAlumnosDelGrupo(GrupoViewHolder viewHolder) {
        int posicionGrupo = viewHolder.getAdapterPosition();
        Group group = (Group) data.get(posicionGrupo);
        // Si se trata de ocultar los hijos.
        if (group.getStudents() == null) {
            int desde = posicionGrupo + 1;
            int cuantos = 0;
            ArrayList<Student> hijosOcultos = new ArrayList<>();
            while (desde < data.size() && data.get(desde).getLayoutResIdForType() == MultiTypeListAdapter.Item.TYPE_CHILD) {
                hijosOcultos.add((Student) data.get(desde));
                data.remove(desde);
                cuantos++;
            }
            group.setHiddenChildren(hijosOcultos);
            notifyItemRangeRemoved(desde, cuantos);
            viewHolder.imgIndicador.setImageResource(R.drawable.ic_arrow_drop_down);
        } else {
            int indice = posicionGrupo + 1;
            for (Student student : group.getHiddenChildren()) {
                data.add(indice, student);
                indice++;
            }
            notifyItemRangeInserted(posicionGrupo + 1, indice - posicionGrupo - 1);
            viewHolder.imgIndicador.setImageResource(R.drawable.ic_arrow_drop_up);
            group.setHiddenChildren(null);
        }
    }
*/

}
