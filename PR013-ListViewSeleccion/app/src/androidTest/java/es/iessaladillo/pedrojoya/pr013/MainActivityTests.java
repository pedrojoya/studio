package es.iessaladillo.pedrojoya.pr013;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import androidx.test.InstrumentationRegistry;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTests {

    @SuppressWarnings({"unchecked", "CanBeFinal"})
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateBtnComprobarInitialState() {
        onView(withId(R.id.btnCheck)).check(matches(not(isDisplayed())));
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.btnCheck)).check(matches(isDisplayed()));
        onView(withId(R.id.btnCheck)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateBtnComprobarEnabledWhenItemSelected() {
        onData(allOf(is(instanceOf(String.class)), is("Blanco")))
                .inAdapterView(withId(R.id.lstAnswers)).perform(click());
        onView(withId(R.id.btnCheck)).check(matches(isEnabled()));
    }

    @Test
    public void validateRespuestaCorrecta() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onData(allOf(is(instanceOf(String.class)), is("Blanco")))
                .inAdapterView(withId(R.id.lstAnswers)).perform(click());
        onView(withId(R.id.btnCheck)).perform(click());
        onView(withId(R.id.lblScore)).check(matches(isDisplayed()));
    }

    @Test
    public void validateRespuestaIncorrecta() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onData(allOf(is(instanceOf(String.class)), is("Negro")))
                .inAdapterView(withId(R.id.lstAnswers)).perform(click());
        onView(withId(R.id.btnCheck)).perform(click());
        onView(withId(R.id.lstAnswers)).check(matches(not(withAdaptedData(is("Negro")))));
    }

    /*@Test
    public void validateNombreAdded() {
        onView(withId(R.id.txtNombre)).perform(replaceText("Baldomero"));
        onView(withId(R.id.btnAgregar)).perform(click());
        //onData(allOf(is(instanceOf(String.class)), is("Baldomero")))
        //        .inAdapterView(withId(R.id.lstAlumnos)).perform(click());
        onView(withId(R.id.lstAlumnos)).check(matches(withAdaptedData(is("Baldomero"))));
    }

    @Test
    public void validateNombreStillOnAdapterAfterRotation() {
        onView(withId(R.id.txtNombre)).perform(replaceText("Baldomero"));
        onView(withId(R.id.btnAgregar)).perform(click());
        //onData(allOf(is(instanceOf(String.class)), is("Baldomero")))
        //        .inAdapterView(withId(R.id.lstAlumnos)).perform(click());
        onView(withId(R.id.lstAlumnos)).check(matches(withAdaptedData(is("Baldomero"))));
        rotateScreen();
        onView(withId(R.id.lstAlumnos)).check(matches(withAdaptedData(is("Baldomero"))));
    }

    @Test
    public void validateEmptyViewInvisibleWhenNombreAdded() {
        onView(withId(R.id.txtNombre)).perform(replaceText("Baldomero"));
        onView(withId(R.id.btnAgregar)).perform(click());
        onView(withId(R.id.lblNoHayAlumnos)).check(matches(not(isDisplayed())));
    }

    @Test
    public void validateEmptyViewVisibleInitially() {
        onView(withId(R.id.lblNoHayAlumnos)).check(matches(isDisplayed()));
    }

    @Test
    public void validateNombreRemovedAndEmptyViewVisible() {
        onView(withId(R.id.txtNombre)).perform(replaceText("Baldomero"));
        onView(withId(R.id.btnAgregar)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Baldomero")))
                .inAdapterView(withId(R.id.lstAlumnos)).perform(click());
        onView(withId(R.id.lstAlumnos)).check(matches(not(withAdaptedData(is("Baldomero")))));
        onView(withId(R.id.lblNoHayAlumnos)).check(matches(isDisplayed()));
    }

    @Test
    public void validateBtnAgregarDisabledInitially() {
        onView(withId(R.id.btnAgregar)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateBtnAgregarEnabledStateDependingOnTxtNombreHasText() {
        onView(withId(R.id.txtNombre)).perform(replaceText("Baldomero"));
        onView(withId(R.id.btnAgregar)).check(matches(isEnabled()));
        onView(withId(R.id.txtNombre)).perform(clearText());
        onView(withId(R.id.btnAgregar)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateTxtNombreHasFocusInitially() {
        onView(withId(R.id.txtNombre)).check(matches(hasFocus()));
    }

    @Test
    public void validateTxtNombreImeActionDone() {
        onView(withId(R.id.txtNombre)).check(matches(hasImeAction(EditorInfo.IME_ACTION_DONE)));
        onView(withId(R.id.txtNombre)).perform(replaceText("Baldomero"), pressImeActionButton());
        onView(withId(R.id.lstAlumnos)).check(matches(withAdaptedData(is("Baldomero"))));
    }*/


    private static Matcher<View> withAdaptedData(final Matcher<String> dataMatcher) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("with class name: ");
                dataMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof AdapterView)) {
                    return false;
                }
                @SuppressWarnings("rawtypes")
                Adapter adapter = ((AdapterView) view).getAdapter();
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (dataMatcher.matches(adapter.getItem(i))) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    // Rota la pantalla.
    @SuppressWarnings("unused")
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
