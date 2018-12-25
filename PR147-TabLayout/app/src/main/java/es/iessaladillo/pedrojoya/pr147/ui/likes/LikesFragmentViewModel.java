package es.iessaladillo.pedrojoya.pr147.ui.likes;

import androidx.lifecycle.ViewModel;

@SuppressWarnings("WeakerAccess")
public class LikesFragmentViewModel extends ViewModel {

    private int likes;

    int getLikes() {
        return likes;
    }

    int incrementLikes() {
        return ++likes;
    }

}
