package es.iessaladillo.pedrojoya.pr049.main;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private List<String> items;

    public List<String> getItems() {
        if (items == null) {
            items = new ArrayList<>(Arrays.asList("Baldomero", "Sergio", "Pablo", "Rodolfo", "Atanasio",
                    "Gervasio",
                    "Prudencia", "Oswaldo", "Gumersindo", "Gerardo", "Rodrigo", "Óscar"));
        }
        return items;
    }

}
