package es.iessaladillo.pedrojoya.pr170.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr170.R;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends AndroidViewModel {

    private static final String PHOTO_BASE_URL = "https://dummyimage.com/356x200/deb4de/ffffff&text=";

    private final Random random = new Random();
    @SuppressWarnings("FieldCanBeLocal")
    private final String[] treatments;
    private final Context context;

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableBoolean polite = new ObservableBoolean();
    public final ObservableInt treatmentIndex = new ObservableInt();
    public final ObservableField<String> photoUrl = new ObservableField<>(PHOTO_BASE_URL + random.nextInt(100));
    public final ObservableField<String> treatment = new ObservableField<>();
    public final ObservableBoolean valid = new ObservableBoolean();
    public final ObservableField<String> lblNameText = new ObservableField<>();
    public final ObservableField<String> viewMessage = new ObservableField<>();


    public MainActivityViewModel(@NonNull Application application, String[] treats) {
        super(application);
        this.context = application.getApplicationContext();
        this.treatments = treats;
        lblNameText.set(context.getString(
                R.string.activity_main_lblName_required));
        treatmentIndex.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                treatment.set(treatments[treatmentIndex.get()]);
            }
        });
        name.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                valid.set(!TextUtils.isEmpty(name.get()));
                lblNameText.set(TextUtils.isEmpty(name.get()) ? context.getString(
                        R.string.activity_main_lblName_required) : context.getString(
                        R.string.activity_main_lblName));
            }
        });
    }

    public void greet() {
        String message = context.getString(R.string.main_activity_good_morning);
        if (polite.get()) {
            message = message + context.getString(R.string.main_activity_nice_to_meet_you);
            if (!TextUtils.isEmpty(treatment.get())) {
                message += " " + treatment.get();
            }
        }
        message += " " + name.get();
        viewMessage.set(message);
    }

    public boolean insult() {
        viewMessage.set(context.getString(R.string.main_activity_insult, name.get()));
        return true;
    }

    @SuppressWarnings("WeakerAccess")
    public void changePhoto() {
        photoUrl.set(PHOTO_BASE_URL + random.nextInt(100));
    }

}
