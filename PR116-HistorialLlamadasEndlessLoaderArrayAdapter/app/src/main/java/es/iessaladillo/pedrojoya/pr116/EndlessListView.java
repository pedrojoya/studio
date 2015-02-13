package es.iessaladillo.pedrojoya.pr116;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

public class EndlessListView extends ListView implements AbsListView.OnScrollListener {

    // Interfaz que debe implementar el objeto que cargará los datos.
    public interface LoadAgent {
        // Método que será llamado para cargar más datos.
        public void loadData();
    }

    // Listener para cuando haya que cargar más datos.
    private LoadAgent mListener;
    // Flag de si se está cargando en la actualidad.
    private boolean mIsLoading;

    // Constructores.
    public EndlessListView(Context context) {
        super(context);
        // El propio objeto actuará como listener del scroll del ListView.
        setOnScrollListener(this);
    }

    public EndlessListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // El propio objeto actuará como listener del scroll del ListView.
        setOnScrollListener(this);
    }

    public EndlessListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // El propio objeto actuará como listener del scroll del ListView.
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    // Cuando se hace scroll.
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        // Si no tiene adaptador o no tiene datos, no se hace nada.
        if (getAdapter() == null || getAdapter().getCount() == 0)
            return;

        // Si se ha llegado al final del scroll y no se está cargando ya.
        int l = visibleItemCount + firstVisibleItem;
        if (l >= totalItemCount && !mIsLoading) {
            if (mListener != null) {
                // Se cargan más datos.
                mIsLoading = true;
                mListener.loadData();
            }
        }
    }

    // Establece el objeto que cargará los datos.
    public void setLoadAgent(LoadAgent listener) {
        this.mListener = listener;
    }

    // Establece el fin de la carga de datos.
    public void setLoaded() {
        mIsLoading = false;
    }

}
