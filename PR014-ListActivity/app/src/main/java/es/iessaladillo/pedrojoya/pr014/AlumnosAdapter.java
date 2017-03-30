package es.iessaladillo.pedrojoya.pr014;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lucasurbas.listitemview.ListItemView;

import java.util.ArrayList;

@SuppressWarnings({"WeakerAccess"})
public class AlumnosAdapter extends ArrayAdapter<Alumno> {

    @SuppressWarnings("CanBeFinal")
    private ArrayList<Alumno> mData;
    private final LayoutInflater mInflador;

    public AlumnosAdapter(Context context, ArrayList<Alumno> data) {
        super(context, R.layout.activity_main_item, data);
        mData = data;
        mInflador = LayoutInflater.from(context);
    }

    public ArrayList<Alumno> getData() {
        return mData;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        // Si no puedo reciclar.
        if (convertView == null) {
            convertView = mInflador.inflate(R.layout.activity_main_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bind(mData.get(position));
        return convertView;
    }

    private class ViewHolder {

        private static final int MAYORIA_EDAD = 18;

        final ListItemView listItemView;

        ViewHolder(final View itemView) {
            listItemView = (ListItemView) itemView;

        }

        void bind(final Alumno alumno) {
            listItemView.setTitle(alumno.getNombre());
            listItemView.setSubtitle(alumno.getCurso() + " " + alumno.getCiclo());
            listItemView.setIconColor(alumno.getEdad() < MAYORIA_EDAD ? ContextCompat.getColor(
                    listItemView.getContext(), R.color.accent) : ContextCompat.getColor(
                    listItemView.getContext(), R.color.primary));
            listItemView.setOnMenuItemClickListener(new ListItemView.OnMenuItemClickListener() {
                @Override
                public void onActionMenuItemSelected(final MenuItem item) {
                    remove(alumno);
                    Toast.makeText(getContext(),
                            getContext().getString(R.string.se_ha_eliminado, alumno.getNombre()),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
