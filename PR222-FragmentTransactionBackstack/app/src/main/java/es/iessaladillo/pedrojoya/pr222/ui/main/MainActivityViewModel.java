package es.iessaladillo.pedrojoya.pr222.ui.main;

import androidx.lifecycle.ViewModel;
import android.os.Bundle;

import java.util.List;

import es.iessaladillo.pedrojoya.pr222.data.Repository;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private static final String STATE_SELECTED_ITEM = "STATE_SELECTED_ITEM";
    private static final String STATE_SELECTED_ITEM_KEY = "STATE_SELECTED_ITEM_KEY";

    private final Repository repository;
    private final String defaultItem;
    private String selectedItem;
    private List<String> students;
    private Long selectedItemKey = MainFragment.NO_ITEM_SELECTED;

    MainActivityViewModel(Repository repository, String defaultItem) {
        this.repository = repository;
        this.defaultItem = defaultItem;
        selectedItem = defaultItem;
    }

    @SuppressWarnings("SameParameterValue")
    List<String> getStudents(boolean forceLoad) {
        if (students == null || forceLoad) {
            students = repository.queryStudents();
        }
        return students;
    }

    String getSelectedItem() {
        return selectedItem;
    }

    void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    Long getSelectedItemKey() {
        return selectedItemKey;
    }

    void setSelectedItemKey(Long key) {
        selectedItemKey = key;
    }

    void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_SELECTED_ITEM, selectedItem);
        outState.putLong(STATE_SELECTED_ITEM_KEY, selectedItemKey);
    }

    void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && selectedItem.equals(defaultItem)) {
            selectedItem = savedInstanceState.getString(STATE_SELECTED_ITEM);
            selectedItemKey = savedInstanceState.getLong(STATE_SELECTED_ITEM_KEY);
        }
    }

}
