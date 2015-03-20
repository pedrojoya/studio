package es.iessaladillo.pedrojoya.pr045;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    // Constantes.
    private static int NUM_PAGINAS = 5;

    // Variables miembro.
    private ViewPager vpPaginas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        PaginasAdapter adaptador = new PaginasAdapter();
        vpPaginas = (ViewPager) findViewById(R.id.vpPaginas);
        vpPaginas.setAdapter(adaptador);
        // Establece la página inicial a mostrar.
        vpPaginas.setCurrentItem(2);
    }

    // Adaptador de páginas para el ViewPager.
    private class PaginasAdapter extends PagerAdapter {

        // Retorna el número de páginas.
        @Override
        public int getCount() {
            return NUM_PAGINAS;
        }

        // Retorna la vista correspondiente a la página que se debe mostar.
        // Recibe la ViewPager y la posición de la página a mostrar.
        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            // Obtiene la página.
            View vistaPagina = LayoutInflater.from(MainActivity.this).inflate(
                    R.layout.pagina, collection, false);
            // Obtiene el TextView y escribe el número de página.
            TextView lblPagina = (TextView) vistaPagina
                    .findViewById(R.id.lblPagina);
            lblPagina.setText(position + "");
            // Agrega la página al ViewPager.
            collection.addView(vistaPagina, 0);
            // Retorna la vista correspondiente a la página.
            return vistaPagina;
        }

        // Quita del ViewPager la vista correspondiente a la página.
        // Recibe el ViewPager, la posición de la página y la vista-página.
        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        // Retorna si una vista-página corresponde al objeto retornado por
        // instantiateItem().
        // Recibe la vista-página y el objeto.
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        // Se llama cuando se inicia un cambio de página.
        @Override
        public void startUpdate(ViewGroup arg0) {
        }

        // Se llama cuando el cambio de página se ha completado.
        @Override
        public void finishUpdate(ViewGroup arg0) {
        }

        // Salva el estado.
        @Override
        public Parcelable saveState() {
            return null;
        }

        // Restaura el estado.
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

    }

}