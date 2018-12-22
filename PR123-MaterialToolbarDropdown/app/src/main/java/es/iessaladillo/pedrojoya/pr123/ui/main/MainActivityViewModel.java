package es.iessaladillo.pedrojoya.pr123.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// We must use activity's viewModel and NOT FRAGMENTS VIEWMODELS, in order to preserve
// values during spinner selection change.
public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<Integer> likesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> effectResIdLiveData = new MutableLiveData<>();

    MainActivityViewModel(int defaultEffectResId) {
        likesLiveData.postValue(0);
        effectResIdLiveData.postValue(defaultEffectResId);
    }

    public LiveData<Integer> getLikes() {
        return likesLiveData;
    }

    public void incrementLikes() {
        int likes = likesLiveData.getValue() == null ? 0 : likesLiveData.getValue();
        likesLiveData.postValue(likes + 1);
    }

    public LiveData<Integer> getEffectResId() {
        return effectResIdLiveData;
    }

    public void setEffectResId(int effectResId) {
        effectResIdLiveData.postValue(effectResId);
    }

}
