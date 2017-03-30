package pedrojoya.iessaladillo.es.pr204.main.presenter;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import pedrojoya.iessaladillo.es.pr204.base.BasePresenter;
import pedrojoya.iessaladillo.es.pr204.main.usecases.AlumnosUseCase;
import pedrojoya.iessaladillo.es.pr204.main.view.MainView;
import pedrojoya.iessaladillo.es.pr204.model.entity.Alumno;

public class MainPresenter implements BasePresenter<MainView> {

    private MainView mView;
    private AlumnosUseCase mUseCase;
    CompositeDisposable mSubscripciones;

    public MainPresenter(MainView mainView, AlumnosUseCase alumnosUseCase) {
        mView = mainView;
        mUseCase = alumnosUseCase;
        mSubscripciones = new CompositeDisposable();
    }

    @Override
    public void onViewAttach(MainView view) {
        mView = view;
    }

    @Override
    public void onViewDetach() {
        mView = null;
    }

    @Override
    public void onDestroy() {
        //
    }

    @Override
    public MainView getView() {
        return mView;
    }

    public void agregarAlumno() {
        if (mView != null) {
            mView.navegarNuevoAlumno();
        }
    }

    public void actualizarAlumno(Alumno alumno) {
        if (mView != null) {
            mView.navegarAlumno(alumno);
        }
    }

    public void obtenerAlumnos() {
        Single<List<Alumno>> observable = mUseCase.getListaAlumnos();
        mSubscripciones.add(observable.subscribeWith(new DisposableSingleObserver<List<Alumno>>() {
            @Override
            public void onSuccess(List<Alumno> alumnos) {
                if (mView != null) {
                    mView.mostrarListaAlumnos(alumnos);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {
                    mView.errorObtiendoAlumnos();
                }
            }
        }));
    }

}
