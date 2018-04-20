package es.iessaladillo.pedrojoya.pr010;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.hasImeAction;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTests {

    @SuppressWarnings({"unchecked", "CanBeFinal"})
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateTxtMensajeHasFocusInitially() {
        onView(withId(R.id.txtMessage)).check(matches(hasFocus()));
    }

    @Test
    public void validateMessageAddedAtTheEnd() {
        onView(withId(R.id.txtMessage)).perform(closeSoftKeyboard(), replaceText("Quillo que"));
        onView(withId(R.id.btnSend)).perform(click());
        onView(withId(R.id.lblText)).check(matches(withText(endsWith("Quillo que\n\n"))));
    }

    @Test
    public void validateTxtMensajeImeActionDone() {
        onView(withId(R.id.txtMessage)).check(matches(hasImeAction(EditorInfo.IME_ACTION_DONE)));
        onView(withId(R.id.txtMessage)).perform(replaceText("Quillo que"), pressImeActionButton());
        onView(withId(R.id.lblText)).check(matches(withText(endsWith("Quillo que\n\n"))));
    }

    @Test
    public void validateBtnEnviarDisabledWhenTxtMensajeEmpty() {
        onView(withId(R.id.txtMessage)).perform(clearText());
        onView(withId(R.id.btnSend)).check(matches(not(isEnabled())));
        onView(withId(R.id.txtMessage)).perform(replaceText("Quillo que"));
        onView(withId(R.id.btnSend)).check(matches(isEnabled()));
    }

    /**
     * Matches a string to a specific pattern
     *
     * @param pattern regular expression
     * @return the matcher result
     *
     * VER https://developer.android.com/reference/java/util/regex/Pattern.html
     */
    public static Matcher<View> matchesPattern(final String pattern){
        checkNotNull(pattern);
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public void describeTo(final Description description) {
                description.appendText("The textview does not conform to the pattern: ")
                        .appendText(pattern);
            }

            @Override
            protected boolean matchesSafely(TextView textView) {
                return textView.getText().toString().matches(pattern);
            }
        };
    }

}
