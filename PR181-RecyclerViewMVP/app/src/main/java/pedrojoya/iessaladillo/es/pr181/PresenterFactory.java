package pedrojoya.iessaladillo.es.pr181;

public interface PresenterFactory<T extends BasePresenter> {
    T create();
}
