package es.iessaladillo.pedrojoya.pr220.ui.main;

import java.util.List;

import es.iessaladillo.pedrojoya.pr220.data.model.Student;
import es.iessaladillo.pedrojoya.pr220.utils.ViewMessage;

public class MainActivityViewData implements Cloneable {

    enum EventType {
        LOADING, DATA, ERROR_REFRESHING, NAVIGATE_TO_ADD,
        NAVIGATE_TO_EDIT, DELETED, ERROR_DELETING
    }

    private EventType eventType;
    private boolean loading;
    private List<Student> data;
    private ViewMessage message;
    private String dataId;

    private MainActivityViewData() {
    }

    MainActivityViewData cloneLoading(boolean loading) throws CloneNotSupportedException {
        MainActivityViewData object = (MainActivityViewData) clone();
        object.eventType = EventType.LOADING;
        object.loading = loading;
        return object;
    }

    MainActivityViewData cloneData(List<Student> data) throws CloneNotSupportedException {
        MainActivityViewData object = new MainActivityViewData();
        object.eventType = EventType.DATA;
        object.data = data;
        return object;
    }

    MainActivityViewData cloneErrorRefreshing(ViewMessage message) throws CloneNotSupportedException {
        MainActivityViewData object = new MainActivityViewData();
        object.eventType = EventType.ERROR_REFRESHING;
        object.message = message;
        return object;
    }

    MainActivityViewData cloneNavigateToEdit(String dataId) throws CloneNotSupportedException {
        MainActivityViewData object = new MainActivityViewData();
        object.eventType = EventType.NAVIGATE_TO_EDIT;
        object.dataId = dataId;
        return object;
    }

    MainActivityViewData cloneNavigateToAdd() throws CloneNotSupportedException {
        MainActivityViewData object = new MainActivityViewData();
        object.eventType = EventType.NAVIGATE_TO_ADD;
        return object;
    }

    MainActivityViewData cloneDeleted(ViewMessage message) throws CloneNotSupportedException {
        MainActivityViewData object = new MainActivityViewData();
        object.eventType = EventType.DELETED;
        object.message = message;
        return object;
    }

    MainActivityViewData cloneErrorDeleting(ViewMessage message) throws CloneNotSupportedException {
        MainActivityViewData object = new MainActivityViewData();
        object.eventType = EventType.ERROR_DELETING;
        object.message = message;
        return object;
    }

}
