package es.iessaladillo.pedrojoya.pr255.ui.main;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import es.iessaladillo.pedrojoya.pr255.base.Event;
import es.iessaladillo.pedrojoya.pr255.services.ExportToTextFileService;

class MainFragmentViewModel extends ViewModel {

    private static final String EXPORT_WORK_REQUEST_TAG = "ExportToTextFileService";

    private final List<String> _students;
    private final MutableLiveData<List<String>> students = new MutableLiveData<>();
    private final MediatorLiveData<Event<Uri>> uri = new MediatorLiveData<>();
    private final MediatorLiveData<Event<String>> errorMessage = new MediatorLiveData<>();
    private final MediatorLiveData<Boolean> loading = new MediatorLiveData<>();

    MainFragmentViewModel(List<String> students) {
        this._students = students;
        updateStudentsLiveData();
        uri.addSource(WorkManager.getInstance().getWorkInfosByTagLiveData(EXPORT_WORK_REQUEST_TAG),
            workInfoList -> {
                if (!workInfoList.isEmpty()) {
                    WorkInfo workInfo = workInfoList.get(0);
                    if (workInfo != null) {
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            uri.setValue(new Event<>(Uri.parse(workInfo.getOutputData()
                                .getString(ExportToTextFileService.EXTRA_RESULT_URI_STRING))));
                        }
                    }
                }
            });
        errorMessage.addSource(WorkManager.getInstance().getWorkInfosByTagLiveData(EXPORT_WORK_REQUEST_TAG),
            workInfoList -> {
                if (!workInfoList.isEmpty()) {
                    WorkInfo workInfo = workInfoList.get(0);
                    if (workInfo != null) {
                        if (workInfo.getState() == WorkInfo.State.FAILED) {
                            errorMessage.setValue(new Event<>(workInfo.getOutputData()
                                .getString(ExportToTextFileService.EXTRA_RESULT_URI_STRING)));
                        }
                    }
                }
            });
        loading.addSource(WorkManager.getInstance().getWorkInfosByTagLiveData(EXPORT_WORK_REQUEST_TAG),
            workInfoList -> {
                if (!workInfoList.isEmpty()) {
                    WorkInfo workInfo = workInfoList.get(0);
                    if (workInfo != null) {
                        loading.setValue(workInfo.getState() == WorkInfo.State.RUNNING);
                    }
                }
            });
    }

    LiveData<List<String>> getStudents() {
        return students;
    }

    void deleteStudent(int position) {
        _students.remove(position);
        updateStudentsLiveData();
    }

    private void updateStudentsLiveData() {
        students.setValue(new ArrayList<>(_students));
    }

    LiveData<Event<Uri>> getUri() {
        return uri;
    }

    LiveData<Event<String>> getErrorMessage() {
        return errorMessage;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    void export() {
        List<String> currentStudents = getStudents().getValue();
        if (currentStudents != null) {
            OneTimeWorkRequest exportWorkRequest = ExportToTextFileService.newOneTimeWorkRequestBuilder(
                currentStudents).setConstraints(
                new Constraints.Builder().setRequiresStorageNotLow(true).build()).addTag(
                EXPORT_WORK_REQUEST_TAG).build();
            WorkManager.getInstance().beginUniqueWork(EXPORT_WORK_REQUEST_TAG,
                ExistingWorkPolicy.REPLACE, exportWorkRequest).enqueue();
        }
    }
}
