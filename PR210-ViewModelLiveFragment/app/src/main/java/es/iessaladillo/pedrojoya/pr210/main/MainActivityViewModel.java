package es.iessaladillo.pedrojoya.pr210.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    public static final int NO_ITEM_SELECTED = -1;

    private List<String> items;
    private MutableLiveData<Integer> selectedItem = new MutableLiveData<>();
    private MutableLiveData<String> currentItem = new MutableLiveData<>();


    public List<String> getItems() {
        if (items == null) {
            items = new ArrayList<>(Arrays.asList("Baldomero", "Sergio", "Pablo", "Rodolfo", "Atanasio",
                    "Gervasio",
                    "Prudencia", "Oswaldo", "Gumersindo", "Gerardo", "Rodrigo", "Óscar"));
        }
        return items;
    }

    public LiveData<Integer> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Integer itemIndex) {
        selectedItem.setValue(itemIndex);
    }

    public LiveData<String> getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(String item) {
        currentItem.setValue(item);
    }

}
