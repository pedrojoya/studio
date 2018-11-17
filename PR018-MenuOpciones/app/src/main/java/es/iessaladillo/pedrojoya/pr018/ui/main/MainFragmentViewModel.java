package es.iessaladillo.pedrojoya.pr018.ui.main;

import androidx.annotation.IdRes;
import androidx.lifecycle.ViewModel;

class MainFragmentViewModel extends ViewModel {

    private int effectId;
    private boolean visible = true;

    MainFragmentViewModel(@IdRes int menuItemResIdOfInitialEffect) {
        effectId = menuItemResIdOfInitialEffect;
    }

    int getEffectId() {
        return effectId;
    }

    void setEffectId(int effectId) {
        this.effectId = effectId;
    }

    boolean isVisible() {
        return visible;
    }

    void toggleVisibility() {
        visible = !visible;
    }

}
