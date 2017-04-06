package es.iessaladillo.pedrojoya.pr156.base;

@SuppressWarnings({"EmptyMethod", "unused"})
public interface BasePresenter<V> {
    void onViewAttach(V view);
    void onViewDetach();
    void onDestroy();
    V getView();
}
