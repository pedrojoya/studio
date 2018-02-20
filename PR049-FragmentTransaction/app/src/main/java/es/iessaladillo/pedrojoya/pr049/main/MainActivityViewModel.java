package es.iessaladillo.pedrojoya.pr049.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.iessaladillo.pedrojoya.pr049.R;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends AndroidViewModel {

    private List<String> items;
    private String selectedItem;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        selectedItem = application.getApplicationContext().getString(R.string
                .main_activity_no_item);
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

}
