package es.iessaladillo.pedrojoya.pr182.utils;

public interface BasePresenter<T extends BaseView> {

    void onViewAttached(T view);
    void onViewDetached();
    void onViewDestroyed();

}
