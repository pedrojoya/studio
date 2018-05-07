package es.iessaladillo.pedrojoya.pr245.main;

import android.content.res.Resources;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.TextView;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr245.BR;
import es.iessaladillo.pedrojoya.pr245.R;
import es.iessaladillo.pedrojoya.pr245.base.ObservableViewModel;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ObservableViewModel {

    private static final String PHOTO_BASE_URL = "https://dummyimage.com/356x200/deb4de/ffffff&text=";

    private final Random random = new Random();
    private final Resources resources;

    private String name;
    private boolean polite;
    private final String[] treatments;
    private int treatmentIndex;
    private String photoUrl = PHOTO_BASE_URL + random.nextInt(100);
    private String viewMessage;

    public MainActivityViewModel(@NonNull Resources resources) {
        this.resources = resources;
        this.treatments = resources.getStringArray(R.array.activity_main_treatments);
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

    @Bindable
    public String getViewMessage() {
        return viewMessage;
    }

    public void setViewMessage(String viewMessage) {
        this.viewMessage = viewMessage;
        notifyPropertyChanged(BR.viewMessage);
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

    @Bindable({"name"})
    public String getLblNameText() {
        return TextUtils.isEmpty(name) ? resources.getString(
                R.string.activity_main_lblName_required) : resources.getString(
                R.string.activity_main_lblName);
    }

    public void greet() {
        String message = resources.getString(R.string.main_activity_good_morning);
        if (polite) {
            message = message + resources.getString(R.string.main_activity_nice_to_meet_you);
            if (!TextUtils.isEmpty(getTreatment())) {
                message += " " + getTreatment();
            }
        }
        message += " " + name;
        setViewMessage(message);
    }

    @SuppressWarnings({"SameReturnValue", "unused"})
    public boolean done(TextView v, int actionId, KeyEvent event) {
        greet();
        return true;
    }

    @SuppressWarnings("SameReturnValue")
    public boolean insult() {
        setViewMessage(resources.getString(R.string.main_activity_insult, name));
        return true;
    }

    @SuppressWarnings("WeakerAccess")
    public void changePhoto() {
        setPhotoUrl(PHOTO_BASE_URL + random.nextInt(100));
    }

}
