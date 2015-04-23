package es.iessaladillo.pedrojoya.pr021;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class PaisesAdapter extends BaseAdapter {

    // Variables.
    private final LayoutInflater mInflador;
    private final ArrayList<Pais> mPaises;

    // Constructor.
    public PaisesAdapter(Context contexto, ArrayList<Pais> paises) {
        mPaises = paises;
        mInflador = LayoutInflater.from(contexto);
    }

    // Retorna cuántos elementos de datos maneja el adaptador.
    @Override
    public int getCount() {
        return mPaises.size();
    }

    // Obtiene un dato.
    @Override
    public Object getItem(int position) {
        return mPaises.get(position);
    }

    // Obtiene el identificador de un dato.
    @Override
    public long getItemId(int position) {
        // No gestionamos ids.
        return 0;
    }

    // Cuando se va a pintar el elemento seleccionado colapsado.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se obtiene la vista-fila inflando el layout.
            convertView = mInflador.inflate(R.layout.activity_main_pais_collapsed, parent, false);
            // Se crea el contenedor de vistas para la vista-fila.
            holder = new ViewHolder(convertView);
            // Se almacena el contenedor en la vista.
            convertView.setTag(holder);
        }
        // Si se puede reciclar.
        else {
            // Se obtiene el contenedor de vistas desde la vista reciclada.
            holder = (ViewHolder) convertView.getTag();
        }
        // Se escriben los datos en las vistas del contenedor de vistas.
        onBindViewHolder(holder, position);
        // Se retorna la vista que representa el elemento.
        return convertView;
    }

    // Cuando se deben escribir los datos en la vista del elemento.
    private void onBindViewHolder(ViewHolder holder, int position) {
        // Se obtiene el alumno que debe mostrar el elemento.
        Pais pais = mPaises.get(position);
        holder.imgBandera.setImageResource(pais.banderaResId);
        holder.lblNombre.setText(pais.getNombre());
    }

    // Contenedor de vistas para la vista-fila.
    static class ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final ImageView imgBandera;
        private final TextView lblNombre;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            // Se obtienen las vistas de la vista-fila.
            imgBandera = (ImageView) itemView
                    .findViewById(R.id.imgBandera);
            lblNombre = (TextView) itemView
                    .findViewById(R.id.lblNombre);
        }

    }

    // Cuando se de va a pintar la lista de elementos expandida.
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se obtiene la vista-fila inflando el layout.
            convertView = mInflador.inflate(R.layout.activity_main_pais_expanded, parent, false);
            // Se crea el contenedor de vistas para la vista-fila.
            holder = new ViewHolder(convertView);
            // Se almacena el contenedor en la vista.
            convertView.setTag(holder);
        }
        // Si se puede reciclar.
        else {
            // Se obtiene el contenedor de vistas desde la vista reciclada.
            holder = (ViewHolder) convertView.getTag();
        }
        // Se escriben los datos en las vistas del contenedor de vistas.
        onBindViewHolder(holder, position);
        // Se retorna la vista que representa el elemento.
        return convertView;
    }

}