package es.iessaladillo.pedrojoya.pr018.ui.main;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr018.R;

class MainActivityViewModel extends ViewModel {

    private int effectId = R.id.mnuOriginal;
    private boolean visible = true;

    int getEffectId() {
        return effectId;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }

    public boolean isVisible() {
        return visible;
    }

    public void toggleVisibility() {
        visible = !visible;
    }

}
