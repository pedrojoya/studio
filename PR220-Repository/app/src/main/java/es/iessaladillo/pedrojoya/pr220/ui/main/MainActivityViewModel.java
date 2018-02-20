package es.iessaladillo.pedrojoya.pr220.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.net.HttpURLConnection;
import java.util.List;

import es.iessaladillo.pedrojoya.pr220.R;
import es.iessaladillo.pedrojoya.pr220.data.Repository;
import es.iessaladillo.pedrojoya.pr220.data.model.Student;
import es.iessaladillo.pedrojoya.pr220.ui.student.StudentActivityStarter;
import es.iessaladillo.pedrojoya.pr220.utils.SingleLiveEvent;
import es.iessaladillo.pedrojoya.pr220.utils.ViewMessage;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private LiveData<List<Student>> students;
    public final ObservableField<ViewMessage> messageResId = new ObservableField<>();
    private final MutableLiveData<ViewMessage> message = new MutableLiveData<>();
    public final ObservableField<Boolean> loading = new ObservableField<>();
    private final MutableLiveData<Boolean> refreshing = new MutableLiveData<>();
    private final SingleLiveEvent<Integer> revertSwipe = new SingleLiveEvent<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Student>> getStudents() {
        if (students == null) {
            students = repository.getStudents();
        }
        return students;
    }

    public LiveData<ViewMessage> getMessage() {
        return message;
    }

    public LiveData<Boolean> getRefreshing() {
        return refreshing;
    }

    public LiveData<Integer> getRevertSwipe() {
        return revertSwipe;
    }

    public void refresh() {
        loading.set(Boolean.TRUE);
        refreshing.setValue(Boolean.TRUE);
        compositeDisposable.add(repository.loadStudentsFromApi()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> showListUpdated(), this::showErrorUpdatingList));
    }

    private void showListUpdated() {
        loading.set(Boolean.FALSE);
        refreshing.setValue(Boolean.FALSE);
        messageResId.set(new ViewMessage(R.string.fragment_main_students_list_updated, false));
        message.setValue(new ViewMessage(R.string.fragment_main_students_list_updated, false));
    }

    @SuppressWarnings("unused")
    private void showErrorUpdatingList(Throwable throwable) {
        loading.set(Boolean.FALSE);
        refreshing.setValue(Boolean.FALSE);
        messageResId.set(new ViewMessage(R.string.fragment_main_error_updating_list, false));
        message.setValue(new ViewMessage(R.string.fragment_main_error_updating_list, false));
    }

    @SuppressWarnings("unused")
    public void onItemClick(View view, Object item, int position) {
        StudentActivityStarter.start(view.getContext(), ((Student) item).getId());
    }

    public void onFabClick(View view) {
        StudentActivityStarter.start(view.getContext());
    }

    @SuppressWarnings("unused")
    public void onSwipedRight(RecyclerView.ViewHolder viewHolder, int direction, Object item,
            int position) {
        Student student = (Student) item;
        compositeDisposable.add(repository.deleteStudent(student)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> showSuccessDeletingStudent(), throwable -> {
                    showErrorDeletingStudent(throwable);
                    revertSwipe.setValue(position);
                }));
    }

    private void showSuccessDeletingStudent() {
        messageResId.set(new ViewMessage(R.string.main_fragment_student_deleted, false));
        message.setValue(new ViewMessage(R.string.main_fragment_student_deleted, false));
    }

    private void showErrorDeletingStudent(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            if (exception.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                messageResId.set(new ViewMessage(R.string.student_fragment_error_no_student, false));
                message.setValue(
                        new ViewMessage(R.string.student_fragment_error_no_student, false));
            } else {
                messageResId.set(new ViewMessage(R.string.main_fragment_error_deleting_student, false));
                message.setValue(
                        new ViewMessage(R.string.main_fragment_error_deleting_student, false));
            }
        } else {
            messageResId.set(new ViewMessage(R.string.main_fragment_error_deleting_student, false));
            message.setValue(new ViewMessage(R.string.main_fragment_error_deleting_student, false));
        }
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

}
