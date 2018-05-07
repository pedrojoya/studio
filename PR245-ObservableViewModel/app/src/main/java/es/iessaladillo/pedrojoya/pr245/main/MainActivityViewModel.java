package es.iessaladillo.pedrojoya.pr245.main;

import android.databinding.Bindable;
import android.text.TextUtils;

import es.iessaladillo.pedrojoya.pr245.BR;
import es.iessaladillo.pedrojoya.pr245.base.ObservableViewModel;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ObservableViewModel {

    private String name;
    private boolean polite;
    private final String[] treatments;
    private int treatmentIndex;
    private String photoUrl;

    public MainActivityViewModel(String[] treats) {
        this.treatments = treats;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public boolean isPolite() {
        return polite;
    }

    public void setPolite(boolean polite) {
        this.polite = polite;
        notifyPropertyChanged(BR.polite);
    }

    @Bindable
    public int getTreatmentIndex() {
        return treatmentIndex;
    }

    public void setTreatmentIndex(int treatmentIndex) {
        this.treatmentIndex = treatmentIndex;
        notifyPropertyChanged(BR.treatmentIndex);
    }

    @Bindable
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        notifyPropertyChanged(BR.photoUrl);
    }

    @Bindable({"treatmentIndex"})
    public String getTreatment() {
        return treatments[treatmentIndex];
    }

    // Propiedad dependiente de la propiedad name.
    @Bindable({"name"})
    public boolean isValid() {
        return !TextUtils.isEmpty(name);
    }

}
