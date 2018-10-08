package es.iessaladillo.pedrojoya.pr147.ui.main;

import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private int likes;

    public int getLikes() {
        return likes;
    }

    public int incrementLikes() {
        return ++likes;
    }

}
