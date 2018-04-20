package es.iessaladillo.pedrojoya.pr049.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends AndroidViewModel {

    private static final String STATE_SELECTED_ITEM = "STATE_SELECTED_ITEM";

    private List<String> items;
    private String selectedItem;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        loadItems();
    }

    private void loadItems() {
        items = new ArrayList<>(Arrays.asList("Baldomero", "Sergio", "Pablo", "Rodolfo", "Atanasio",
                "Gervasio",
                "Prudencia", "Oswaldo", "Gumersindo", "Gerardo", "Rodrigo", "Óscar"));
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
        if (savedInstanceState != null && selectedItem == null) {
            selectedItem = savedInstanceState.getString(STATE_SELECTED_ITEM);
        }
    }
}
