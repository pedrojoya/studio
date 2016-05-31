package pedrojoya.iessaladillo.es.pr181.main.presenter;

import java.util.List;

import pedrojoya.iessaladillo.es.pr181.BasePresenter;
import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;
import pedrojoya.iessaladillo.es.pr181.main.model.repository.DBMainRepository;
import pedrojoya.iessaladillo.es.pr181.main.model.repository.MainRepositoryCallback;
import pedrojoya.iessaladillo.es.pr181.main.view.MainView;


public class MainPresenter implements BasePresenter, MainRepositoryCallback {

    private final MainView mView;
    private final DBMainRepository mRepository;

    public MainPresenter(MainView view) {
        mView = view;
        mRepository = new DBMainRepository();
    }

    // =====================
    // Intefaz BasePresenter
    // =====================

    @Override
    public void initialize() {
        mRepository.getList(this);

    }

    // Destruye el presentador. Procede de la interfaz BasePresenter.
    @Override
    public void destroy() {

    }

    // ===================================
    // Métodos propios de este presentador
    // ===================================

    public void addStudent() {
        mRepository.addElement(this);
    }

    public void removeStudent(int position, Student student) {
        mRepository.removeElement(position, student, this);
    }

    // ===============================
    // Interfaz MainRepositoryCallback
    // ===============================

    @Override
    public void onListReceived(List<Student> list) {
        mView.showStudentList(list);
    }

    @Override
    public void onElementAdded(Student student) {
        mView.notifyStudentAdded(student);
    }

    @Override
    public void onElementRemoved(int position, Student student) {
        mView.notifyStudentRemoved(position, student);
    }

}
