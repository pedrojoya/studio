package es.iessaladillo.pedrojoya.pr097.ui.main;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import es.iessaladillo.pedrojoya.pr097.R;
import es.iessaladillo.pedrojoya.pr097.ui.retain.RetainActivity;
import es.iessaladillo.pedrojoya.pr097.ui.save.SaveActivity;
import es.iessaladillo.pedrojoya.pr097.ui.state.AndroidStateActivity;
import es.iessaladillo.pedrojoya.pr097.ui.viewmodel.ViewModelActivity;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public final IntentsTestRule<MainActivity> testRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void shouldStartSaveActivityWhenSaveButtonClicked() {
        clickOn(R.id.btnSave);
        intended(hasComponent(SaveActivity.class.getName()));
    }

    @Test
    public void shouldStartRetainActivityWhenRetainButtonClicked() {
        clickOn(R.id.btnRetain);
        intended(hasComponent(RetainActivity.class.getName()));
    }

    @Test
    public void shouldStartStateActivityWhenStateButtonClicked() {
        clickOn(R.id.btnState);
        intended(hasComponent(AndroidStateActivity.class.getName()));
    }

    @Test
    public void shouldStartViewModelActivityWhenViewModelButtonClicked() {
        clickOn(R.id.btnViewModel);
        intended(hasComponent(ViewModelActivity.class.getName()));
    }

}
