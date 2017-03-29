package pedrojoya.iessaladillo.es.pr204.base;

// Interfaz que debe implementar cualquier presentador.
@SuppressWarnings("EmptyMethod")
public interface BasePresenter<V> {
    void onViewAttach(V view);
    void onViewDetach();
    void onDestroy();
}
