package es.iessaladillo.pedrojoya.pr184.utils.mvp;

public interface BasePresenter<T extends BaseView> {

    void onViewAttached(T view);
    void onViewDetached();
    void onViewDestroyed();

}
