package es.iessaladillo.pedrojoya.pr210.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.iessaladillo.pedrojoya.pr210.data.Database;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private final Database database;

    private List<String> items;
    private MutableLiveData<String> currentItem = new MutableLiveData<>();

    public MainActivityViewModel() {
        database = Database.getInstance();
    }

    public LiveData<String> getCurrentItem() {
        return currentItem;
    }

    public List<String> getItems() {
        if (items == null) {
            items = database.getItems();
        }
        return items;
    }

    public void setCurrentItem(String item) {
        currentItem.setValue(item);
    }

}
