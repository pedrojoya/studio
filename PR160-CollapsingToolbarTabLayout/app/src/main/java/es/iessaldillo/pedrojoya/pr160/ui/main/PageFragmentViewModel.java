package es.iessaldillo.pedrojoya.pr160.ui.main;

import androidx.lifecycle.ViewModel;

class PageFragmentViewModel extends ViewModel {

    private int likes;

    int getLikes() {
        return likes;
    }

    int incrementsLikes() {
        return ++likes;
    }

}
