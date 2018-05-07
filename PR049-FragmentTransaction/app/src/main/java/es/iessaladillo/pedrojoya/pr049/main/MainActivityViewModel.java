package es.iessaladillo.pedrojoya.pr049.main;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private static final String STATE_SELECTED_ITEM = "STATE_SELECTED_ITEM";

    private final String defaultItem;
    private final List<String> items = new ArrayList<>(
            Arrays.asList("Baldomero", "Sergio", "Pablo", "Rodolfo", "Atanasio", "Gervasio",
                    "Prudencia", "Oswaldo", "Gumersindo", "Gerardo", "Rodrigo", "Óscar"));
    private String selectedItem;

    public MainActivityViewModel(String defaultItem) {
        this.defaultItem = defaultItem;
        selectedItem = defaultItem;
    }

    public List<String> getItems() {
        return items;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_SELECTED_ITEM, selectedItem);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && selectedItem.equals(defaultItem)) {
            selectedItem = savedInstanceState.getString(STATE_SELECTED_ITEM);
        }
    }
}
