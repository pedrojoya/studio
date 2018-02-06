package es.iessaladillo.pedrojoya.pr170.main;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import es.iessaladillo.pedrojoya.pr170.BR;
import es.iessaladillo.pedrojoya.pr170.R;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ActivityMainModel extends BaseObservable {

    private String name;
    private boolean polite;
    private final String[] treatments;
    private int treatmentIndex;
    private String photoUrl;

    public ActivityMainModel(Context context) {
        treatments = context.getResources().getStringArray(R.array.activity_main_treatments);
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
