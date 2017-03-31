package pedrojoya.iessaladillo.es.pr204.main.presenter;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import pedrojoya.iessaladillo.es.pr204.base.BasePresenter;
import pedrojoya.iessaladillo.es.pr204.main.usecases.AlumnosUseCase;
import pedrojoya.iessaladillo.es.pr204.main.view.DummyMainView;
import pedrojoya.iessaladillo.es.pr204.main.view.MainView;
import pedrojoya.iessaladillo.es.pr204.model.entity.Alumno;

@SuppressWarnings("WeakerAccess")
public class MainPresenter implements BasePresenter<MainView> {

    private MainView mView;
    private final AlumnosUseCase mUseCase;
    final Scheduler mSubscribeOnScheduler;
    final Scheduler mObserveOnScheduler;
    final CompositeDisposable mSubscripciones;
    private final DummyMainView mDummyMainView;

    public MainPresenter(MainView mainView, AlumnosUseCase alumnosUseCase,
            Scheduler subscribeOnScheduler, Scheduler observeOnScheduler,
            CompositeDisposable subscripciones) {
        mView = mainView;
        mUseCase = alumnosUseCase;
        mSubscribeOnScheduler = subscribeOnScheduler;
        mObserveOnScheduler = observeOnScheduler;
        mSubscripciones = subscripciones;
        mDummyMainView = new DummyMainView();
    }

    @Override
    public void onViewAttach(MainView view) {
        mView = view;
    }

    @Override
    public void onViewDetach() {
        mView = mDummyMainView;
    }

    @Override
    public void onDestroy() {
        mSubscripciones.clear();
    }

    @Override
    public MainView getView() {
        return mView;
    }

    public void agregarAlumno() {
        mView.navegarNuevoAlumno();
    }

    public void actualizarAlumno(Alumno alumno) {
        mView.navegarAlumno(alumno);
    }

    public void obtenerAlumnos() {
        mSubscripciones.add(mUseCase.getListaAlumnos()
                .subscribeOn(mSubscribeOnScheduler)
                .observeOn(mObserveOnScheduler)
                .subscribe(alumnos -> mView.mostrarListaAlumnos(alumnos),
                        throwable -> mView.errorObtiendoAlumnos()));
    }

}
