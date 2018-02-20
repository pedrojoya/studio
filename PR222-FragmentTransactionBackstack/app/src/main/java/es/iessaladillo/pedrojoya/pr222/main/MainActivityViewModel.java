package es.iessaladillo.pedrojoya.pr222.main;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    public static final int NO_ITEM_SELECTED = -1;

    private List<String> items;
    private int selectedItem = NO_ITEM_SELECTED;


    public List<String> getItems() {
        if (items == null) {
            items = new ArrayList<>(Arrays.asList("Baldomero", "Sergio", "Pablo", "Rodolfo", "Atanasio",
                    "Gervasio",
                    "Prudencia", "Oswaldo", "Gumersindo", "Gerardo", "Rodrigo", "Óscar"));
        }
        return items;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

}
