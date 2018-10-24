package es.iessaladillo.pedrojoya.pr007;

import android.content.Context;
import androidx.annotation.ColorRes;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.iessaladillo.pedrojoya.pr007.ui.main.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasFocus;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTests {

    @SuppressWarnings({"unchecked", "CanBeFinal"})
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateBtnAceptarDisabledWhenTxtUsuarioEmpty() {
        onView(withId(R.id.txtUsername)).perform(clearText());
        onView(withId(R.id.btnLogin)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateBtnAceptarDisabledWhenTxtEdadEmpty() {
        onView(withId(R.id.txtPassword)).perform(clearText());
        onView(withId(R.id.btnLogin)).check(matches(not(isEnabled())));
    }

    @Test
    public void validateBtnAceptarEnabledWhenFormularioCorrecto() {
        onView(withId(R.id.txtUsername)).perform(replaceText("Baldomero"));
        onView(withId(R.id.txtPassword)).perform(replaceText("llegateligero"));
        onView(withId(R.id.btnLogin)).check(matches(isEnabled()));
    }

    @Test
    public void validateLblUsuarioVisibleWhenTxtUsuarioHasData() {
        onView(withId(R.id.txtUsername)).perform(replaceText("Baldomero"));
        onView(withId(R.id.lblUsername)).check(matches(isDisplayed()));
    }

    @Test
    public void validateLblUsuarioInvisibleWhenTxtUsuarioisEmpty() {
        onView(withId(R.id.txtUsername)).perform(clearText());
        onView(withId(R.id.lblUsername)).check(matches(not(isDisplayed())));
    }

    @Test
    public void validateLblUsuarioColorWhenTxtUsuarioHasFocus() {
        onView(withId(R.id.txtUsername)).perform(replaceText("Baldomero"));
        onView(withId(R.id.lblUsername)).check(matches(withCurrentTextColor(R.color.accent)));
    }

    @Test
    public void validateLblUsuarioColorWhenTxtUsuarioNoFocus() {
        onView(withId(R.id.txtUsername)).perform(typeText("Baldomero"));
        onView(withId(R.id.txtPassword)).perform(typeText("llegateligero"));
        onView(withId(R.id.lblUsername)).check(matches(withCurrentTextColor(R.color.primary)));
    }

    @Test
    public void validateLblClaveVisibleWhenTxtClaveHasData() {
        onView(withId(R.id.txtPassword)).perform(replaceText("llegateligero"));
        onView(withId(R.id.lblPassword)).check(matches(isDisplayed()));
    }

    @Test
    public void validateLblClaveInvisibleWhenTxtClaveisEmpty() {
        onView(withId(R.id.txtPassword)).perform(clearText());
        onView(withId(R.id.lblPassword)).check(matches(not(isDisplayed())));
    }

    @Test
    public void validateLblClaveColorWhenTxtClaveHasFocus() {
        onView(withId(R.id.txtPassword)).perform(typeText("Baldomero"));
        onView(withId(R.id.lblPassword)).check(matches(withCurrentTextColor(R.color.accent)));
    }

    @Test
    public void validateLblClaveColorWhenTxtClaveNoFocus() {
        onView(withId(R.id.txtPassword)).perform(typeText("llegateligero"));
        onView(withId(R.id.txtUsername)).perform(typeText("Baldomero"));
        onView(withId(R.id.lblPassword)).check(matches(withCurrentTextColor(R.color.primary)));
    }

    @Test
    public void validateToastShownWhenBtnAceptarPressed() {
        onView(withId(R.id.txtUsername)).perform(replaceText("Baldomero"));
        onView(withId(R.id.txtPassword)).perform(replaceText("llegateligero"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("Conectando con el usuario Baldomero…"))
                .inRoot(withDecorView(Matchers.not(mActivityRule.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateResetFieldsWhenBtnCancelarPressed() {
        onView(withId(R.id.txtUsername)).perform(replaceText("Baldomero"));
        onView(withId(R.id.txtPassword)).perform(replaceText("llegateligero"), closeSoftKeyboard());
        onView(withId(R.id.btnCancel)).perform(click());
        onView(withId(R.id.txtUsername)).check(matches(withText("")));
        onView(withId(R.id.txtPassword)).check(matches(withText("")));
    }

    @Test
    public void validateTxtUsuarioHasFocusInitially() {
        onView(withId(R.id.txtUsername)).check(matches(hasFocus()));
    }

    /**
     * Returns a matcher that matches {@link TextView}s based on text property value. Note: View's
     * text property is never null. If you setText(null) it will still be "". Do not use null
     * matcher.
     *
     * @param integerMatcher {@link Matcher} of {@link String} with text to match
     */
    private static Matcher<View> withCurrentTextColor(final Matcher<Integer> integerMatcher) {
        checkNotNull(integerMatcher);
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with text color: ");
                integerMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(TextView textView) {
                return integerMatcher.matches(textView.getCurrentTextColor());
            }
        };
    }

    /**
     * Returns a matcher that matches {@link TextView} based on it's text property value. Note:
     * View's Sugar for withTextColor(is("string")).
     */
    private static Matcher<View> withCurrentTextColor(@ColorRes int color) {
        Context context = InstrumentationRegistry.getTargetContext();
        return withCurrentTextColor(is(ContextCompat.getColor(context, color)));
    }

}
