package es.iessaladillo.pedrojoya.pr002;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import es.iessaladillo.pedrojoya.pr002.ui.main.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.schibsted.spain.barista.assertion.BaristaCheckedAssertions.assertChecked;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;
import static com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public final ActivityTestRule<MainActivity> testRule =
            new ActivityTestRule<>(MainActivity.class);

    // Initial state

    @Test
    public void shouldCheckBoxBeCheckedInitially() {
        assertChecked(R.id.chkPolite);
        assertDisplayed(R.id.chkPolite, R.string.main_polite_mode);
    }

    // Checkbox

    @Test
    public void shouldCheckBoxShowImpoliteModeWhenUnChecked() {
        clickOn(R.id.chkPolite);
        assertDisplayed(R.id.chkPolite, R.string.main_impolite_mode);
    }

    @Test
    public void shouldCheckBoxShowPoliteModeWhenChecked() {
        clickOn(R.id.chkPolite);
        clickOn(R.id.chkPolite);
        assertDisplayed(R.id.chkPolite, R.string.main_polite_mode);
    }

    @Test
    public void shouldGreetPolitelyWhenCheckedAndButtonPressed() {
        String name = "Test";
        writeTo(R.id.txtName, name);
        clickOn(R.id.btnGreet);
        String message = testRule.getActivity().getString(R.string.main_good_morning) +
                testRule.getActivity().getString(R.string.main_nice_to_meet_you) + " "
                + name;
        onView(withText(message))
                .inRoot(withDecorView(not(testRule.getActivity().getWindow()
                        .getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void shouldGreetImPolitelyWhenUncheckedAndButtonPressed() {
        String name = "Test";
        clickOn(R.id.chkPolite);
        writeTo(R.id.txtName, name);
        clickOn(R.id.btnGreet);
        String message = testRule.getActivity().getString(R.string.main_good_morning) + " "
                + name;
        onView(withText(message))
                .inRoot(withDecorView(not(testRule.getActivity().getWindow()
                        .getDecorView()))).check(matches(isDisplayed()));
    }

}