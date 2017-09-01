package es.iessaladillo.pedrojoya.pr092.main;

import android.arch.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss",
            Locale.getDefault());

    private ArrayList<String> data;

    public List<String> getData() {
        if (data == null) {
            data = new ArrayList<>();
            data.add(simpleDateFormat.format(new Date()));
        }
        return data;
    }

}
