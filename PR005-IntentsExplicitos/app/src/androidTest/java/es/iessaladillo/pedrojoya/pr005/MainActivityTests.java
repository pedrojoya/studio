package es.iessaladillo.pedrojoya.pr005;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
        onView(withId(R.id.btnSolicitar)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        onView(withId(R.id.txtNombre)).perform(typeText("Baldomero"));
        onView(withId(R.id.txtEdad)).perform(clearText()).perform(typeText("18"));
        onView(withId(R.id.btnAceptar)).perform(click());
        onView(withId(R.id.lblDatos)).check(matches(withText("Nombre: Baldomero\nEdad: 18")));
    }

    @Test
    public void validateNoDatosWhenBackPressed() {
        onView(withId(R.id.btnSolicitar)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        Espresso.pressBack();
        onView(withId(R.id.lblDatos)).check(matches(withText("(no disponibles)")));
    }

    @SuppressLint("PrivateResource")
    @Test
    public void validateNoDatosWhenUpPressed() {
        onView(withId(R.id.btnSolicitar)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        onView(withId(R.id.lblDatos)).check(matches(withText("(no disponibles)")));
    }

    @Test
    public void validateEnvioDatos() {
        onView(withId(R.id.btnSolicitar)).perform(click());
        onView(withId(R.id.txtNombre)).perform(typeText("Baldomero"));
        onView(withId(R.id.txtEdad)).perform(clearText()).perform(typeText("18"));
        onView(withId(R.id.btnAceptar)).perform(click());
        onView(withId(R.id.btnSolicitar)).perform(click());
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
        onView(withId(R.id.btnSolicitar)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        onView(withId(R.id.txtNombre)).perform(clearText());
        onView(withId(R.id.btnAceptar)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateBtnAceptarDisabledWhenTxtEdadEmpty() {
        onView(withId(R.id.btnSolicitar)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        onView(withId(R.id.txtEdad)).perform(clearText());
        onView(withId(R.id.btnAceptar)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateBtnAceptarEnabledWhenFormularioCorrecto() {
        onView(withId(R.id.btnSolicitar)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        onView(withId(R.id.txtNombre)).perform(clearText());
        onView(withId(R.id.txtNombre)).perform(typeText("Baldomero"));
        onView(withId(R.id.txtEdad)).perform(clearText());
        onView(withId(R.id.txtEdad)).perform(typeText("18"));
        onView(withId(R.id.btnAceptar)).check(matches(isEnabled()));
    }

    @Test
    public void validateBtnAceptarDisabledWhenEdadIncorrecta() {
        onView(withId(R.id.btnSolicitar)).perform(click());
        intended(toPackage("es.iessaladillo.pedrojoya.pr005"));
        onView(withId(R.id.txtNombre)).perform(clearText());
        onView(withId(R.id.txtNombre)).perform(typeText("Baldomero"));
        onView(withId(R.id.txtEdad)).perform(clearText());
        onView(withId(R.id.txtEdad)).perform(typeText("131"));
        onView(withId(R.id.btnAceptar)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateEnvioDatosAfterRotation() {
        onView(withId(R.id.btnSolicitar)).perform(click());
        onView(withId(R.id.txtNombre)).perform(typeText("Baldomero"));
        onView(withId(R.id.txtEdad)).perform(clearText()).perform(typeText("18"));
        onView(withId(R.id.btnAceptar)).perform(click());
        rotateScreen();
        onView(withId(R.id.btnSolicitar)).perform(click());
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
