package pedrojoya.iessaladillo.es.pr181.main.presenter;

import android.util.Log;

import java.util.List;

import pedrojoya.iessaladillo.es.pr181.BasePresenter;
import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;
import pedrojoya.iessaladillo.es.pr181.main.model.repository.DBMainRepository;
import pedrojoya.iessaladillo.es.pr181.main.model.repository.MainRepositoryCallback;
import pedrojoya.iessaladillo.es.pr181.main.view.MainView;


public class MainPresenter implements BasePresenter<MainView>, MainRepositoryCallback {

    private MainView mView;
    private DBMainRepository mRepository;

    public MainPresenter() {
        mRepository = new DBMainRepository();
    }

    // =====================
    // Intefaz BasePresenter
    // =====================

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
        Log.d("Mia", "Pidiendo datos");
        if (mView != null) {
            mView.showLoading();
        }
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
