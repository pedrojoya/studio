package es.iessaladillo.pedrojoya.pr084;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

// GridView que solicita más datos al hacer scroll hasta el final.
public class EndlessGridView extends GridView implements OnScrollListener {

    private LoadAgent listener;
    private boolean isLoading;

    // Interfaz que debe implementar el objeto que cargará los datos.
    public interface LoadAgent {
        // Método que será llamado para cargar más datos.
        void loadData();
    }

    // Constructores.
    public EndlessGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
    }

    public EndlessGridView(Context context) {
        super(context);
        this.setOnScrollListener(this);
    }

    public EndlessGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnScrollListener(this);
    }

    // Cuando se hace scroll.
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        if (getAdapter() == null)
            return;

        if (getAdapter().getCount() == 0)
            return;

        // Si se ha llegado al final del scroll y no se está cargando ya.
        int l = visibleItemCount + firstVisibleItem;
        if (l >= totalItemCount && !isLoading) {
            // Se cargan más datos.
            isLoading = true;
            listener.loadData();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    // Establece el objeto que cargará los datos.
    public void setLoadAgent(LoadAgent listener) {
        this.listener = listener;
    }

    // Establece el fin de la carga de datos.
    public void setLoaded() {
        isLoading = false;
    }

}