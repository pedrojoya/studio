package es.iessaladillo.pedrojoya.pr015;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class ConceptosAdapter extends BaseAdapter {

    private final ArrayList<Concepto> datos;
    private final LayoutInflater inflador;

    public ConceptosAdapter(Context contexto, ArrayList<Concepto> datos) {
        this.datos = datos;
        // Se obtiene un objeto inflador de layouts.
        inflador = LayoutInflater.from(contexto);
    }

    // Retorna cuantos elementos de datos maneja el adaptador.
    @Override
    public int getCount() {
        // Retorno el número de elementos del ArrayList.
        return datos.size();
    }

    // Obtiene un dato.
    @Override
    public Object getItem(int position) {
        // Retorno el id de la imagen solicitada.
        return datos.get(position);
    }

    // Obtiene el identificador de un dato.
    @Override
    public long getItemId(int position) {
        // No se gestionan ids.
        return 0;
    }

    // Cuando debe dibujarse un elemento de la lista.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se obtiene la vista-fila inflando el layout.
            convertView = inflador.inflate(R.layout.activity_main_celda, parent, false);
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
        Concepto concepto = datos.get(position);
        // Se escriben los datos del alumno en las vistas.
        holder.bind(concepto);
    }

    // Contenedor de vistas para la vista-fila.
    static class ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final ImageView imgFoto;
        private final TextView lblEnglish;
        private final TextView lblSpanish;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            // Se obtienen las vistas de la vista-fila.
            imgFoto = (ImageView) itemView
                    .findViewById(R.id.imgFoto);
            lblEnglish = (TextView) itemView
                    .findViewById(R.id.lblEnglish);
            lblSpanish = (TextView) itemView
                    .findViewById(R.id.lblSpanish);
        }

        // Escribe los datos del concepto en las vistas.
        public void bind(Concepto concepto) {
            imgFoto.setImageResource(concepto.getFotoResId());
            lblEnglish.setText(concepto.getEnglish());
            lblSpanish.setText(concepto.getSpanish());
        }
    }

}