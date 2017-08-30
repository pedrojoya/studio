package es.iessaladillo.pedrojoya.pr011;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.hasImeAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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
    public void validateNombreAdded() {
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"));
        onView(withId(R.id.btnAdd)).perform(click());
        //onData(allOf(is(instanceOf(String.class)), is("Baldomero")))
        //        .inAdapterView(withId(R.id.lstAlumnos)).perform(click());
        onView(withId(R.id.lstStudents)).check(matches(withAdaptedData(is("Baldomero"))));
    }

    @Test
    public void validateNombreStillOnAdapterAfterRotation() {
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"));
        onView(withId(R.id.btnAdd)).perform(click());
        //onData(allOf(is(instanceOf(String.class)), is("Baldomero")))
        //        .inAdapterView(withId(R.id.lstAlumnos)).perform(click());
        onView(withId(R.id.lstStudents)).check(matches(withAdaptedData(is("Baldomero"))));
        rotateScreen();
        onView(withId(R.id.lstStudents)).check(matches(withAdaptedData(is("Baldomero"))));
    }

    @Test
    public void validateEmptyViewInvisibleWhenNombreAdded() {
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"));
        onView(withId(R.id.btnAdd)).perform(click());
        onView(withId(R.id.lblEmptyView)).check(matches(not(isDisplayed())));
    }

    @Test
    public void validateEmptyViewVisibleInitially() {
        onView(withId(R.id.lblEmptyView)).check(matches(isDisplayed()));
    }

    @Test
    public void validateNombreRemovedAndEmptyViewVisible() {
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"));
        onView(withId(R.id.btnAdd)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Baldomero")))
                .inAdapterView(withId(R.id.lstStudents)).perform(click());
        onView(withId(R.id.lstStudents)).check(matches(not(withAdaptedData(is("Baldomero")))));
        onView(withId(R.id.lblEmptyView)).check(matches(isDisplayed()));
    }

    @Test
    public void validateBtnAgregarDisabledInitially() {
        onView(withId(R.id.btnAdd)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateBtnAgregarEnabledStateDependingOnTxtNombreHasText() {
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"));
        onView(withId(R.id.btnAdd)).check(matches(isEnabled()));
        onView(withId(R.id.txtName)).perform(clearText());
        onView(withId(R.id.btnAdd)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateTxtNombreHasFocusInitially() {
        onView(withId(R.id.txtName)).check(matches(hasFocus()));
    }

    @Test
    public void validateTxtNombreImeActionDone() {
        onView(withId(R.id.txtName)).check(matches(hasImeAction(EditorInfo.IME_ACTION_DONE)));
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"), pressImeActionButton());
        onView(withId(R.id.lstStudents)).check(matches(withAdaptedData(is("Baldomero"))));
    }


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
