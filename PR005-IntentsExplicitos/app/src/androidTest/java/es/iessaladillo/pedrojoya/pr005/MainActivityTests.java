package es.iessaladillo.pedrojoya.pr005;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;
import es.iessaladillo.pedrojoya.pr005.ui.main.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTests {

    @SuppressWarnings({"unchecked", "CanBeFinal"})
    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule(MainActivity.class);

    @Test
    public void validateObtencionDatos() {
        onView(withId(R.id.btnRequest)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"));
        onView(withId(R.id.txtAge)).perform(replaceText("18"));
        onView(withId(R.id.btnSend)).perform(click());
        onView(withId(R.id.lblData)).check(matches(withText("Nombre: Baldomero\nEdad: 18")));
    }

    @Test
    public void validateNoDatosWhenBackPressed() {
        onView(withId(R.id.btnRequest)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        Espresso.pressBack();
        onView(withId(R.id.lblData)).check(matches(withText("(no disponibles)")));
    }

    @SuppressLint("PrivateResource")
    @Test
    public void validateNoDatosWhenUpPressed() {
        onView(withId(R.id.btnRequest)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        onView(withId(R.id.lblData)).check(matches(withText("(no disponibles)")));
    }

    @Test
    public void validateEnvioDatos() {
        onView(withId(R.id.btnRequest)).perform(click());
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"));
        onView(withId(R.id.txtAge)).perform(replaceText("18"));
        onView(withId(R.id.btnSend)).perform(click());
        onView(withId(R.id.btnRequest)).perform(click());
        intended(allOf(
                    toPackage("es.iessaladillo.pedrojoya.pr005"),
                    hasExtras(
                            allOf(
                                hasEntry(equalTo("nombre"), equalTo("Baldomero")),
                                hasEntry(equalTo("edad"), equalTo(18))
                            )
                    )
                ));
    }

    @Test
    public void validateBtnAceptarDisabledWhenTxtNombreEmpty() {
        onView(withId(R.id.btnRequest)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        onView(withId(R.id.txtName)).perform(clearText());
        onView(withId(R.id.btnSend)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateBtnAceptarDisabledWhenTxtEdadEmpty() {
        onView(withId(R.id.btnRequest)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        onView(withId(R.id.txtAge)).perform(clearText());
        onView(withId(R.id.btnSend)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateBtnAceptarEnabledWhenFormularioCorrecto() {
        onView(withId(R.id.btnRequest)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"));
        onView(withId(R.id.txtAge)).perform(replaceText("18"));
        onView(withId(R.id.btnSend)).check(matches(isEnabled()));
    }

    @Test
    public void validateBtnAceptarDisabledWhenEdadIncorrecta() {
        onView(withId(R.id.btnRequest)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"));
        onView(withId(R.id.txtAge)).perform(replaceText("131"));
        onView(withId(R.id.btnSend)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateEnvioDatosAfterRotation() {
        onView(withId(R.id.btnRequest)).perform(click());
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"));
        onView(withId(R.id.txtAge)).perform(replaceText("18"));
        onView(withId(R.id.btnSend)).perform(click());
        rotateScreen();
        onView(withId(R.id.btnRequest)).perform(click());
        intended(allOf(
                toPackage("es.iessaladillo.pedrojoya.pr005"),
                hasExtras(
                        allOf(
                                hasEntry(equalTo("nombre"), equalTo("Baldomero")),
                                hasEntry(equalTo("edad"), equalTo(18))
                        )
                )
        ));
    }

    // Rota la pantalla.
    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation
                = context.getResources().getConfiguration().orientation;
        Activity activity = mActivityRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
