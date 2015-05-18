package es.iessaladillo.pedrojoya.pr094;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

// Adaptador para el paginador de fragmentos.
class PaginasAdapter extends FragmentStatePagerAdapter {

    // Constantes.
    private final int NUM_PAGINAS = 5;

    // Variables.
    private final int[] colores;
    private final String mTab;
    private int mScrollY;
    private Fragment[] mFragmentos = {null, null, null, null, null};

    // Constructor. Recibe el gestor de fragmentos.
    public PaginasAdapter(Context context, FragmentManager fm) {
        super(fm);
        colores = new int[]{context.getResources().getColor(R.color.color1),
                context.getResources().getColor(R.color.color2),
                context.getResources().getColor(R.color.color3),
                context.getResources().getColor(R.color.color4),
                context.getResources().getColor(R.color.color5)};
        mTab = context.getResources().getString(R.string.tab);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("Mia", "Creado fragmento " + position);
        return super.instantiateItem(container, position);
    }

    // Retorna el fragmento correspondiente a una página. Recibe el número
    // de página.
    @Override
    public Fragment getItem(int position) {
        // Se crea el fragmento y se le pasa como argumento el número de
        // pestaña para que lo escriba en su TextView.
        Fragment frg = mFragmentos[position];
        if (frg == null) {
            frg = PaginaFragment.newInstance(colores[position]);
            mFragmentos[position] = frg;
        }
        return frg;
    }

    // Retorna el número de páginas.
    @Override
    public int getCount() {
        return NUM_PAGINAS;
    }

    // Retorna el título de una página. Recibe el número de página.
    @Override
    public CharSequence getPageTitle(int position) {
        return mTab + " "
                + (position + 1);
    }
}
