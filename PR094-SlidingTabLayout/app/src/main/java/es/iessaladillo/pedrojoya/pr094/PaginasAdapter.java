package es.iessaladillo.pedrojoya.pr094;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

// Adaptador para el paginador de fragmentos.
public class PaginasAdapter extends FragmentPagerAdapter {

    // Constantes.
    private int NUM_PAGINAS = 5;

    // Variables.
    Context context;

    // Constructor. Recibe el gestor de fragmentos.
    public PaginasAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    // Retorna el fragmento correspondiente a una página. Recibe el número
    // de página.
    @Override
    public Fragment getItem(int position) {
        // Se crea el fragmento y se le pasa como argumento el número de
        // pestaña para que lo escriba en su TextView.
        Fragment frgPagina = new PaginaFragment();
        Bundle parametros = new Bundle();
        parametros.putInt(PaginaFragment.PAR_NUM_PAGINA, position + 1);
        frgPagina.setArguments(parametros);
        return frgPagina;
    }

    // Retorna el número de páginas.
    @Override
    public int getCount() {
        return NUM_PAGINAS;
    }

    // Retorna el título de una página. Recibe el número de página.
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(R.string.tab) + " " + (position + 1);
    }
}
