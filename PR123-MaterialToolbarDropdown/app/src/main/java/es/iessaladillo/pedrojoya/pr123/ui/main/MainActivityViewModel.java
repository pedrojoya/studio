package es.iessaladillo.pedrojoya.pr123.ui.main;

import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private int likes = 0;
    private int effectResId;

    public MainActivityViewModel(int defaultEffectResId) {
        effectResId = defaultEffectResId;
    }

    public int getLikes() {
        return likes;
    }

    public void incrementLikes() {
        likes++;
    }

    public int getEffectResId() {
        return effectResId;
    }

    public void setEffectResId(int effectResId) {
        this.effectResId = effectResId;
    }


}
