package es.iessaladillo.pedrojoya.pr112;

import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

public class MainActivity extends ActionBarActivity implements OnItemLongClickListener {

    // Modelo para los items de las listas.
    public class Item {
        Drawable icono;
        String texto;

        Item(Drawable icono, String texto) {
            this.icono = icono;
            this.texto = texto;
        }
    }

    // Modelo para la info de arrastre
    class DragInfo {
        View vista;
        Item item;
        List<Item> datos;

        DragInfo(View vista, Item item, List<Item> datos) {
            this.vista = vista;
            this.item = item;
            this.datos = datos;
        }
    }

    // Adaptador para las listas.
    public class Adaptador extends ArrayAdapter {

        // Contenedor de vistas del item.
        private class ViewHolder {
            ImageView icon;
            TextView text;
        }

        private Context context;
        private List<Item> datos;
        private final LayoutInflater inflador;

        Adaptador(Context context, List<Item> datos) {
            super(context, 0, datos);
            this.inflador = LayoutInflater.from(context);
            this.context = context;
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
        public List<Item> getDatos() {
            return datos;
        }
    }

    // Variables a nivel de clase
    List<Item> mDatos1, mDatos2;
    ListView mLst1, mLst2;
    Adaptador mAdaptador1, mAdaptador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();

    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        cargarDatos();
        mLst1 = (ListView) findViewById(R.id.lst1);
        mAdaptador1 = new Adaptador(this, mDatos1);
        mLst1.setAdapter(mAdaptador1);
        mLst1.setOnDragListener(new OnListDragListener());
        //Auto scroll to end of ListView
//        mLst1.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mLst2 = (ListView) findViewById(R.id.lst2);
        mAdaptador2 = new Adaptador(this, mDatos2);
        mLst2.setAdapter(mAdaptador2);
        mLst2.setOnDragListener(new OnListDragListener());
        mLst1.setOnItemLongClickListener(this);
        mLst2.setOnItemLongClickListener(this);
//        mLst2.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    // Cuando se hace click largo en un item de una lista.
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // Se crea el objeto de información para la operación de drag & drop.
        Item item = (Item) (parent.getItemAtPosition(position));
        Adaptador adaptador = (Adaptador) (parent.getAdapter());
        List<Item> datos = adaptador.getDatos();
        DragInfo dragInfo = new DragInfo(view, item, datos);
        // Se inicia la operación de drag & drop.
        ClipData data = ClipData.newPlainText("", "");
        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(data, shadowBuilder, dragInfo, 0);
        return true;
    }


    class OnListDragListener implements OnDragListener {

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
                    v.setBackgroundColor(getResources().getColor(android.R.color.white));
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d(getString(R.string.app_name), "Lista - ACTION_DRAG_DROP");
                    // Se obtienen los datos contenidos en el objeto de información de la operación.
                    DragInfo dragInfo = (DragInfo) event.getLocalState();
                    View vistaOrigen = dragInfo.vista;
                    Item itemOrigen = dragInfo.item;
                    List<Item> datosOrigen = dragInfo.datos;
                    // Se obtienen las listas y adaptadores de origen y de destino.
                    ListView lstOrigen = (ListView) vistaOrigen.getParent();
                    if (lstOrigen == null) {
                        return false;
                    }
                    Adaptador adaptadorOrigen = (Adaptador) (lstOrigen.getAdapter());
                    ListView lstDestino = (ListView) v;
                    Adaptador adaptadorDestino = (Adaptador) (lstDestino.getAdapter());
                    List<Item> datosAdaptadorDestino = adaptadorDestino.getDatos();
                    adaptadorOrigen.remove(itemOrigen);
                    adaptadorDestino.add(itemOrigen);
                    adaptadorOrigen.notifyDataSetChanged();
                    adaptadorDestino.notifyDataSetChanged();
                    // Se hace scroll hasta el final.
                    lstDestino.smoothScrollToPosition(adaptadorDestino.getCount() - 1);
                    v.setBackgroundColor(getResources().getColor(android.R.color.white));
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(getString(R.string.app_name), "Lista - ACTION_DRAG_ENDED");
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

        Item item;
        int colorFondoOriginal;

        OnItemDragListener(Item item) {
            this.item = item;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    // Se cambia el color de fondo de la vista candidata.
                    DragInfo di = (DragInfo) event.getLocalState();
//                    if (di.vista != v) {
                    v.setBackgroundColor(getResources().getColor(R.color.item_background_candidato));
//                    }
                    DragInfo dinfo = (DragInfo) event.getLocalState();
                    final ListView lstC = (ListView) v.getParent();
                    int posCandidata = lstC.getPositionForView(v);
                    if (posCandidata == lstC.getLastVisiblePosition()) {
                        ((ArrayAdapter) lstC.getAdapter()).notifyDataSetChanged();
                        int scrollTo = posCandidata + 1;
                        if (scrollTo < lstC.getCount()) {
                            lstC.smoothScrollToPosition(scrollTo);
                        }
                        //to scroll up
                        //test if the item entered is the first visible
                    } else if (posCandidata == lstC.getFirstVisiblePosition()) {
                        ((ArrayAdapter) lstC.getAdapter()).notifyDataSetChanged();
                        int scrollTo = posCandidata - 1;
                        if (scrollTo >= 0) {
                            lstC.smoothScrollToPosition(scrollTo);
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(getResources().getColor(R.color.item_background));
                    v.invalidate();
                    break;
                case DragEvent.ACTION_DROP:
                    // Se obtienen los datos contenidos en el objeto de información de la operación.
                    DragInfo dragInfo = (DragInfo) event.getLocalState();
                    View vistaOrigen = dragInfo.vista;
                    Item itemOrigen = dragInfo.item;
                    List<Item> datosOrigen = dragInfo.datos;
                    // Se obtienen las listas y adaptadores de origen y de destino.
                    ListView lstOrigen = (ListView) vistaOrigen.getParent();
                    Adaptador adaptadorOrigen = (Adaptador) (lstOrigen.getAdapter());
                    ListView lstDestino = (ListView) v.getParent();
                    Adaptador adaptadorDestino = (Adaptador) (lstDestino.getAdapter());
                    List<Item> datosAdaptadorDestino = adaptadorDestino.getDatos();
                    int posicionEliminacion = datosOrigen.indexOf(itemOrigen);
                    int posicionInsercion = datosAdaptadorDestino.indexOf(this.item);

                    // Si en la operación el drop se hace donde ya estaba, no se hace nada.
                    if (datosOrigen != datosAdaptadorDestino || posicionEliminacion != posicionInsercion) {
                        adaptadorOrigen.remove(itemOrigen);
                        adaptadorDestino.insert(itemOrigen, posicionInsercion);
                        adaptadorOrigen.notifyDataSetChanged();
                        adaptadorDestino.notifyDataSetChanged();

                    }
                    v.setBackgroundColor(getResources().getColor(R.color.item_background));
                    lstDestino.setBackgroundColor(getResources().getColor(android.R.color.white));
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
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
        mDatos1 = new ArrayList<Item>();
        mDatos2 = new ArrayList<Item>();

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

    // Elimina un item de una lista y retorna si ha ido bien.
    private boolean removeItemToList(List<Item> l, Item it) {
        return l.remove(it);
    }

    private boolean addItemToList(List<Item> l, Item it) {
        boolean result = l.add(it);
        return result;
    }

}
