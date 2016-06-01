package pedrojoya.iessaladillo.es.pr181.main.presenter;

import java.util.List;

import pedrojoya.iessaladillo.es.pr181.BasePresenter;
import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;
import pedrojoya.iessaladillo.es.pr181.main.model.repository.DBMainRepository;
import pedrojoya.iessaladillo.es.pr181.main.model.repository.MainRepositoryCallback;
import pedrojoya.iessaladillo.es.pr181.main.view.MainView;


public class MainPresenter implements BasePresenter, MainRepositoryCallback {

    private MainView mView;
    private DBMainRepository mRepository;

    public MainPresenter(MainView view) {
        mView = view;
        mRepository = new DBMainRepository();
    }

    // =====================
    // Intefaz BasePresenter
    // =====================

    @Override
    public void onResume() {
        getStudents();
    }

    // Destruye el presentador. Procede de la interfaz BasePresenter.
    @Override
    public void onDestroy() {
        mView = null;
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

    public void getStudents() {
        mView.showLoading();
        mRepository.getList(this);
    }

    // ===============================
    // Interfaz MainRepositoryCallback
    // ===============================

    @Override
    public void onListReceived(List<Student> list) {
        if (mView != null) {
            mView.showStudentList(list);
            mView.hideLoading();
        }
    }

    @Override
    public void onElementAdded(Student student) {
        if (mView != null) {
            mView.notifyStudentAdded(student);
        }
    }

    @Override
    public void onElementRemoved(int position, Student student) {
        if (mView != null) {
            mView.notifyStudentRemoved(position, student);
        }
    }

}
