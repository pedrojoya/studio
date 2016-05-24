package es.iessaladillo.pedrojoya.PR004;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTests {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule(MainActivity.class);

    @Test
    public void validateNavegar() {
        onView(withId(R.id.btnNavegar)).perform(click());
        intended(toPackage("com.android.browser"));
    }

    @Test
    public void validateBuscar() {
        onView(withId(R.id.btnBuscar)).perform(click());
        intended(toPackage("com.android.quicksearchbox"));
    }

    @Test
    public void validateLlamar() {
        onView(withId(R.id.btnLlamar)).perform(click());
        intended(toPackage("com.android.server.telecom"));
    }

    @Test
    public void validateMarcar() {
        onView(withId(R.id.btnMarcar)).perform(click());
        intended(toPackage("com.android.dialer"));
    }

    @Test
    public void validateMostrarMapa() {
        onView(withId(R.id.btnMostrarMapa)).perform(click());
        intended(toPackage("com.google.android.apps.maps"));
    }

    @Test
    public void validateBuscarMapa() {
        onView(withId(R.id.btnBuscarMapa)).perform(click());
        intended(toPackage("com.google.android.apps.maps"));
    }

    @Test
    public void validateMostrarContactos() {
        onView(withId(R.id.btnMostrarContactos)).perform(click());
        intended(toPackage("com.android.contacts"));
    }

}
