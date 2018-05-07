package es.iessaladillo.pedrojoya.pr244.main;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
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

import es.iessaladillo.pedrojoya.pr244.R;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private static final String PHOTO_BASE_URL = "https://dummyimage.com/356x200/deb4de/ffffff&text=";

    private final Random random = new Random();
    private final Resources resources;
    @SuppressWarnings("FieldCanBeLocal")
    private final String[] treatments;

    public final MutableLiveData<String> name = new MutableLiveData<>();
    public final MutableLiveData<Boolean> polite = new MutableLiveData<>();
    public final MutableLiveData<Integer> treatmentIndex = new MutableLiveData<>();
    public final MutableLiveData<String> photoUrl = new MutableLiveData<>();
    public final LiveData<Boolean> valid;
    public final LiveData<String> lblNameText;
    public final MutableLiveData<String> viewMessage = new MutableLiveData<>();


    public MainActivityViewModel(@NonNull Resources resources) {
        this.resources = resources;
        this.treatments = resources.getStringArray(R.array.activity_main_treatments);
        lblNameText = Transformations.map(name, theName -> TextUtils.isEmpty(theName) ?
                resources.getString(R.string.activity_main_lblName_required) :
                resources.getString(R.string.activity_main_lblName));
        valid = Transformations.map(name, theName -> !TextUtils.isEmpty(theName));

/*
        treatment = Transformations.switchMap(treatmentIndex, this::createTreatment);
        lblNameText = Transformations.switchMap(name, this::createLblNameText);
        valid = Transformations.switchMap(name, this::createValid);
*/

        // Initial values.
        name.setValue("");
        polite.setValue(false);
        treatmentIndex.setValue(0);
        photoUrl.setValue(PHOTO_BASE_URL + random.nextInt(100));
    }

    @NonNull
    private MutableLiveData<String> createTreatment(Integer index) {
        MutableLiveData<String> result = new MutableLiveData<>();
        result.setValue(treatments[index]);
        return result;
    }

    @NonNull
    private MutableLiveData<String> createLblNameText(String name) {
        MutableLiveData<String> result = new MutableLiveData<>();
        result.setValue(TextUtils.isEmpty(name) ?
                resources.getString(R.string.activity_main_lblName_required) :
                resources.getString(R.string.activity_main_lblName));
        return result;
    }

    @NonNull
    private MutableLiveData<Boolean> createValid(String name) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        result.setValue(!TextUtils.isEmpty(name));
        return result;
    }

    public void greet() {
        String message = resources.getString(R.string.main_activity_good_morning);
        if (polite.getValue()) {
            message = message + resources.getString(R.string.main_activity_nice_to_meet_you);
            if (!TextUtils.isEmpty(treatments[treatmentIndex.getValue()])) {
                message += " " + treatments[treatmentIndex.getValue()];
            }
        }
        message += " " + name.getValue();
        viewMessage.setValue(message);
    }

    public boolean done(TextView v, int actionId, KeyEvent event) {
        greet();
        return true;
    }

    public boolean insult() {
        viewMessage.setValue(resources.getString(R.string.main_activity_insult, name.getValue()));
        return true;
    }

    @SuppressWarnings("WeakerAccess")
    public void changePhoto() {
        photoUrl.setValue(PHOTO_BASE_URL + random.nextInt(100));
    }

/*
    public ObservableBoolean isValidForm = new ObservableBoolean(name) {

        @Override
        public void notifyChange() {
            boolean newValue = !TextUtils.isEmpty(name.get()) && name.get().length() > 1;
            if (get() != newValue) {
                set(newValue);
                super.notifyChange();
            }
        }

    };
*/

}

