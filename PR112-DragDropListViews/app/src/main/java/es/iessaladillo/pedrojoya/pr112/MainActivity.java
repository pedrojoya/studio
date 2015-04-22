package es.iessaladillo.pedrojoya.pr112;

import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemLongClickListener {

    // Modelo para los items de las listas.
    public class Item {
        final Drawable icono;
        final String texto;

        Item(Drawable icono, String texto) {
            this.icono = icono;
            this.texto = texto;
        }
    }

    // Modelo para la info de arrastre
    class DragInfo {
        final View vista;
        final Item item;

        DragInfo(View vista, Item item) {
            this.vista = vista;
            this.item = item;
        }
    }

    // Adaptador para las listas.
    public class Adaptador extends ArrayAdapter {

        // Contenedor de vistas del item.
        private class ViewHolder {
            ImageView icon;
            TextView text;
        }

        // Variables a nivel de clase.
        private final ArrayList<Item> datos;
        private final LayoutInflater inflador;

        // Constructor.
        Adaptador(Context context, ArrayList<Item> datos) {
            super(context, 0, datos);
            this.inflador = LayoutInflater.from(context);
            this.datos = datos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder contenedor;
            if (convertView == null) {
                convertView = inflador.inflate(R.layout.activity_main_item, parent, false);
                contenedor = new ViewHolder();
                contenedor.icon = (ImageView) convertView.findViewById(R.id.imgIcono);
                contenedor.text = (TextView) convertView.findViewById(R.id.lblTexto);
                convertView.setTag(contenedor);
            } else {
                contenedor = (ViewHolder) convertView.getTag();
            }
            Item elemento = datos.get(position);
            contenedor.icon.setImageDrawable(elemento.icono);
            contenedor.text.setText(elemento.texto);
            // La vista raíz del ítem será posible destinatario de drag & drop.
            convertView.setOnDragListener(new OnItemDragListener(datos.get(position)));
            return convertView;
        }

        // Retorna el array de datos que maneja el adaptador.
        public ArrayList<Item> getDatos() {
            return datos;
        }
    }

    // Variables a nivel de clase
    private ArrayList<Item> mDatos1;
    private ArrayList<Item> mDatos2;

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();

    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        // Se crean y rellenan los ArrayList de datos.
        cargarDatos();
        // Se configuran las listas. Se les crea un listener para cuando sean destinatarias
        // de una operación de drag & drop.
        ListView mLst1 = (ListView) findViewById(R.id.lst1);
        Adaptador mAdaptador1 = new Adaptador(this, mDatos1);
        mLst1.setAdapter(mAdaptador1);
        mLst1.setOnDragListener(new OnListDragListener());
        ListView mLst2 = (ListView) findViewById(R.id.lst2);
        Adaptador mAdaptador2 = new Adaptador(this, mDatos2);
        mLst2.setAdapter(mAdaptador2);
        mLst2.setOnDragListener(new OnListDragListener());
        // La operación de drag & drop se iniciará al hacer click largo sobre un elemento
        // de las listas.
        mLst1.setOnItemLongClickListener(this);
        mLst2.setOnItemLongClickListener(this);
    }

    // Cuando se hace click largo en un item de una lista.
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // Se crea el objeto de información para la operación de drag & drop.
        Item item = (Item) (parent.getItemAtPosition(position));
        DragInfo dragInfo = new DragInfo(view, item);
        // Se inicia la operación de drag & drop.
        ClipData data = ClipData.newPlainText("", "");
        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(data, shadowBuilder, dragInfo, 0);
        return true;
    }


    private class OnListDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    // Se cambia el color de fondo de la vista candidata.
                    Log.d(getString(R.string.app_name), "Lista - ACTION_DRAG_ENTERED");
                    v.setBackgroundColor(getResources().getColor(R.color.list_background_candidato));
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d(getString(R.string.app_name), "Lista - ACTION_DRAG_EXITED");
                    // Se resetea el color de fondo de la vista candidata.
                    v.setBackgroundColor(getResources().getColor(android.R.color.white));
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d(getString(R.string.app_name), "Lista - ACTION_DRAG_DROP");
                    // Se obtienen los datos contenidos en el objeto de información de la operación.
                    DragInfo dragInfo = (DragInfo) event.getLocalState();
                    View vistaOrigen = dragInfo.vista;
                    Item itemOrigen = dragInfo.item;
                    // Se obtienen las listas y adaptadores de origen y de destino.
                    ListView lstOrigen = (ListView) vistaOrigen.getParent();
                    if (lstOrigen == null) {
                        return false;
                    }
                    Adaptador adaptadorOrigen = (Adaptador) (lstOrigen.getAdapter());
                    ListView lstDestino = (ListView) v;
                    Adaptador adaptadorDestino = (Adaptador) (lstDestino.getAdapter());
                    // Se elimina el item de la lista de origen y se añade al final de la lista
                    // de destino.
                    adaptadorOrigen.remove(itemOrigen);
                    adaptadorDestino.add(itemOrigen);
                    // Se hace scroll hasta el final.
                    lstDestino.smoothScrollToPosition(adaptadorDestino.getCount() - 1);
                    // Se resetea el color de fondo de la vista destinataria.
                    v.setBackgroundColor(getResources().getColor(android.R.color.white));
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(getString(R.string.app_name), "Lista - ACTION_DRAG_ENDED");
                    // Se resetea el color de fondo de la vista destinataria.
                    v.setBackgroundColor(getResources().getColor(android.R.color.white));
                    break;
                default:
                    break;
            }
            return true;
        }

    }

    // Listener de arrastre de un item de una lista.
    class OnItemDragListener implements OnDragListener {

        final Item item;

        OnItemDragListener(Item item) {
            this.item = item;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    // Se cambia el color de fondo de la vista candidata (si no es la propia
                    // que estamos arrastrando.
                    DragInfo di = (DragInfo) event.getLocalState();
                    if (di.vista != v) {
                        v.setBackgroundColor(getResources().getColor(R.color.item_background_candidato));
                    }
                    // Si es necesario hacer scroll, se hace.
                    final ListView lstC = (ListView) v.getParent();
                    int posCandidata = lstC.getPositionForView(v);
                    if (posCandidata == lstC.getLastVisiblePosition()) {
                        int scrollTo = posCandidata + 1;
                        if (scrollTo < lstC.getCount()) {
                            lstC.smoothScrollToPosition(scrollTo);
                        }
                    } else if (posCandidata == lstC.getFirstVisiblePosition()) {
                        int scrollTo = posCandidata - 1;
                        if (scrollTo >= 0) {
                            lstC.smoothScrollToPosition(scrollTo);
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    // Se resetea el color de fondo de la vista candidata.
                    v.setBackgroundColor(getResources().getColor(R.color.item_background));
                    break;
                case DragEvent.ACTION_DROP:
                    // Se obtienen los datos contenidos en el objeto de información de la operación.
                    DragInfo dragInfo = (DragInfo) event.getLocalState();
                    View vistaOrigen = dragInfo.vista;
                    Item item = dragInfo.item;
                    // Se obtienen las listas y adaptadores de origen y de destino.
                    ListView lstOrigen = (ListView) vistaOrigen.getParent();
                    Adaptador adaptadorOrigen = (Adaptador) (lstOrigen.getAdapter());
                    List<Item> datosOrigen = adaptadorOrigen.getDatos();
                    ListView lstDestino = (ListView) v.getParent();
                    Adaptador adaptadorDestino = (Adaptador) (lstDestino.getAdapter());
                    List<Item> datosAdaptadorDestino = adaptadorDestino.getDatos();
                    int posicionEliminacion = datosOrigen.indexOf(item);
                    int posicionInsercion = datosAdaptadorDestino.indexOf(this.item);

                    // Si el drop es en una lista distinta o en una posición distinta.
                    if (datosOrigen != datosAdaptadorDestino || posicionEliminacion != posicionInsercion) {
                        // Se elimina el item de la lista de origen y se añade a la de destino.
                        adaptadorOrigen.remove(item);
                        adaptadorDestino.insert(item, posicionInsercion);
                    }
                    // Se resetea el color de fondo de la vista destinataria y de la lista que
                    // lo contiene.
                    v.setBackgroundColor(getResources().getColor(R.color.item_background));
                    lstDestino.setBackgroundColor(getResources().getColor(android.R.color.white));
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // Se resetea el color de fondo de la vista candidata.
                    v.setBackgroundColor(getResources().getColor(R.color.item_background));
                    break;
                default:
                    break;
            }
            return true;
        }

    }

    // Carga los arraylist de datos.
    private void cargarDatos() {
        mDatos1 = new ArrayList<>();
        mDatos2 = new ArrayList<>();

        TypedArray arrayDrawable = getResources().obtainTypedArray(R.array.resicon);
        TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);

        for (int i = 0; i < arrayDrawable.length(); i++) {
            Drawable d = arrayDrawable.getDrawable(i);
            String s = arrayText.getString(i);
            Item item = new Item(d, s);
            mDatos1.add(item);
        }
        arrayDrawable.recycle();
        arrayText.recycle();
    }

}
