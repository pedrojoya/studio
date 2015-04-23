package es.iessaladillo.pedrojoya.pr016;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        OnChildClickListener {

    // Variables.
    private AdaptadorAlumnos mAdaptador;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        ExpandableListView lstAlumnos = (ExpandableListView) this.findViewById(R.id.lstAlumnos);
        // No se usarán los indicadores por defecto para grupos e hijos.
        lstAlumnos.setGroupIndicator(null);
        lstAlumnos.setChildIndicator(null);
        // Se obtienen los datos.
        ArrayList<String> grupos = new ArrayList<>();
        ArrayList<ArrayList<Alumno>> hijos = new ArrayList<>();
        fillDatos(grupos, hijos);
        // Se crea el adaptador para la lista y se establece.
        mAdaptador = new AdaptadorAlumnos(this, grupos, hijos);
        lstAlumnos.setAdapter(mAdaptador);
        // Inicialmente aparecerán expandidos.
        for (int i = 0; i < mAdaptador.getGroupCount(); i++) {
            lstAlumnos.expandGroup(i);
        }
        // La actividad actuará como listener cuando se pulse un hijo de la
        // lista.
        lstAlumnos.setOnChildClickListener(this);
    }

    // Obtiene los ArrayList de datos para grupos e hijos. Modifica los
    // parámetros recibidos.
    private void fillDatos(ArrayList<String> grupos,
                           ArrayList<ArrayList<Alumno>> hijos) {
        ArrayList<Alumno> grupoActual;
        // Primer grupo.
        grupos.add("CFGM Sistemas Microinformáticos y Redes");
        grupoActual = new ArrayList<>();
        grupoActual.add(new Alumno("Baldomero", 16, "CFGM", "2º"));
        grupoActual.add(new Alumno("Sergio", 27, "CFGM", "1º"));
        grupoActual.add(new Alumno("Atanasio", 17, "CFGM", "1º"));
        grupoActual.add(new Alumno("Oswaldo", 26, "CFGM", "1º"));
        grupoActual.add(new Alumno("Rodrigo", 22, "CFGM", "2º"));
        grupoActual.add(new Alumno("Antonio", 16, "CFGM", "1º"));
        hijos.add(grupoActual);
        // Segundo grupo.
        grupos.add("CFGS Desarrollo de Aplicaciones Multiplataforma");
        grupoActual = new ArrayList<>();
        grupoActual.add(new Alumno("Pedro", 22, "CFGS", "2º"));
        grupoActual.add(new Alumno("Pablo", 22, "CFGS", "2º"));
        grupoActual.add(new Alumno("Rodolfo", 21, "CFGS", "1º"));
        grupoActual.add(new Alumno("Gervasio", 24, "CFGS", "2º"));
        grupoActual.add(new Alumno("Prudencia", 20, "CFGS", "2º"));
        grupoActual.add(new Alumno("Gumersindo", 17, "CFGS", "2º"));
        grupoActual.add(new Alumno("Gerardo", 18, "CFGS", "1º"));
        grupoActual.add(new Alumno("Óscar", 21, "CFGS", "2º"));
        hijos.add(grupoActual);
    }

    // Cuando se pulsa un hijo de la lista.
    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        // Se obtiene el hijo pulsado.
        // Si tuvieramos que obtener el adaptador se debe usar el método
        // getExpandableListAdapter() de la lista y NO getAdapter().
        Alumno alumno = mAdaptador.getChild(groupPosition, childPosition);
        Toast.makeText(
                this,
                alumno.getNombre() + " (" + alumno.getCurso() + " "
                        + alumno.getCiclo() + ")", Toast.LENGTH_SHORT).show();
        // Se retorna true para indicar que el evento ya ha sido gestionado.
        return true;
    }

    // Clase adaptador de la lista de alumnos.
    private class AdaptadorAlumnos extends BaseExpandableListAdapter {

        // Variables miembro.
        private final ArrayList<String> mGrupos; // Nombres de los grupos.
        private final ArrayList<ArrayList<Alumno>> mHijos; // Alumnos por grupo.
        private final LayoutInflater mInflador;
        private final int mColorMenorDeEdad;
        private final int mColorMayorDeEdad;

        // Clase interna privada contenedor de vistas de hijo.
        private class ContenedorVistasHijo {
            TextView lblNombre;
            TextView lblCurso;
        }

        // Clase interna privada contenedor de vistas de grupo.
        private class ContenedorVistasGrupo {
            TextView lblEncCiclo;
            ImageView imgIndicador;
            LinearLayout llEncColumnas;
        }

        // Constructor.
        public AdaptadorAlumnos(Context contexto, ArrayList<String> grupos,
                                ArrayList<ArrayList<Alumno>> alumnos) {
            this.mGrupos = grupos;
            this.mHijos = alumnos;
            // Se obtiene un inflador de layouts.
            mInflador = LayoutInflater.from(contexto);
            // Se obtienen los colores de menor y mayor de edad.
            Resources recursos = contexto.getResources();
            mColorMenorDeEdad = recursos.getColor(R.color.primary_400);
            mColorMayorDeEdad = recursos.getColor(R.color.primary_text);
        }

        // Retorna el objeto de datos de un hijo de un grupo.
        @Override
        public Alumno getChild(int posGrupo, int posHijo) {
            return mHijos.get(posGrupo).get(posHijo);
        }

        // Retorna el id de un hijo de un grupo
        @Override
        public long getChildId(int posGrupo, int posHijo) {
            // No gestionamos los ids.
            return 0;
        }

        // Cuando se va a pintar un hijo de un grupo.
        @Override
        public View getChildView(int posGrupo, int posHijo,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ContenedorVistasHijo contenedor;
            // Si no se puede reciclar.
            if (convertView == null) {
                // Se infla el layout para el hijo.
                convertView = mInflador.inflate(R.layout.activity_main_hijo,
                        parent, false);
                // Se obtienen las vistas y se almacenan en el contenedor.
                contenedor = new ContenedorVistasHijo();
                contenedor.lblNombre = (TextView) convertView
                        .findViewById(R.id.lblNombre);
                contenedor.lblCurso = (TextView) convertView
                        .findViewById(R.id.lblCurso);
                // El contenedor se almacena en el tag del hijo.
                convertView.setTag(contenedor);
            } else {
                // Si se recicla, se obtiene el contenedor desde el tag del
                // hijo.
                contenedor = (ContenedorVistasHijo) convertView.getTag();
            }
            // Se escriben los valores en las vistas.
            Alumno alumno = mHijos.get(posGrupo).get(posHijo);
            contenedor.lblNombre.setText(alumno.getNombre());
            contenedor.lblCurso.setText(alumno.getCurso());
            if (alumno.getEdad() < 18) {
                contenedor.lblNombre.setTextColor(mColorMenorDeEdad);
            } else {
                contenedor.lblNombre.setTextColor(mColorMayorDeEdad);
            }
            // Se retorna la vista correspondiente al hijo.
            return convertView;
        }

        // Retorna cuántos hijos tiene un grupo.
        @Override
        public int getChildrenCount(int posGrupo) {
            return mHijos.get(posGrupo).size();
        }

        // Retorna un ArrayList con todos los hijos de un grupo.
        @Override
        public ArrayList<Alumno> getGroup(int posGrupo) {
            return mHijos.get(posGrupo);
        }

        // Retorna el número de grupos.
        @Override
        public int getGroupCount() {
            return mHijos.size();
        }

        // Retorna el id de un grupo.
        @Override
        public long getGroupId(int posGrupo) {
            // No gestionamos los ids.
            return 0;
        }

        // Cuando se va a pintar el encabezado de un grupo.
        @Override
        public View getGroupView(int posGrupo, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            ContenedorVistasGrupo contenedor;
            // Si no se puede reciclar.
            if (convertView == null) {
                // Se infla del layout del grupo.
                convertView = mInflador.inflate(R.layout.activity_main_grupo,
                        parent, false);
                // Se obtienen las vistas y se almacenan en el contenedor.
                contenedor = new ContenedorVistasGrupo();
                contenedor.lblEncCiclo = (TextView) convertView
                        .findViewById(R.id.lblEncCiclo);
                contenedor.imgIndicador = (ImageView) convertView
                        .findViewById(R.id.imgIndicador);
                contenedor.llEncColumnas = (LinearLayout) convertView
                        .findViewById(R.id.llEncColumnas);
                // El contenedor se almacena en el tag de la vista grupo.
                convertView.setTag(contenedor);
            } else {
                // Si se recicla, se obtiene el contenedor desde la prop Tag de
                // la
                // vista grupo.
                contenedor = (ContenedorVistasGrupo) convertView.getTag();
            }
            // Se escriben los valores en las vistas.
            contenedor.lblEncCiclo.setText(mGrupos.get(posGrupo));
            // Si el grupo no tiene hijos se oculta el icono de despliegue y la
            // cabecera de columnas.
            if (getChildrenCount(posGrupo) == 0) {
                contenedor.imgIndicador.setVisibility(View.INVISIBLE);
                contenedor.llEncColumnas.setVisibility(View.GONE);
            } else {
                // Se hace visible el indicador de expansión.
                contenedor.imgIndicador.setVisibility(View.VISIBLE);
                // Si el grupo está expandido se muestra el icono de colapsar
                // y la cabecera de columnas.
                if (isExpanded) {
                    contenedor.imgIndicador
                            .setImageResource(R.drawable.ic_action_navigation_collapse);
                    contenedor.llEncColumnas.setVisibility(View.VISIBLE);
                } else {
                    // Si el grupo no está expandido se muestra el icono de
                    // expandir
                    // y se oculta la cabecera de columnas.
                    contenedor.imgIndicador
                            .setImageResource(R.drawable.ic_action_navigation_expand);
                    contenedor.llEncColumnas.setVisibility(View.GONE);
                }
            }
            // Se retorna la vista correspondiente al grupo.
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            // Comportamiento por defecto.
            return false;
        }

        @Override
        public boolean isChildSelectable(int posGrupo, int posHijo) {
            // Comportamiento por defecto.
            return true;
        }

        // Cuando se colapsa un grupo
        @Override
        public void onGroupCollapsed(int posGrupo) {
            // Si se desea que siempre aparezca expandida, se expande cuando se
            // trate de colapsar.
            // lstAlumnos.expandGroup(posGrupo);
        }

    }

}
