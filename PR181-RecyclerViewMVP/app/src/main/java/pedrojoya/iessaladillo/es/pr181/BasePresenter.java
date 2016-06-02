package pedrojoya.iessaladillo.es.pr181;

// Interfaz que debe implementar cualquier presentador.
public interface BasePresenter<V> {
    void onViewAttach(V view);
    void onViewDetach();
    void onDestroy();
}
