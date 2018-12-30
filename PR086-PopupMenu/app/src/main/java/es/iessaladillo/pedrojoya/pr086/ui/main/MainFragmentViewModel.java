package es.iessaladillo.pedrojoya.pr086.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr086.base.Event;
import es.iessaladillo.pedrojoya.pr086.data.Repository;
import es.iessaladillo.pedrojoya.pr086.data.local.model.Student;

class MainFragmentViewModel extends ViewModel {

    private final LiveData<List<Student>> students;
    private final Repository repository;
    private final MutableLiveData<Event<Student>> navigateToStudentDetail = new MutableLiveData<>();
    private final MutableLiveData<Event<Student>> navigateToStudentAssignments = new
        MutableLiveData<>();
    private final MutableLiveData<Event<Student>> navigateToStudentMarks = new MutableLiveData<>();
    private final MutableLiveData<Event<Student>> navigateToCallStudent = new MutableLiveData<>();
    private final MutableLiveData<Event<Student>> navigateToSendMessageToStudent = new
        MutableLiveData<>();

    MainFragmentViewModel(Repository repository) {
        this.repository = repository;
        students = repository.queryStudents();
    }

    LiveData<List<Student>> getStudents() {
        return students;
    }

    LiveData<Event<Student>> getNavigateToStudentDetail() {
        return navigateToStudentDetail;
    }

    LiveData<Event<Student>> getNavigateToStudentAssignments() {
        return navigateToStudentAssignments;
    }

    LiveData<Event<Student>> getNavigateToStudentMarks() {
        return navigateToStudentMarks;
    }

    LiveData<Event<Student>> getNavigateToCallStudent() {
        return navigateToCallStudent;
    }

    LiveData<Event<Student>> getNavigateToSendMessageToStudent() {
        return navigateToSendMessageToStudent;
    }

    void addStudent(Student student) {
        repository.insertStudent(student);
    }

    void deleteStudent(Student student) {
        repository.deleteStudent(student);
    }

    void showStudent(Student student) {
        navigateToStudentDetail.postValue(new Event<>(student));
    }

    void showAssignments(Student student) {
        navigateToStudentAssignments.postValue(new Event<>(student));
    }

    void showMarks(Student student) {
        navigateToStudentMarks.postValue(new Event<>(student));
    }

    void call(Student student) {
        navigateToCallStudent.postValue(new Event<>(student));
    }

    void sendMessage(Student student) {
        navigateToSendMessageToStudent.postValue(new Event<>(student));
    }

}
