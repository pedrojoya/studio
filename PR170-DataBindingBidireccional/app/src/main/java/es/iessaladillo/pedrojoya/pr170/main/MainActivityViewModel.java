package es.iessaladillo.pedrojoya.pr170.main;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.content.res.Resources;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.TextView;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr170.R;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private static final String PHOTO_BASE_URL = "https://dummyimage.com/356x200/deb4de/ffffff&text=";

    private final Random random = new Random();
    @SuppressWarnings("FieldCanBeLocal")
    private final String[] treatments;
    @SuppressLint("StaticFieldLeak")
    private final Resources resources;

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableBoolean polite = new ObservableBoolean();
    public final ObservableInt treatmentIndex = new ObservableInt();
    public final ObservableField<String> photoUrl = new ObservableField<>(PHOTO_BASE_URL + random.nextInt(100));
    public final ObservableBoolean valid = new ObservableBoolean();
    public final ObservableField<String> lblNameText = new ObservableField<>();
    public final ObservableField<String> viewMessage = new ObservableField<>();


    public MainActivityViewModel(@NonNull Resources resources) {
        this.resources = resources;
        this.treatments = resources.getStringArray(R.array.activity_main_treatments);
        lblNameText.set(resources.getString(
                R.string.activity_main_lblName_required));
        name.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                valid.set(!TextUtils.isEmpty(name.get()));
                lblNameText.set(TextUtils.isEmpty(name.get()) ? resources.getString(
                        R.string.activity_main_lblName_required) : resources.getString(
                        R.string.activity_main_lblName));
            }
        });
    }

    public void greet() {
        String message = resources.getString(R.string.main_activity_good_morning);
        if (polite.get()) {
            message = message + resources.getString(R.string.main_activity_nice_to_meet_you);
            if (!TextUtils.isEmpty(treatments[treatmentIndex.get()])) {
                message += " " + treatments[treatmentIndex.get()];
            }
        }
        message += " " + name.get();
        viewMessage.set(message);
    }

    @SuppressWarnings({"SameReturnValue", "unused"})
    public boolean done(TextView v, int actionId, KeyEvent event) {
        greet();
        return true;
    }

    @SuppressWarnings("SameReturnValue")
    public boolean insult() {
        viewMessage.set(resources.getString(R.string.main_activity_insult, name.get()));
        return true;
    }

    @SuppressWarnings("WeakerAccess")
    public void changePhoto() {
        photoUrl.set(PHOTO_BASE_URL + random.nextInt(100));
    }

}
